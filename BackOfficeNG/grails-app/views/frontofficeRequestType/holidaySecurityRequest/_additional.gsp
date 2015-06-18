


  
    
    <label class="required"><g:message code="hsr.property.alarm.label" /> *  <span><g:message code="hsr.property.alarm.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['additional'].invalidFields.contains('alarm') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="alarm_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="alarm" ${it == rqt.alarm ? 'checked="checked"': ''} />
                <label for="alarm_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label class="required"><g:message code="hsr.property.light.label" /> *  <span><g:message code="hsr.property.light.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['additional'].invalidFields.contains('light') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="light_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="light" ${it == rqt.light ? 'checked="checked"': ''} />
                <label for="light_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label class="required"><g:message code="hsr.property.isAnimalOwner.label" /> *  <span><g:message code="hsr.property.isAnimalOwner.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['additional'].invalidFields.contains('isAnimalOwner') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="isAnimalOwner_${it ? 'yes' : 'no'}" class="required condition-isAnimalOwner-trigger  validate-one-required boolean" title="" value="${it}" name="isAnimalOwner" ${it == rqt.isAnimalOwner ? 'checked="checked"': ''} />
                <label for="isAnimalOwner_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label for="animalInformation" class="required condition-isAnimalOwner-filled"><g:message code="hsr.property.animalInformation.label" /> *  <span><g:message code="hsr.property.animalInformation.help" /></span></label>
            <input  type="text" id="animalInformation"
                   name="animalInformation"
                   value="${rqt.animalInformation?.toString()}"
                   class="required condition-isAnimalOwner-filled  validate-string ${rqt.stepStates['additional'].invalidFields.contains('animalInformation') ? 'validation-failed' : ''}"
                   title="<g:message code="hsr.property.animalInformation.validationError" />"   />
            

  

  
    
    <label class="required"><g:message code="hsr.property.isSecurityCompany.label" /> *  <span><g:message code="hsr.property.isSecurityCompany.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['additional'].invalidFields.contains('isSecurityCompany') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="isSecurityCompany_${it ? 'yes' : 'no'}" class="required condition-isSecurityCompany-trigger  validate-one-required boolean" title="" value="${it}" name="isSecurityCompany" ${it == rqt.isSecurityCompany ? 'checked="checked"': ''} />
                <label for="isSecurityCompany_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label for="securityCompanyName" class="required condition-isSecurityCompany-filled"><g:message code="hsr.property.securityCompanyName.label" /> *  <span><g:message code="hsr.property.securityCompanyName.help" /></span></label>
            <input  type="text" id="securityCompanyName"
                   name="securityCompanyName"
                   value="${rqt.securityCompanyName?.toString()}"
                   class="required condition-isSecurityCompany-filled  validate-string ${rqt.stepStates['additional'].invalidFields.contains('securityCompanyName') ? 'validation-failed' : ''}"
                   title="<g:message code="hsr.property.securityCompanyName.validationError" />"   />
            

  

  
    
    <label for="securityCompanyAddress" class="required condition-isSecurityCompany-filled"><g:message code="hsr.property.securityCompanyAddress.label" /> *  <span><g:message code="hsr.property.securityCompanyAddress.help" /></span></label>
            <input  type="text" id="securityCompanyAddress"
                   name="securityCompanyAddress"
                   value="${rqt.securityCompanyAddress?.toString()}"
                   class="required condition-isSecurityCompany-filled  validate-string ${rqt.stepStates['additional'].invalidFields.contains('securityCompanyAddress') ? 'validation-failed' : ''}"
                   title="<g:message code="hsr.property.securityCompanyAddress.validationError" />"   />
            

  

  
    
    <label for="securityCompanyTelephone" class="required condition-isSecurityCompany-filled"><g:message code="hsr.property.securityCompanyTelephone.label" /> *  <span><g:message code="hsr.property.securityCompanyTelephone.help" /></span></label>
            <input  type="text" id="securityCompanyTelephone"
                   name="securityCompanyTelephone"
                   value="${rqt.securityCompanyTelephone?.toString()}"
                   class="required condition-isSecurityCompany-filled  validate-phone ${rqt.stepStates['additional'].invalidFields.contains('securityCompanyTelephone') ? 'validation-failed' : ''}"
                   title="<g:message code="hsr.property.securityCompanyTelephone.validationError" />"  maxlength="10" />
            

  

