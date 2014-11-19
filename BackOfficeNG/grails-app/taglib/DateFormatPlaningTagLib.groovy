import java.text.SimpleDateFormat


class DateFormatPlaningTagLib { // tag lig to display cart list items
    
    def calendar =  Calendar.instance
	
    
    def dateFormat = { attrs, body ->
        calendar.setTime(attrs.day)       
        def dateFormatString = new SimpleDateFormat("yyyy/MM/dd").format(attrs.day)
		out << '<div>'
        out << String.format('%te %<tb %<tY', calendar) 
        out << '&nbsp;<span class="legend-label-box'+getType(attrs.type)+'" style="background-color: #'+attrs.color+';">&nbsp;</span>'
        out << '<input type="hidden" name="item" class="item" value="'
        out << attrs.childId +'-'+ attrs.homeFolderId + '-' + attrs.activityCode +'-'+ attrs.activityServiceCode
        out << '-'+ dateFormatString +'-'+ attrs.type +'-'+ attrs.color
        out << '" />'
		out << '<a class="deleteItem" id="remove-'+attrs.id+'" title="'+message(code:'action.delete')+'" alt="'+message(code:'action.delete')+'"></a>'
		out << '</div>'
		
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
