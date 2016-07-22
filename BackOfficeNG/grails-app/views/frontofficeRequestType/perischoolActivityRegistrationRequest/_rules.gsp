


  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('rulesAndRegulationsAcceptance') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_rulesAndRegulationsAcceptance" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="rulesAndRegulationsAcceptance" name="rulesAndRegulationsAcceptance"
                           class="required  validate-acceptance"
                           title="${message(code:'parr.property.rulesAndRegulationsAcceptance.validationError')}"
                       ${rqt.rulesAndRegulationsAcceptance ? 'checked="checked"' : ''} value="true" />
                <label for="rulesAndRegulationsAcceptance" class="required">
                  ${message(code:'parr.property.rulesAndRegulationsAcceptance.label')}&nbsp;*
                  <g:if test="${availableRules.contains('rulesAndRegulationsAcceptance')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'rulesAndRegulationsAcceptance']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'parr.property.rulesAndRegulationsAcceptance.help')}</span>
                </label>
              </li>
            </ul>
            

  

  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('classTripPermission') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_classTripPermission" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="classTripPermission" name="classTripPermission"
                           class="  "
                           title="${message(code:'parr.property.classTripPermission.validationError')}"
                       ${rqt.classTripPermission ? 'checked="checked"' : ''} value="true" />
                <label for="classTripPermission" class="">
                  ${message(code:'parr.property.classTripPermission.label')}
                  <g:if test="${availableRules.contains('classTripPermission')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'classTripPermission']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'parr.property.classTripPermission.help')}</span>
                </label>
              </li>
            </ul>
            

  

  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('childPhotoExploitationPermission') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_childPhotoExploitationPermission" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="childPhotoExploitationPermission" name="childPhotoExploitationPermission"
                           class="  "
                           title="${message(code:'parr.property.childPhotoExploitationPermission.validationError')}"
                       ${rqt.childPhotoExploitationPermission ? 'checked="checked"' : ''} value="true" />
                <label for="childPhotoExploitationPermission" class="">
                  ${message(code:'parr.property.childPhotoExploitationPermission.label')}
                  <g:if test="${availableRules.contains('childPhotoExploitationPermission')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'childPhotoExploitationPermission']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'parr.property.childPhotoExploitationPermission.help')}</span>
                </label>
              </li>
            </ul>
            

  

  
    
    
            <ul ${rqt.stepStates['rules'].invalidFields.contains('hospitalizationPermission') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_hospitalizationPermission" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="hospitalizationPermission" name="hospitalizationPermission"
                           class="  "
                           title="${message(code:'parr.property.hospitalizationPermission.validationError')}"
                       ${rqt.hospitalizationPermission ? 'checked="checked"' : ''} value="true" />
                <label for="hospitalizationPermission" class="">
                  ${message(code:'parr.property.hospitalizationPermission.label')}
                  <g:if test="${availableRules.contains('hospitalizationPermission')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'hospitalizationPermission']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'parr.property.hospitalizationPermission.help')}</span>
                </label>
              </li>
            </ul>
            

  

