import java.text.SimpleDateFormat
import java.util.Locale

class DetailFormatCartTagLib { // tag lig to display cart detail
    
    def calendar =  Calendar.instance
	
    
    def detailFormat = { attrs, body ->
        calendar.setTime(attrs.day)       
        def dateFormatString = new SimpleDateFormat("yyyy/MM/dd").format(attrs.day)
        out << String.format(Locale.FRANCE, '%te %<tb %<tY', calendar)
        out << '&nbsp;<span class="legend-label-box'+getType(attrs.type)+'" style="background-color: #'+attrs.color+';">&nbsp;</span> '
		out << attrs.labelService +" "
        out << '( '+attrs.name."${attrs.childId.toLong()}"+' )'
		
    }
    
    protected String getType(type){ // return the end of attribut class to display colors and type of day
        def result        
        if(type.toString().toLowerCase().equals('reservationencours')) {
            result = '-R'
        } else if(type.toString().toLowerCase().equals('reportencours')) {
            result = '-A'
        }
        return result
    }
}
