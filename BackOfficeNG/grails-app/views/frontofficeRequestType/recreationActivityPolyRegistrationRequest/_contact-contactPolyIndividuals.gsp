
  <g:set var="currentCollectionItem" value="${rqt?.contactPolyIndividuals.size() > collectionIndex ? rqt.contactPolyIndividuals.get(collectionIndex) : null}" />
  <h4>
    ${message(code:'raprr.property.contactPolyIndividuals.label')}
    <span>
      <g:if test="${currentCollectionItem != null}">
        ${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        ${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  
    <label for="contactPolyIndividuals.${collectionIndex}.lastName" class="required"><g:message code="raprr.property.lastName.label" /> *  <span><g:message code="raprr.property.lastName.help" /></span></label>
            <input  type="text" id="contactPolyIndividuals.${collectionIndex}.lastName" name="contactPolyIndividuals[${collectionIndex}].lastName" value="${currentCollectionItem?.lastName?.toString()}" 
                    class="required  validate-lastName ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].lastName') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.lastName.validationError" />"  maxlength="38" style="width:99%" />
            

  
    <label for="contactPolyIndividuals.${collectionIndex}.firstName" class="required"><g:message code="raprr.property.firstName.label" /> *  <span><g:message code="raprr.property.firstName.help" /></span></label>
            <input  type="text" id="contactPolyIndividuals.${collectionIndex}.firstName" name="contactPolyIndividuals[${collectionIndex}].firstName" value="${currentCollectionItem?.firstName?.toString()}" 
                    class="required  validate-firstName ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].firstName') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.firstName.validationError" />"  maxlength="38" style="width:99%" />
            

  
    <label class="required"><g:message code="raprr.property.address.label" />&nbsp;* <span><g:message code="raprr.property.address.help" /></span></label>
            <div class="address required  ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address') ? 'validation-failed' : ''}">
            <label for="contactPolyIndividuals.${collectionIndex}.address.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalDeliveryInformation}" maxlength="38" id="contactPolyIndividuals.${collectionIndex}.address.additionalDeliveryInformation" name="contactPolyIndividuals[${collectionIndex}].address.additionalDeliveryInformation" />  
            <label for="contactPolyIndividuals.${collectionIndex}.address.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalGeographicalInformation}" maxlength="38" id="contactPolyIndividuals.${collectionIndex}.address.additionalGeographicalInformation" name="contactPolyIndividuals[${collectionIndex}].address.additionalGeographicalInformation" />
            <label for="contactPolyIndividuals.${collectionIndex}.address.streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="contactPolyIndividuals.${collectionIndex}.address.streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.streetNumber') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetNumber}" size="5" maxlength="5" id="contactPolyIndividuals.${collectionIndex}.address.streetNumber" name="contactPolyIndividuals[${collectionIndex}].address.streetNumber" />
            <input type="text" class="line2 required validate-streetName ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.streetName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetName}" maxlength="32" id="contactPolyIndividuals.${collectionIndex}.address.streetName" name="contactPolyIndividuals[${collectionIndex}].address.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <label for="contactPolyIndividuals.${collectionIndex}.address.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.placeNameOrService') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.placeNameOrService}" maxlength="38" id="contactPolyIndividuals.${collectionIndex}.address.placeNameOrService" name="contactPolyIndividuals[${collectionIndex}].address.placeNameOrService" />
            <label for="contactPolyIndividuals.${collectionIndex}.address.postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="contactPolyIndividuals.${collectionIndex}.address.city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.postalCode') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.postalCode}" size="5" maxlength="5" id="contactPolyIndividuals.${collectionIndex}.address.postalCode" name="contactPolyIndividuals[${collectionIndex}].address.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.city') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.city}" maxlength="32" id="contactPolyIndividuals.${collectionIndex}.address.city" name="contactPolyIndividuals[${collectionIndex}].address.city" title="<g:message code="address.property.city.validationError" />" />
            <label for="contactPolyIndividuals.${collectionIndex}.address.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].address.countryName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.countryName}" maxlength="38" id="contactPolyIndividuals.${collectionIndex}.address.countryName" name="contactPolyIndividuals[${collectionIndex}].address.countryName" />
            </div>
            

  
    <label for="contactPolyIndividuals.${collectionIndex}.homePhone" class=""><g:message code="raprr.property.homePhone.label" />   <span><g:message code="raprr.property.homePhone.help" /></span></label>
            <input  type="text" id="contactPolyIndividuals.${collectionIndex}.homePhone" name="contactPolyIndividuals[${collectionIndex}].homePhone" value="${currentCollectionItem?.homePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].homePhone') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.homePhone.validationError" />"  maxlength="10" style="width:99%" />
            

  
    <label for="contactPolyIndividuals.${collectionIndex}.officePhone" class=""><g:message code="raprr.property.officePhone.label" />   <span><g:message code="raprr.property.officePhone.help" /></span></label>
            <input  type="text" id="contactPolyIndividuals.${collectionIndex}.officePhone" name="contactPolyIndividuals[${collectionIndex}].officePhone" value="${currentCollectionItem?.officePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals['+collectionIndex+'].officePhone') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.officePhone.validationError" />"  maxlength="10" style="width:99%" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'contact'])}">
    ${message(code:'request.action.backToMainForm')}
  </a>
  
