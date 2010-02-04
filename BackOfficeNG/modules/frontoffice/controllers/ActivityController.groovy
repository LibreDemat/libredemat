import fr.cg95.cvq.business.users.Adult
import fr.cg95.cvq.business.users.Individual
import fr.cg95.cvq.service.request.IRequestSearchService
import fr.cg95.cvq.service.request.IRequestExternalService
import fr.cg95.cvq.business.request.Request
import fr.cg95.cvq.security.SecurityContext

class ActivityController {
    
    IRequestSearchService requestSearchService
    IRequestExternalService requestExternalService

    Adult ecitizen
    
    def afterInterceptor = { result ->
        result.month = params.month
        result.year = params.year
    	def calendar = Calendar.instance
    	
        result.currentYear = calendar.get(Calendar.YEAR)
        result.currentMonth = calendar.get(Calendar.MONTH) + 1
        
        result.monthsNames = [:]
    	(1..12).each { it ->
    		calendar.set(Calendar.MONTH, it - 1)
    		result.monthsNames[it] = 
    			calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, SecurityContext.currentLocale)
    	}
    }
    
    def beforeInterceptor = {
        this.ecitizen = SecurityContext.getCurrentEcitizen();
    }
    
    def index = { 
        def result = [:]
        if (params.year && params.month)
            result.activities = this.getActivities(Integer.valueOf(params.month), Integer.valueOf(params.year), null, null)
        else result.activities = this.getActivities(null, null, null, null)
        return result
    }
    
    def details = {
        def result = [:]
        result.individual = params.name.decodeURL()
        result.label = params.label.decodeURL()
        result.datas = getActivities(Integer.valueOf(params.month),
            Integer.valueOf(params.year), result.individual, result.label)[result.individual][result.label]

        return result
    }

    private getActivities(month, year, individualName, requestTypeLabel) {
        def from
        def to
        def dates
        if (month && year)
            dates = buildDate(month, year)
        else
            dates = buildDate(Calendar.instance.get(Calendar.MONTH) + 1,
                Calendar.instance.get(Calendar.YEAR))
        from = dates.from.time
        to = dates.to.time
        def result = [:]
        for (Request r :
            requestSearchService.getByHomeFolderId(ecitizen.homeFolder.id)) {
            def name = "${r.subjectFirstName} ${r.subjectLastName}"
            def label = r.requestType.label
            if (
                (individualName && name != individualName)
                || (requestTypeLabel && label != requestTypeLabel)
            ) continue
            if(!result[name]) result[name] = [:]
            def map = requestExternalService.getConsumptionsByRequest(r.id,from,to)
            if(map && !map.keySet().isEmpty()) {
                for(Date date : map.keySet()) { 
                    if(!result[name][label]) result[name][label] = [:]
                    if(!result[name][label]["${map.get(date)}".toString()])
                        result[name][label]["${map.get(date)}".toString()] = []
                    
                    result.get(name.toString()).get(label.toString()).get(map.get(date)).add(date)
                }
            }
            if(result[name].isEmpty()) result.remove(name.toString())
        }
        return result
    }
    
    protected Map buildDate(int month, int year) {
        def result = [
            from : new GregorianCalendar(),
            to: new GregorianCalendar()
        ]
        
        result.from.set( Calendar.YEAR, year)
        result.from.set( Calendar.MONTH, month - 1 )
        result.from.set( Calendar.DAY_OF_MONTH, 1 )
        
        result.to.set( Calendar.YEAR, year)
        result.to.set( Calendar.MONTH, month - 1 )
        result.to.set( Calendar.DAY_OF_MONTH, result.from.getActualMaximum(Calendar.DAY_OF_MONTH) )
        
        return result
    }
}
