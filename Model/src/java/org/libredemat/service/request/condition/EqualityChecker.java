package org.libredemat.service.request.condition;

import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Check if condition triggered value is equal to mark value
 */
public class EqualityChecker implements IConditionChecker {
    private String mark;
    
    public EqualityChecker(String mark) {
        this.mark = mark;
    }
    
    public boolean test(String value) {
        return mark.equals(value);
    }
}
