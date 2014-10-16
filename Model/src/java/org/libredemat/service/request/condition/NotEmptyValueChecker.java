package org.libredemat.service.request.condition;

/**
 * Check if condition triggered value is equal to one mark value of a list
 */
public class NotEmptyValueChecker implements IConditionChecker {
	public NotEmptyValueChecker() {
	}

	public boolean test(String value) {
		if (!value.isEmpty())
			return true;
		return false;
	}
}
