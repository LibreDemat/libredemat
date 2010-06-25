


  
    
              <label class="required">
                <g:message code="rarr.property.rulesAndRegulationsAcceptance.label" /> *
                <g:if test="${availableRules.contains('rulesAndRegulationsAcceptance')}">
                  <p><a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':requestTypeLabel, 'filename':'rulesAndRegulationsAcceptance']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a></p>
                </g:if>
                <span><g:message code="rarr.property.rulesAndRegulationsAcceptance.help" /></span>
              </label>
              <ul class="yes-no required ${stepStates != null && stepStates['rules']?.invalidFields.contains('rulesAndRegulationsAcceptance') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="rulesAndRegulationsAcceptance_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="rulesAndRegulationsAcceptance" ${it == rqt.rulesAndRegulationsAcceptance ? 'checked="checked"': ''} />
                    <label for="rulesAndRegulationsAcceptance_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

  
    
              <label class="required">
                <g:message code="rarr.property.classTripPermission.label" /> *
                <g:if test="${availableRules.contains('classTripPermission')}">
                  <p><a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':requestTypeLabel, 'filename':'classTripPermission']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a></p>
                </g:if>
                <span><g:message code="rarr.property.classTripPermission.help" /></span>
              </label>
              <ul class="yes-no required ${stepStates != null && stepStates['rules']?.invalidFields.contains('classTripPermission') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="classTripPermission_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="classTripPermission" ${it == rqt.classTripPermission ? 'checked="checked"': ''} />
                    <label for="classTripPermission_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

  
    
              <label class="required">
                <g:message code="rarr.property.childPhotoExploitationPermission.label" /> *
                <g:if test="${availableRules.contains('childPhotoExploitationPermission')}">
                  <p><a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':requestTypeLabel, 'filename':'childPhotoExploitationPermission']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a></p>
                </g:if>
                <span><g:message code="rarr.property.childPhotoExploitationPermission.help" /></span>
              </label>
              <ul class="yes-no required ${stepStates != null && stepStates['rules']?.invalidFields.contains('childPhotoExploitationPermission') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="childPhotoExploitationPermission_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="childPhotoExploitationPermission" ${it == rqt.childPhotoExploitationPermission ? 'checked="checked"': ''} />
                    <label for="childPhotoExploitationPermission_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

  
    
              <label class="required">
                <g:message code="rarr.property.hospitalizationPermission.label" /> *
                <g:if test="${availableRules.contains('hospitalizationPermission')}">
                  <p><a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':requestTypeLabel, 'filename':'hospitalizationPermission']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a></p>
                </g:if>
                <span><g:message code="rarr.property.hospitalizationPermission.help" /></span>
              </label>
              <ul class="yes-no required ${stepStates != null && stepStates['rules']?.invalidFields.contains('hospitalizationPermission') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="hospitalizationPermission_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="hospitalizationPermission" ${it == rqt.hospitalizationPermission ? 'checked="checked"': ''} />
                    <label for="hospitalizationPermission_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

