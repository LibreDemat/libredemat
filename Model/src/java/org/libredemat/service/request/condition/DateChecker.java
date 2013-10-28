package org.libredemat.service.request.condition;

import java.text.ParseException;
import java.util.Date;

import org.libredemat.util.DateUtils;


/**
 * Check if condition triggered date value is after or before a reference date
 */
public class DateChecker implements IConditionChecker {
    private Date date;
    private String comparator;
    
    public DateChecker(String comparator, Date date) {
        this.comparator = comparator;
        this.date = date;
    }
    
    public boolean test(String value) {
        try {
            if (comparator.equals("<"))
                return DateUtils.parseDate(value).after(date);
            else if (comparator.equals(">"))
                return DateUtils.parseDate(value).before(date);
        } catch (ParseException pe) {
            return false;
        }
        return false;
    }
}
