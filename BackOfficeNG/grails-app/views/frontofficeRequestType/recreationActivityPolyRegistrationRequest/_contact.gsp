


  
    <div class="collection  summary-box">
      <h4 class=""><g:message code="raprr.property.contactPolyIndividuals.label" /> 
        <span><g:message code="raprr.property.contactPolyIndividuals.help" /></span>
      </h4>

    <g:if test="${true && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit',            params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactPolyIndividuals',
                    'collectionIndex':(rqt.contactPolyIndividuals ? rqt.contactPolyIndividuals.size() : 0),
                    'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}"
           style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.contactPolyIndividuals}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="raprr.property.contactPolyIndividuals.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactPolyIndividuals', 'collectionIndex':index, 'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactPolyIndividuals', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="raprr.property.lastName.label" /></dt>
        <dd class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].lastName') ? 'validation-failed' : ''}">${it.lastName?.toString()}</dd>
    
        <dt><g:message code="raprr.property.firstName.label" /></dt>
        <dd class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].firstName') ? 'validation-failed' : ''}">${it.firstName?.toString()}</dd>
    
        <dt><g:message code="raprr.property.address.label" /></dt>
        
              <g:if test="${it.address}">
                <dd class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address') ? 'validation-failed' : ''}">
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.additionalDeliveryInformation') ? 'validation-failed' : ''}">${it.address?.additionalDeliveryInformation}</p>
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.additionalGeographicalInformation') ? 'validation-failed' : ''}">${it.address?.additionalGeographicalInformation}</p>
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.streetNumber') || rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.streetName') ? 'validation-failed' : ''}">${it.address?.streetNumber} ${it.address?.streetName}</p>
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.placeNameOrService') ? 'validation-failed' : ''}">${it.address?.placeNameOrService}</p>
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.postalCode') || rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.city') ? 'validation-failed' : ''}">${it.address?.postalCode} ${it.address?.city}</p>
                  <p class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].address.countryName') ? 'validation-failed' : ''}">${it.address?.countryName}</p>
                </dd>
              </g:if>
              
    
        <dt><g:message code="raprr.property.homePhone.label" /></dt>
        <dd class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].homePhone') ? 'validation-failed' : ''}">${it.homePhone?.toString()}</dd>
    
        <dt><g:message code="raprr.property.officePhone.label" /></dt>
        <dd class="${rqt.stepStates['contact'].invalidFields.contains('contactPolyIndividuals[' + index + '].officePhone') ? 'validation-failed' : ''}">${it.officePhone?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

