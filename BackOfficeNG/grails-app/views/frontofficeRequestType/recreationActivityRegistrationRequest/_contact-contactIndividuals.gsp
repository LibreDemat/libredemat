
  <g:set var="currentCollectionItem" value="${rqt?.contactIndividuals.size() > collectionIndex ? rqt.contactIndividuals.get(collectionIndex) : null}" />
  <h4>
    ${message(code:'rarr.property.contactIndividuals.label')}
    <span>
      <g:if test="${currentCollectionItem != null}">
        ${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        ${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  
    <label for="contactIndividuals.${collectionIndex}.lastName" class="required"><g:message code="rarr.property.lastName.label" /> *  <span><g:message code="rarr.property.lastName.help" /></span></label>
            <input type="text" id="contactIndividuals.${collectionIndex}.lastName" name="contactIndividuals[${collectionIndex}].lastName" value="${currentCollectionItem?.lastName?.toString()}" 
                    class="required  validate-lastName ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].lastName') ? 'validation-failed' : ''}" title="<g:message code="rarr.property.lastName.validationError" />"  maxlength="38" />
            

  
    <label for="contactIndividuals.${collectionIndex}.firstName" class="required"><g:message code="rarr.property.firstName.label" /> *  <span><g:message code="rarr.property.firstName.help" /></span></label>
            <input type="text" id="contactIndividuals.${collectionIndex}.firstName" name="contactIndividuals[${collectionIndex}].firstName" value="${currentCollectionItem?.firstName?.toString()}" 
                    class="required  validate-firstName ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].firstName') ? 'validation-failed' : ''}" title="<g:message code="rarr.property.firstName.validationError" />"  maxlength="38" />
            

  
    <label class="required"><g:message code="rarr.property.address.label" />&nbsp;* <span><g:message code="rarr.property.address.help" /></span></label>
            <div class="address required  ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address') ? 'validation-failed' : ''}">
            <label for="contactIndividuals.${collectionIndex}.address.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalDeliveryInformation}" maxlength="38" id="contactIndividuals.${collectionIndex}.address.additionalDeliveryInformation" name="contactIndividuals[${collectionIndex}].address.additionalDeliveryInformation" />  
            <label for="contactIndividuals.${collectionIndex}.address.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalGeographicalInformation}" maxlength="38" id="contactIndividuals.${collectionIndex}.address.additionalGeographicalInformation" name="contactIndividuals[${collectionIndex}].address.additionalGeographicalInformation" />
            <label for="contactIndividuals.${collectionIndex}.address.streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="contactIndividuals.${collectionIndex}.address.streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.streetNumber') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetNumber}" size="5" maxlength="5" id="contactIndividuals.${collectionIndex}.address.streetNumber" name="contactIndividuals[${collectionIndex}].address.streetNumber" />
            <input type="text" class="line2 required validate-streetName ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.streetName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetName}" maxlength="32" id="contactIndividuals.${collectionIndex}.address.streetName" name="contactIndividuals[${collectionIndex}].address.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <label for="contactIndividuals.${collectionIndex}.address.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.placeNameOrService') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.placeNameOrService}" maxlength="38" id="contactIndividuals.${collectionIndex}.address.placeNameOrService" name="contactIndividuals[${collectionIndex}].address.placeNameOrService" />
            <label for="contactIndividuals.${collectionIndex}.address.postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="contactIndividuals.${collectionIndex}.address.city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.postalCode') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.postalCode}" size="5" maxlength="5" id="contactIndividuals.${collectionIndex}.address.postalCode" name="contactIndividuals[${collectionIndex}].address.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.city') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.city}" maxlength="32" id="contactIndividuals.${collectionIndex}.address.city" name="contactIndividuals[${collectionIndex}].address.city" title="<g:message code="address.property.city.validationError" />" />
            <label for="contactIndividuals.${collectionIndex}.address.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].address.countryName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.countryName}" maxlength="38" id="contactIndividuals.${collectionIndex}.address.countryName" name="contactIndividuals[${collectionIndex}].address.countryName" />
            </div>
            

  
    <label for="contactIndividuals.${collectionIndex}.homePhone" class=""><g:message code="rarr.property.homePhone.label" />   <span><g:message code="rarr.property.homePhone.help" /></span></label>
            <input type="text" id="contactIndividuals.${collectionIndex}.homePhone" name="contactIndividuals[${collectionIndex}].homePhone" value="${currentCollectionItem?.homePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].homePhone') ? 'validation-failed' : ''}" title="<g:message code="rarr.property.homePhone.validationError" />"  maxlength="10" />
            

  
    <label for="contactIndividuals.${collectionIndex}.officePhone" class=""><g:message code="rarr.property.officePhone.label" />   <span><g:message code="rarr.property.officePhone.help" /></span></label>
            <input type="text" id="contactIndividuals.${collectionIndex}.officePhone" name="contactIndividuals[${collectionIndex}].officePhone" value="${currentCollectionItem?.officePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['contact'].invalidFields.contains('contactIndividuals['+collectionIndex+'].officePhone') ? 'validation-failed' : ''}" title="<g:message code="rarr.property.officePhone.validationError" />"  maxlength="10" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'contact'])}">
    ${message(code:'request.action.backToMainForm')}
  </a>
  
