


  
    
    
            <ul ${rqt.stepStates['reglements'].invalidFields.contains('acceptationReglementInterieur') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_acceptationReglementInterieur" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="acceptationReglementInterieur" name="acceptationReglementInterieur"
                           class="required  validate-acceptance"
                           title="${message(code:'hcrr.property.acceptationReglementInterieur.validationError')}"
                       ${rqt.acceptationReglementInterieur ? 'checked="checked"' : ''} value="true" />
                <label for="acceptationReglementInterieur" class="required">
                  ${message(code:'hcrr.property.acceptationReglementInterieur.label')}&nbsp;*
                  <g:if test="${availableRules.contains('acceptationReglementInterieur')}">
                  <a target="_blank"
                     href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'acceptationReglementInterieur']).encodeAsXML()}">
                    <span>${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>${message(code:'hcrr.property.acceptationReglementInterieur.help')}</span>
                </label>
              </li>
            </ul>
            

  

