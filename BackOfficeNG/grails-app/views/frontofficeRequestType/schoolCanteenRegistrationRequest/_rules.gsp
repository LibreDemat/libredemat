


  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('hospitalizationPermission') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_hospitalizationPermission" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="hospitalizationPermission" name="hospitalizationPermission"
                           class="  "
                           title="${message(code:'scrr.property.hospitalizationPermission.validationError')}"
                       ${rqt.hospitalizationPermission ? 'checked="checked"' : ''} value="true" />
                <label for="hospitalizationPermission" class="">
                  ${message(code:'scrr.property.hospitalizationPermission.label')}
                  <g:if test="${availableRules.contains('hospitalizationPermission')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'hospitalizationPermission']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'scrr.property.hospitalizationPermission.help')}</span>
                </label>
              </li>
            </ul>
            

  

  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('rulesAndRegulationsAcceptance') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_rulesAndRegulationsAcceptance" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="rulesAndRegulationsAcceptance" name="rulesAndRegulationsAcceptance"
                           class="required  validate-acceptance"
                           title="${message(code:'scrr.property.rulesAndRegulationsAcceptance.validationError')}"
                       ${rqt.rulesAndRegulationsAcceptance ? 'checked="checked"' : ''} value="true" />
                <label for="rulesAndRegulationsAcceptance" class="required">
                  ${message(code:'scrr.property.rulesAndRegulationsAcceptance.label')}&nbsp;*
                  <g:if test="${availableRules.contains('rulesAndRegulationsAcceptance')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'rulesAndRegulationsAcceptance']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'scrr.property.rulesAndRegulationsAcceptance.help')}</span>
                </label>
              </li>
            </ul>
            

  

