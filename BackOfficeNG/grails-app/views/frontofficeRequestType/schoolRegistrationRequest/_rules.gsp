


  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('rulesAndRegulationsAcceptance') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_rulesAndRegulationsAcceptance" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="rulesAndRegulationsAcceptance" name="rulesAndRegulationsAcceptance"
                           class="required  validate-acceptance"
                           title="${message(code:'srr.property.rulesAndRegulationsAcceptance.validationError')}"
                       ${rqt.rulesAndRegulationsAcceptance ? 'checked="checked"' : ''} value="true" />
                <label for="rulesAndRegulationsAcceptance" class="required">
                  ${message(code:'srr.property.rulesAndRegulationsAcceptance.label')}&nbsp;*
                  <g:if test="${availableRules.contains('rulesAndRegulationsAcceptance')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'rulesAndRegulationsAcceptance']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'srr.property.rulesAndRegulationsAcceptance.help')}</span>
                </label>
              </li>
            </ul>
            

  

