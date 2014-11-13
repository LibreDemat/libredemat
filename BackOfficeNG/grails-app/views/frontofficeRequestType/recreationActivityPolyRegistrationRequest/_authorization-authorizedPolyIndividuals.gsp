
  <g:set var="currentCollectionItem" value="${rqt?.authorizedPolyIndividuals.size() > collectionIndex ? rqt.authorizedPolyIndividuals.get(collectionIndex) : null}" />
  <h4>
    ${message(code:'raprr.property.authorizedPolyIndividuals.label')}
    <span>
      <g:if test="${currentCollectionItem != null}">
        ${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        ${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  
    <label for="authorizedPolyIndividuals.${collectionIndex}.lastName" class="required"><g:message code="raprr.property.lastName.label" /> *  <span><g:message code="raprr.property.lastName.help" /></span></label>
            <input  type="text" id="authorizedPolyIndividuals.${collectionIndex}.lastName" name="authorizedPolyIndividuals[${collectionIndex}].lastName" value="${currentCollectionItem?.lastName?.toString()}" 
                    class="required  validate-lastName ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].lastName') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.lastName.validationError" />"  maxlength="38" style="width:99%" />
            

  
    <label for="authorizedPolyIndividuals.${collectionIndex}.firstName" class="required"><g:message code="raprr.property.firstName.label" /> *  <span><g:message code="raprr.property.firstName.help" /></span></label>
            <input  type="text" id="authorizedPolyIndividuals.${collectionIndex}.firstName" name="authorizedPolyIndividuals[${collectionIndex}].firstName" value="${currentCollectionItem?.firstName?.toString()}" 
                    class="required  validate-firstName ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].firstName') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.firstName.validationError" />"  maxlength="38" style="width:99%" />
            

  
    <label class="required"><g:message code="raprr.property.address.label" />&nbsp;* <span><g:message code="raprr.property.address.help" /></span></label>
            <div class="address required  ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address') ? 'validation-failed' : ''}">
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalDeliveryInformation}" maxlength="38" id="authorizedPolyIndividuals.${collectionIndex}.address.additionalDeliveryInformation" name="authorizedPolyIndividuals[${collectionIndex}].address.additionalDeliveryInformation" />  
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.additionalGeographicalInformation}" maxlength="38" id="authorizedPolyIndividuals.${collectionIndex}.address.additionalGeographicalInformation" name="authorizedPolyIndividuals[${collectionIndex}].address.additionalGeographicalInformation" />
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.streetNumber') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetNumber}" size="5" maxlength="5" id="authorizedPolyIndividuals.${collectionIndex}.address.streetNumber" name="authorizedPolyIndividuals[${collectionIndex}].address.streetNumber" />
            <input type="text" class="line2 required validate-streetName ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.streetName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.streetName}" maxlength="32" id="authorizedPolyIndividuals.${collectionIndex}.address.streetName" name="authorizedPolyIndividuals[${collectionIndex}].address.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.placeNameOrService') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.placeNameOrService}" maxlength="38" id="authorizedPolyIndividuals.${collectionIndex}.address.placeNameOrService" name="authorizedPolyIndividuals[${collectionIndex}].address.placeNameOrService" />
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.postalCode') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.postalCode}" size="5" maxlength="5" id="authorizedPolyIndividuals.${collectionIndex}.address.postalCode" name="authorizedPolyIndividuals[${collectionIndex}].address.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.city') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.city}" maxlength="32" id="authorizedPolyIndividuals.${collectionIndex}.address.city" name="authorizedPolyIndividuals[${collectionIndex}].address.city" title="<g:message code="address.property.city.validationError" />" />
            <label for="authorizedPolyIndividuals.${collectionIndex}.address.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].address.countryName') ? 'validation-failed' : ''}" value="${currentCollectionItem?.address?.countryName}" maxlength="38" id="authorizedPolyIndividuals.${collectionIndex}.address.countryName" name="authorizedPolyIndividuals[${collectionIndex}].address.countryName" />
            </div>
            

  
    <label for="authorizedPolyIndividuals.${collectionIndex}.homePhone" class=""><g:message code="raprr.property.homePhone.label" />   <span><g:message code="raprr.property.homePhone.help" /></span></label>
            <input  type="text" id="authorizedPolyIndividuals.${collectionIndex}.homePhone" name="authorizedPolyIndividuals[${collectionIndex}].homePhone" value="${currentCollectionItem?.homePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].homePhone') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.homePhone.validationError" />"  maxlength="10" style="width:99%" />
            

  
    <label for="authorizedPolyIndividuals.${collectionIndex}.officePhone" class=""><g:message code="raprr.property.officePhone.label" />   <span><g:message code="raprr.property.officePhone.help" /></span></label>
            <input  type="text" id="authorizedPolyIndividuals.${collectionIndex}.officePhone" name="authorizedPolyIndividuals[${collectionIndex}].officePhone" value="${currentCollectionItem?.officePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals['+collectionIndex+'].officePhone') ? 'validation-failed' : ''}" title="<g:message code="raprr.property.officePhone.validationError" />"  maxlength="10" style="width:99%" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'authorization'])}">
    ${message(code:'request.action.backToMainForm')}
  </a>
  
