


  
    
    
            <ul ${rqt.stepStates['reglements'].invalidFields.contains('acceptationReglementInterieur') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_acceptationReglementInterieur" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="acceptationReglementInterieur" name="acceptationReglementInterieur"
                           class="required  validate-acceptance"
                           title="${message(code:'pptwr.property.acceptationReglementInterieur.validationError')}"
                       ${rqt.acceptationReglementInterieur ? 'checked="checked"' : ''} value="true" />
                <label for="acceptationReglementInterieur" class="required">
                  ${message(code:'pptwr.property.acceptationReglementInterieur.label')}&nbsp;*
                  <g:if test="${availableRules.contains('acceptationReglementInterieur')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'acceptationReglementInterieur']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'pptwr.property.acceptationReglementInterieur.help')}</span>
                </label>
              </li>
            </ul>
            

  

  
    
        <div class="field-header-information">${message(code:'pptwr.property.observationsReglement.headerInformation')}</div>
    
    <label for="observationsReglement" class=""><g:message code="pptwr.property.observationsReglement.label" />   <span><g:message code="pptwr.property.observationsReglement.help" /></span></label>
            <textarea id="observationsReglement" name="observationsReglement" class="  validate-textarea ${rqt.stepStates['reglements'].invalidFields.contains('observationsReglement') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.observationsReglement.validationError" />" rows="3" cols=""  >${rqt.observationsReglement}</textarea>
            

  

