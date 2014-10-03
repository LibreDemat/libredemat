


  
    
              <label class="required">
                <g:message code="ycrr.property.childAlone.label" /> *
                <g:if test="${availableRules.contains('childAlone')}">
                  <a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'childAlone']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a>
                </g:if>
                <span><g:message code="ycrr.property.childAlone.help" /></span>
              </label>
              <ul class="yes-no required ${rqt.stepStates['rules'].invalidFields.contains('childAlone') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="childAlone_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="childAlone" ${it == rqt.childAlone ? 'checked="checked"': ''} />
                    <label for="childAlone_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

  
    
              <label class="required">
                <g:message code="ycrr.property.multiActivities.label" /> *
                <g:if test="${availableRules.contains('multiActivities')}">
                  <a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'multiActivities']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a>
                </g:if>
                <span><g:message code="ycrr.property.multiActivities.help" /></span>
              </label>
              <ul class="yes-no required ${rqt.stepStates['rules'].invalidFields.contains('multiActivities') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="multiActivities_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="multiActivities" ${it == rqt.multiActivities ? 'checked="checked"': ''} />
                    <label for="multiActivities_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

  
    
              <label class="required">
                <g:message code="ycrr.property.rulesAcceptance.label" /> *
                <g:if test="${availableRules.contains('rulesAcceptance')}">
                  <a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'rulesAcceptance']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a>
                </g:if>
                <span><g:message code="ycrr.property.rulesAcceptance.help" /></span>
              </label>
              <ul class="yes-no required ${rqt.stepStates['rules'].invalidFields.contains('rulesAcceptance') ? 'validation-failed' : ''}">
                <g:each in="${[true,false]}">
                  <li>
                    <input type="radio" id="rulesAcceptance_${it ? 'yes' : 'no'}" class="required  validate-acceptance" title="" value="${it}" name="rulesAcceptance" ${it == rqt.rulesAcceptance ? 'checked="checked"': ''} />
                    <label for="rulesAcceptance_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            

  

