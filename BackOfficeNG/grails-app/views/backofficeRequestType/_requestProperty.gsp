<div class="mainbox mainbox-yellow" id="requestTypeRequestProperty">
  <h2><g:message code="requestType.configuration.requestProperty" /></h2>
  <form class="edit" action="${createLink(action:"saveRequestProperty")}" id="requestTypeRequestPropertyForm" method="post">
      <div class="error" id="dialogRequestTypeRequestPropertyFormError"></div>
        <p class="field">
            <label for="subjectPolicy">${message(code:'requestType.property.subjectPolicy')}</label>
            <g:if test="${subjectPolicy? (subjectPolicy.equals('SUBJECT_POLICY_NONE')) :true}">
                <label>${message(code:'requestType.property.subjectPolicy.none')}</label>
            </g:if>
            <g:if test="${subjectPolicy? (!subjectPolicy.equals('SUBJECT_POLICY_NONE')) :false}">
              <select name="subjectPolicy" id="subjectPolicy" >
                  <option value="SUBJECT_POLICY_INDIVIDUAL" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_INDIVIDUAL") ? 'selected' : '') : ''}>
                    ${message(code:'requestType.property.subjectPolicy.individual')}
                    </option>
                  <option value="SUBJECT_POLICY_ADULT" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_ADULT") ? 'selected' : '') : ''}>
                    ${message(code:'requestType.property.subjectPolicy.adult')}
                  </option>
                  <option value="SUBJECT_POLICY_CHILD" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_CHILD") ? 'selected' : '') : ''}>
                    ${message(code:'requestType.property.subjectPolicy.child')}
                  </option>
              </select>
            </g:if>
        </p>
        <p class="field">
          <label for="isOfRegistrationKind" style="margin: 0;">${message(code:'requestType.property.isOfRegistrationKind')}</label>
          <input type="checkbox" value="active" ${isOfRegistrationKind ? 'checked' : ''} name="isOfRegistrationKind" id="isOfRegistrationKind"/>
        </p>
        <p class="field">
          <label for="supportMultiple" style="margin: 0;">${message(code:'requestType.property.supportMultiple')}</label>
          <input ${!isOfRegistrationKind ? 'disabled="disabled"' : ''} type="checkbox" value="active" ${supportMultiple ? 'checked' : ''} name="supportMultiple" id="supportMultiple"/>
        </p>
        <p class="field">
          <label for="supportUnregisteredCreation">${message(code:'requestType.property.supportUnregisteredCreation')}</label>
          <input type="checkbox" value="active" ${unregisteredCreation ? 'checked' : ''} name="supportUnregisteredCreation" id="supportUnregisteredCreation"/>
        </p>
        <p class="field">
          <label for="filingDelay" style="margin: 0;">${message(code:'requestType.property.filingDelay')}</label>
          <input type="number" value="${filingDelay}" name="filingDelay" id="filingDelay"/>
        </p>
    <input type="hidden" value="${id}" name="id" />
    </br>
    <div class="form-button">
      <input  id="saveRequestTypeRequestProperty" name="saveRequestTypeRequestProperty" class="submitStepChange bt" type="button" value="${message(code:'action.save')}" />
    </div>
  </form>
</div>
