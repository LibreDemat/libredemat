import java.text.SimpleDateFormat

class MiniPlaningTagLib {
    /**
     * let see PlanningTagLib for comment, those file are similare
     */
    def styles = [:]
    def calendar =  Calendar.instance
    
    def miniPlanning = {attrs,body ->  // tag lib to render mini planning
        def year = attrs.year ? attrs.year : Calendar.instance.get(Calendar.YEAR)
        def month = attrs.month ? Integer.valueOf(attrs.month) - 1 : (Calendar.instance.get(Calendar.MONTH))
        def data = attrs.data, i = 0
	def deadLine = attrs.deadLine
	def childId = attrs.childId
	def activityCode = attrs.activityCode
        def incompatibility = attrs.incompatibility
        
        def dl = new SimpleDateFormat("yyyy-MM-dd").parse(deadLine)
		//dl = dl+1
		
        calendar.setMinimalDaysInFirstWeek(1)
        calendar.setFirstDayOfWeek(Calendar.MONDAY)
        calendar.set(Calendar.DATE, 1)
        calendar.set(Calendar.ERA, GregorianCalendar.AD)
        calendar.set(Calendar.YEAR, Integer.valueOf(year))
        calendar.set(Calendar.MONTH, Integer.valueOf(month))
        
        def cal1 = [:], cal2 = [:]
        for(Integer num : (1..6)) cal1[num] = [1,2,3,4,5,6,7]
        
        for(Integer day : (1 .. calendar.getActualMaximum(Calendar.DAY_OF_MONTH))) {
            calendar.set(Calendar.DATE, day)
            //def wom = calendar.get(Calendar.WEEK_OF_MONTH)
            def dom = calendar.get(Calendar.DAY_OF_MONTH)
            def middle = (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) / 2) as int            
            if (day < middle +1) {
                if(!cal2[0]) cal2[0] = []
                cal2[0].add(dom)
            } else { 
                if(!cal2[1]) cal2[1] = []
                cal2[1].add(dom)
            }
        }
      
        calendar.set(Calendar.DATE, 1)
        def table = ''
        for(Integer wom : cal2.keySet()) {
            if(cal2[wom]) {
                def tr = ''
                for(Integer row : cal2[wom]) {
                    def index = cal2[wom].indexOf(row)
                    tr += this.buildCell(cal2[wom][index], data, dl, activityCode, childId.toLong(), incompatibility)                    
                    tr += "\t\t<td></td>\n"
                }
                table += "\t<tr>\n${tr}\n\t</tr>\n"
            }
        }
      
        table = """
<table class="mini-activity-calendar">
  <tbody>
			
$table
  </tbody>
</table>
"""     
        out << table
    }
    
     protected String buildCell(Integer dom, Map data, Date dl, String activityCode, Long childId, Map incompatibility) {  // method to buil cell of planning
         def c = new GregorianCalendar();
	calendar.set(Calendar.DAY_OF_MONTH, dom)
        def result = ""
        def incompatible = []
        if(incompatibility[data.code]){
            incompatibility[data.code].incompatible.each{				
                incompatible.add(it.key+"|"+it.value.color+"|"+it.value.label.replaceAll('-',' '))
            }
        }
        
		String incompatibleString = incompatible.join("µ")
		
        def listDate = []
        
        data.days.each{                
             Date d3 = new SimpleDateFormat("yyyy-MM-dd").parse(it.key)
             c.setTime(d3)
             if(c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)){
                def dd = c.get(Calendar.DAY_OF_MONTH)
                listDate.add(dd)
             }
        }
        
        def dateRegistered = listDate.contains(calendar.get(Calendar.DAY_OF_MONTH))
        if(dateRegistered != false){
          result += "\t\t<td>"
        } else {
            result += "\t\t<td class='notRegistered'>"
        }
        
            result +=  '<span class="value">' + 
		String.format('%ta %<te', calendar) + '</span><div class="container">'

        data.days.each{
            def state = getAssociationType(it.value.toString())
            //if(it.value.toString() != "Ouvert"){                
                Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(it.key)
		c.setTime(d2)
		
		if(c.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
			&& c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
			&& c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                    result += '<span class="legend-label-box'+state.get(1)+'" id="'+data.code+'-'+it.key.replace('-','/') +'" style="background-color: #'+data.color+'"></span>'            
                    if(dl.before(c.getTime())){
						
						if(data.reservation == 'true'){
							println(incompatibleString)
	                        result += '<a id="'+state.get(0)+'Item-'+data.code+'-'+it.key.replace('-','/')+'-'+it.value.toString()+'" class="linkMonth '+state.get(0)+'"></a>'
	                        result += '<input type="hidden" id="monthly-'+ it.key.replace('-','/') 
	                        result += '" name="data" value="'+ childId + '-' + activityCode + '-' + data.code + '-' + it.key.replace('-','/') + '-' 
	                        result +=  it.value.toString() + '-' + data.color +'-'+data.label.replaceAll('-',' ')+'-'+incompatibleString+'" class="dataMonth" />'
						}
                    }
		}
            
        }

         return result + "</div></td>\n";
     }
     private getAssociationType(type){ // method to define association among the type of the day
            def result = []
            if(type.toString().toLowerCase().equals('reservationvalide')){
                result.add('associate')
                result.add('')
            } else if(type.toString().toLowerCase().equals('reservationencours')) {
                result.add('associate')
                result.add('-R')
            } else if(type.toString().toLowerCase().equals('reportencours')) {
                result.add('unassociate')
                result.add('-A')
            } else {
                result.add('unassociate')
                result.add('-O')
            }
            return result
        }
        
    
}
