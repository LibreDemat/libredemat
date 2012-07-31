import java.beans.PropertyEditorSupport

import org.joda.time.DateTime

class DateTimeEditor extends PropertyEditorSupport {

    PropertyEditorSupport dateEditor

    public void setAsText(String text) {
        dateEditor.setAsText(text)
        this.value = new DateTime(dateEditor.value)
    }
}
