
  <g:set var="currentCollectionItem" value="${rqt?.parkImatriculation.size() > collectionIndex ? rqt.parkImatriculation.get(collectionIndex) : null}" />
  <h4>
    ${message(code:'pcr.property.parkImatriculation.label')}
    <span>
      <g:if test="${currentCollectionItem != null}">
        ${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        ${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  
    
    <label for="parkImatriculation.${collectionIndex}.immatriculation" class="required"><g:message code="pcr.property.immatriculation.label" /> *  <span><g:message code="pcr.property.immatriculation.help" /></span></label>
            <input  type="text" id="parkImatriculation.${collectionIndex}.immatriculation" name="parkImatriculation[${collectionIndex}].immatriculation" value="${currentCollectionItem?.immatriculation?.toString()}" 
                    class="required  validate-string ${rqt.stepStates['car'].invalidFields.contains('parkImatriculation['+collectionIndex+'].immatriculation') ? 'validation-failed' : ''}" title="<g:message code="pcr.property.immatriculation.validationError" />"   style="width:99%" />
            

  
    
    <label for="parkImatriculation.${collectionIndex}.tarif" class=""><g:message code="pcr.property.tarif.label" />   <span><g:message code="pcr.property.tarif.help" /></span></label>
            <input disabled=disabled type="text" id="parkImatriculation.${collectionIndex}.tarif" name="parkImatriculation[${collectionIndex}].tarif" value="${collectionSpecific['tarifImatriculation'][collectionIndex + collectionIndexAdded]}" 
                    class="  validate-string ${rqt.stepStates['car'].invalidFields.contains('parkImatriculation['+collectionIndex+'].tarif') ? 'validation-failed' : ''}" title="<g:message code="pcr.property.tarif.validationError" />"   style="width:99%" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'car'])}">
    ${message(code:'request.action.backToMainForm')}
  </a>
  
