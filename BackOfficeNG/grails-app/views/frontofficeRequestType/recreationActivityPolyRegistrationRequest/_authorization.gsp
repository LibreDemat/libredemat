


  
    <div class="collection  summary-box">
      <h4 class=""><g:message code="raprr.property.authorizedPolyIndividuals.label" /> 
        <span><g:message code="raprr.property.authorizedPolyIndividuals.help" /></span>
      </h4>

    <g:if test="${true && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'authorization', 'currentCollection':'authorizedPolyIndividuals', 'collectionIndex':(rqt.authorizedPolyIndividuals ? rqt.authorizedPolyIndividuals.size() : 0)])}" style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.authorizedPolyIndividuals}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="raprr.property.authorizedPolyIndividuals.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'authorization', 'currentCollection':'authorizedPolyIndividuals', 'collectionIndex':index])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'authorization', 'currentCollection':'authorizedPolyIndividuals', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="raprr.property.lastName.label" /></dt>
        <dd class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].lastName') ? 'validation-failed' : ''}">${it.lastName?.toString()}</dd>
    
        <dt><g:message code="raprr.property.firstName.label" /></dt>
        <dd class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].firstName') ? 'validation-failed' : ''}">${it.firstName?.toString()}</dd>
    
        <dt><g:message code="raprr.property.address.label" /></dt>
        
              <g:if test="${it.address}">
                <dd class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address') ? 'validation-failed' : ''}">
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.additionalDeliveryInformation') ? 'validation-failed' : ''}">${it.address?.additionalDeliveryInformation}</p>
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.additionalGeographicalInformation') ? 'validation-failed' : ''}">${it.address?.additionalGeographicalInformation}</p>
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.streetNumber') || rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.streetName') ? 'validation-failed' : ''}">${it.address?.streetNumber} ${it.address?.streetName}</p>
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.placeNameOrService') ? 'validation-failed' : ''}">${it.address?.placeNameOrService}</p>
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.postalCode') || rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.city') ? 'validation-failed' : ''}">${it.address?.postalCode} ${it.address?.city}</p>
                  <p class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].address.countryName') ? 'validation-failed' : ''}">${it.address?.countryName}</p>
                </dd>
              </g:if>
              
    
        <dt><g:message code="raprr.property.homePhone.label" /></dt>
        <dd class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].homePhone') ? 'validation-failed' : ''}">${it.homePhone?.toString()}</dd>
    
        <dt><g:message code="raprr.property.officePhone.label" /></dt>
        <dd class="${rqt.stepStates['authorization'].invalidFields.contains('authorizedPolyIndividuals[' + index + '].officePhone') ? 'validation-failed' : ''}">${it.officePhone?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

