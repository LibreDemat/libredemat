import java.text.DateFormat
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.web.binding.GrailsDataBinder
import org.joda.time.DateMidnight
import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.springframework.web.servlet.support.RequestContextUtils

class GlobalPropertyEditorConfig {
    static newDataBinder = { request, object ->
        def binder = GrailsDataBinder.createBinder(object, GrailsDataBinder.DEFAULT_OBJECT_NAME, request)
        registerCustomEditors(request, binder)
        return binder
    }

    private static void registerCustomEditors(request, binder) {
        def dateEditor = new NullifyingStructuredDateEditor(
            DateFormat.getDateInstance(DateFormat.SHORT, RequestContextUtils.getLocale(request)))

        binder.registerCustomEditor(Date.class, dateEditor)
        binder.registerCustomEditor(Calendar.class, dateEditor)
        binder.registerCustomEditor(DateMidnight.class,
            new DateMidnightEditor(dateEditor : dateEditor))
        binder.registerCustomEditor(DateTime.class,
            new DateTimeEditor(dateEditor : dateEditor))
        binder.registerCustomEditor(LocalTime.class, new LocalTimeEditor())
        binder.registerCustomEditor(String.class,
            new StringEditor(editor : binder.findCustomEditor(String.class, null)))
    }
}
