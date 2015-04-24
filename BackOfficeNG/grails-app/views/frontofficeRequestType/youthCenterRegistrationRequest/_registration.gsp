


  
    
    
            <label for="subjectId" class="required">
              <g:message code="ycrr.property.subject.label" /> *
              <span><g:message code="request.property.subject.help" /></span>
              <g:if test="${rqt.stepStates[currentStep].state != 'complete' && !rqt.requestType.getStepAccountCompletion()}">
                <g:if test="${!org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_NONE.equals(subjectPolicy)}">
                  <g:if test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_ADULT.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:if>
                  <g:elseif test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_CHILD.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:elseif>
                  <g:elseif test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_INDIVIDUAL.equals(subjectPolicy)}">
                    <span>(<a id="addAdultLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addAdult" /></a>
                    <g:message code="message.or" />
                    <a id="addChildLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addChild" /></a>)</span>
                  </g:elseif>
                </g:if>
              </g:if>
            </label>
            <select id="subjectId" name="subjectId" <g:if test="${isEdition || rqt.stepStates[currentStep].state == 'complete'}">disabled="disabled"</g:if> class="required validate-not-first autofill-subjectFilling-trigger ${rqt.stepStates['registration'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="request.property.subject.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${subjects}">
                <option value="${it.key}" ${it.key == rqt.subjectId ? 'selected="selected"': ''}>${it.value}</option>
              </g:each>
            </select>
            

  

  
    
    <label class=""><g:message code="ycrr.property.subjectChoiceBirthDate.label" />   <span><g:message code="ycrr.property.subjectChoiceBirthDate.help" /></span></label>
            <div class="date  autofill-subjectFilling-listener-BirthDate validate-date  autofill-subjectFilling-listener-BirthDate">
              <select class="day ${rqt.stepStates['registration'].invalidFields.contains('subjectChoiceBirthDate') ? 'validation-failed' : ''}"
                id="subjectChoiceBirthDate_day"
                name="subjectChoiceBirthDate_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..31}">
                  <option value="${it}"
                    <g:if test="${rqt.subjectChoiceBirthDate?.date == it
                      || (rqt.subjectChoiceBirthDate == null && params['subjectChoiceBirthDate_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    ${it}
                  </option>
                </g:each>
              </select>
              <select class="month ${rqt.stepStates['registration'].invalidFields.contains('subjectChoiceBirthDate') ? 'validation-failed' : ''}"
                id="subjectChoiceBirthDate_month"
                name="subjectChoiceBirthDate_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..12}">
                  <option value="${it}"
                    <g:if test="${rqt.subjectChoiceBirthDate?.month == (it - 1)
                      || (rqt.subjectChoiceBirthDate == null && params['subjectChoiceBirthDate_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year ${rqt.stepStates['registration'].invalidFields.contains('subjectChoiceBirthDate') ? 'validation-failed' : ''}"
                id="subjectChoiceBirthDate_year"
                name="subjectChoiceBirthDate_year"
                value="${rqt.subjectChoiceBirthDate ? rqt.subjectChoiceBirthDate.year + 1900 : params['subjectChoiceBirthDate_year']}"
                title="<g:message code="ycrr.property.subjectChoiceBirthDate.validationError" />" />
            </div>
            

  

  
    
    <label for="subjectChoiceMobilePhone" class=""><g:message code="ycrr.property.subjectChoiceMobilePhone.label" />   <span><g:message code="ycrr.property.subjectChoiceMobilePhone.help" /></span></label>
            <input  type="text" id="subjectChoiceMobilePhone" name="subjectChoiceMobilePhone" value="${rqt.subjectChoiceMobilePhone?.toString()}" 
                    class="  validate-phone ${rqt.stepStates['registration'].invalidFields.contains('subjectChoiceMobilePhone') ? 'validation-failed' : ''}" title="<g:message code="ycrr.property.subjectChoiceMobilePhone.validationError" />"  maxlength="10" />
            

  

  
    
    <label for="subjectChoiceEmail" class=""><g:message code="ycrr.property.subjectChoiceEmail.label" />   <span><g:message code="ycrr.property.subjectChoiceEmail.help" /></span></label>
            <input  type="text" id="subjectChoiceEmail" name="subjectChoiceEmail" value="${rqt.subjectChoiceEmail?.toString()}" 
                    class="  validate-email ${rqt.stepStates['registration'].invalidFields.contains('subjectChoiceEmail') ? 'validation-failed' : ''}" title="<g:message code="ycrr.property.subjectChoiceEmail.validationError" />"   />
            

  

  
    
    <label class="required"><g:message code="ycrr.property.isFirstRegistration.label" /> *  <span><g:message code="ycrr.property.isFirstRegistration.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['registration'].invalidFields.contains('isFirstRegistration') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="isFirstRegistration_${it ? 'yes' : 'no'}" class="required condition-isFirstRegistration-trigger  validate-one-required boolean" title="" value="${it}" name="isFirstRegistration" ${it == rqt.isFirstRegistration ? 'checked="checked"': ''} />
                <label for="isFirstRegistration_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label class="required condition-isFirstRegistration-filled"><g:message code="ycrr.property.firstRegistrationDate.label" /> *  <span><g:message code="ycrr.property.firstRegistrationDate.help" /></span></label>
            <div class="date required condition-isFirstRegistration-filled  validate-date required condition-isFirstRegistration-filled ">
              <select class="day ${rqt.stepStates['registration'].invalidFields.contains('firstRegistrationDate') ? 'validation-failed' : ''}"
                id="firstRegistrationDate_day"
                name="firstRegistrationDate_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..31}">
                  <option value="${it}"
                    <g:if test="${rqt.firstRegistrationDate?.date == it
                      || (rqt.firstRegistrationDate == null && params['firstRegistrationDate_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    ${it}
                  </option>
                </g:each>
              </select>
              <select class="month ${rqt.stepStates['registration'].invalidFields.contains('firstRegistrationDate') ? 'validation-failed' : ''}"
                id="firstRegistrationDate_month"
                name="firstRegistrationDate_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..12}">
                  <option value="${it}"
                    <g:if test="${rqt.firstRegistrationDate?.month == (it - 1)
                      || (rqt.firstRegistrationDate == null && params['firstRegistrationDate_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year ${rqt.stepStates['registration'].invalidFields.contains('firstRegistrationDate') ? 'validation-failed' : ''}"
                id="firstRegistrationDate_year"
                name="firstRegistrationDate_year"
                value="${rqt.firstRegistrationDate ? rqt.firstRegistrationDate.year + 1900 : params['firstRegistrationDate_year']}"
                title="<g:message code="ycrr.property.firstRegistrationDate.validationError" />" />
            </div>
            

  

  
    
    <label for="firstRegistrationNumeroAdherent" class="required condition-isFirstRegistration-filled"><g:message code="ycrr.property.firstRegistrationNumeroAdherent.label" /> *  <span><g:message code="ycrr.property.firstRegistrationNumeroAdherent.help" /></span></label>
            <input  type="text" id="firstRegistrationNumeroAdherent" name="firstRegistrationNumeroAdherent" value="${rqt.firstRegistrationNumeroAdherent?.toString()}" 
                    class="required condition-isFirstRegistration-filled  validate-string ${rqt.stepStates['registration'].invalidFields.contains('firstRegistrationNumeroAdherent') ? 'validation-failed' : ''}" title="<g:message code="ycrr.property.firstRegistrationNumeroAdherent.validationError" />"   />
            

  

