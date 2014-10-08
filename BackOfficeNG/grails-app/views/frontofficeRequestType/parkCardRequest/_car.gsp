


  
    <label for="parkResident" class=""><g:message code="pcr.property.parkResident.label" />   <span><g:message code="pcr.property.parkResident.help" /></span></label>
            <input disabled=disabled type="text" id="parkResident" name="parkResident" value="${rqt.parkResident?.toString()}" 
                    class="  validate-string ${rqt.stepStates['car'].invalidFields.contains('parkResident') ? 'validation-failed' : ''}" title="<g:message code="pcr.property.parkResident.validationError" />"   />
            

  

  
    <label for="informationCardLimitRest" class=""><g:message code="pcr.property.informationCardLimitRest.label" />   <span><g:message code="pcr.property.informationCardLimitRest.help" /></span></label>
            <input disabled=disabled type="text" id="informationCardLimitRest" name="informationCardLimitRest" value="${rqt.informationCardLimitRest?.toString()}" 
                    class="  validate-string ${rqt.stepStates['car'].invalidFields.contains('informationCardLimitRest') ? 'validation-failed' : ''}" title="<g:message code="pcr.property.informationCardLimitRest.validationError" />"   />
            

  

  
    <div class="collection required summary-box">
      <h4 class="required"><g:message code="pcr.property.parkImatriculation.label" /> 
        <span><g:message code="pcr.property.parkImatriculation.help" /></span>
      </h4>

    <g:if test="${rqt.getCardNumberLimit() > (collectionIndexAdded + rqt.getParkImatriculation()?.size()) && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'car', 'currentCollection':'parkImatriculation', 'collectionIndex':(rqt.parkImatriculation ? rqt.parkImatriculation.size() : 0)])}" style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.parkImatriculation}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="pcr.property.parkImatriculation.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'car', 'currentCollection':'parkImatriculation', 'collectionIndex':index])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'car', 'currentCollection':'parkImatriculation', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="pcr.property.immatriculation.label" /></dt>
        <dd class="${rqt.stepStates['car'].invalidFields.contains('parkImatriculation[' + index + '].immatriculation') ? 'validation-failed' : ''}">${it.immatriculation?.toString()}</dd>
    
        <dt><g:message code="pcr.property.tarif.label" /></dt>
        <dd class="${rqt.stepStates['car'].invalidFields.contains('parkImatriculation[' + index + '].tarif') ? 'validation-failed' : ''}">${it.tarif?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

  
    <label for="paymentTotal" class=""><g:message code="pcr.property.paymentTotal.label" />   <span><g:message code="pcr.property.paymentTotal.help" /></span></label>
            <input disabled=disabled type="text" id="paymentTotal" name="paymentTotal" value="${rqt.paymentTotal?.toString()}" 
                    class="  validate-string ${rqt.stepStates['car'].invalidFields.contains('paymentTotal') ? 'validation-failed' : ''}" title="<g:message code="pcr.property.paymentTotal.validationError" />"   />
            

  

