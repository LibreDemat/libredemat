


  
    
    
            <label for="subjectId" class="required">
              <g:message code="srwrcr.property.subject.label" /> *
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
            <select id="subjectId" name="subjectId" <g:if test="${isEdition || rqt.stepStates[currentStep].state == 'complete'}">disabled="disabled"</g:if> class="required validate-not-first  ${rqt.stepStates['registration'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="request.property.subject.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${subjects}">
                <option value="${it.key}" ${it.key == rqt.subjectId ? 'selected="selected"': ''}>${it.value}</option>
              </g:each>
            </select>
            

  

  
    
    <label for="section" class="required"><g:message code="srwrcr.property.section.label" /> *  <span><g:message code="srwrcr.property.section.help" /></span></label>
            <select id="section" name="section" class="required  validate-not-first ${rqt.stepStates['registration'].invalidFields.contains('section') ? 'validation-failed' : ''}" title="<g:message code="srwrcr.property.section.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['BEFORE_FIRST_SECTION','FIRST_SECTION','SECOND_SECTION','THIRD_SECTION','C_P','C_E1','C_E2','C_M1','C_M2','C_L_I_S_S','UNKNOWN']}">
                <option value="${it}" ${it == rqt.section?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="srwrcr.property.section" /></option>
              </g:each>
            </select>
            

  

  
    <fieldset class="required" id="TheSchool">
    <legend><g:message code="srwrcr.property.theSchool.label" /></legend>
    
      
      <label for="idSchoolName" class="required"><g:message code="srwrcr.property.idSchoolName.label" /> *  <span><g:message code="srwrcr.property.idSchoolName.help" /></span></label>
            <input  type="text" id="idSchoolName"
                   name="idSchoolName"
                   value="${rqt.idSchoolName?.toString()}"
                   class="required  validate-string ${rqt.stepStates['registration'].invalidFields.contains('idSchoolName') ? 'validation-failed' : ''}"
                   title="<g:message code="srwrcr.property.idSchoolName.validationError" />"   />
            

    
      
      <label for="labelSchoolName" class="required"><g:message code="srwrcr.property.labelSchoolName.label" /> *  <span><g:message code="srwrcr.property.labelSchoolName.help" /></span></label>
            <input  type="text" id="labelSchoolName"
                   name="labelSchoolName"
                   value="${rqt.labelSchoolName?.toString()}"
                   class="required  validate-string ${rqt.stepStates['registration'].invalidFields.contains('labelSchoolName') ? 'validation-failed' : ''}"
                   title="<g:message code="srwrcr.property.labelSchoolName.validationError" />"   />
            

    
    </fieldset>
  

