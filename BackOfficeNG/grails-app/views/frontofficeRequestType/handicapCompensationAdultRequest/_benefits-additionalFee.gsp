
  <g:set var="currentCollectionItem" value="${rqt?.additionalFee.size() > collectionIndex ? rqt.additionalFee.get(collectionIndex) : null}" />
  <h4>
    ${message(code:'hcar.property.additionalFee.label')}
    <span>
      <g:if test="${currentCollectionItem != null}">
        ${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        ${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  
    
    <label for="additionalFee.${collectionIndex}.additionalFeeKind" class="required"><g:message code="hcar.property.additionalFeeKind.label" /> *  <span><g:message code="hcar.property.additionalFeeKind.help" /></span></label>
            <input  type="text" id="additionalFee.${collectionIndex}.additionalFeeKind" name="additionalFee[${collectionIndex}].additionalFeeKind" value="${currentCollectionItem?.additionalFeeKind?.toString()}" 
                    class="required   ${rqt.stepStates['benefits'].invalidFields.contains('additionalFee['+collectionIndex+'].additionalFeeKind') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.additionalFeeKind.validationError" />"  maxlength="30" style="width:99%" />
            

  
    
    <label for="additionalFee.${collectionIndex}.additionalFeeCost" class="required"><g:message code="hcar.property.additionalFeeCost.label" /> *  <span><g:message code="hcar.property.additionalFeeCost.help" /></span></label>
            <input  type="text" id="additionalFee.${collectionIndex}.additionalFeeCost" name="additionalFee[${collectionIndex}].additionalFeeCost" value="${currentCollectionItem?.additionalFeeCost?.toString()}" 
                    class="required   ${rqt.stepStates['benefits'].invalidFields.contains('additionalFee['+collectionIndex+'].additionalFeeCost') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.additionalFeeCost.validationError" />"   style="width:99%" />
            

  
    
    <label for="additionalFee.${collectionIndex}.additionalFeePeriodicity" class="required"><g:message code="hcar.property.additionalFeePeriodicity.label" /> *  <span><g:message code="hcar.property.additionalFeePeriodicity.help" /></span></label>
            <input  type="text" id="additionalFee.${collectionIndex}.additionalFeePeriodicity" name="additionalFee[${collectionIndex}].additionalFeePeriodicity" value="${currentCollectionItem?.additionalFeePeriodicity?.toString()}" 
                    class="required   ${rqt.stepStates['benefits'].invalidFields.contains('additionalFee['+collectionIndex+'].additionalFeePeriodicity') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.additionalFeePeriodicity.validationError" />"  maxlength="30" style="width:99%" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'benefits'])}">
    ${message(code:'request.action.backToMainForm')}
  </a>
  
