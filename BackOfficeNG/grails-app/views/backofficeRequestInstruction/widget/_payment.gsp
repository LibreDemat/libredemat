<form method="post"
	id="${propertyName.replace('[','').replace(']','').replace('.', '')}_Form"
	action="<g:createLink action="modify" />" class="editable-list-form">
	<span id="${propertyName}_FormErrors" class="error"></span>

	<table>
	<tr>
		<td><input id="${propertyName}_Field" name="amount"
			type="text" style="width:150px;"
			class="${propertyType != '' ? 'validate-' + propertyType : ''} required"
			title="<g:message code="${i18nKeyPrefix}.validationError" />"
			${regex != '' ? ' regex="' + regex + '"' : ''}
			${minLength != null ? ' minlength="' + minLength + '"' : ''}
			${maxLength != null ? ' maxlength="' + maxLength + '"' : ''}
			value="${propertyValue}" />
			</td>
		<td>&nbsp;</td>
		<td><g:message code="system.paiement" /></td>
	</tr></table>
	<g:message code="payment.submit.validation.info" />

	<input name="requestId" type="hidden" value="${requestId}" /> 
	<input name="payment" type="hidden" value="true" /> 
	<input type="button" class="submitField" id='submitField' value="<g:message code="action.save" />" />&nbsp;
	<input type="button" class="revertField" id='revertField' value="<g:message code="action.cancel" />" />
</form>
