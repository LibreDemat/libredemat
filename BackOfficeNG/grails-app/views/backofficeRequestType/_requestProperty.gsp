<div class="mainbox mainbox-yellow" id="requestTypeRequestProperty">
  <h2><g:message code="requestType.configuration.requestProperty" /></h2>
  <form class="edit" action="${createLink(action:"saveRequestProperty")}" id="requestTypeRequestPropertyForm" method="post">
      <div class="error" id="dialogRequestTypeRequestPropertyFormError"></div>
    <ul>
      <li>
        <label for="subjectPolicy">${message(code:'requestType.property.subjectPolicy')}</label>
        <select name="subjectPolicy" id="subjectPolicy" >
            <g:if test="${subjectPolicy? (subjectPolicy.equals('SUBJECT_POLICY_NONE')) :true}">
            <option value="SUBJECT_POLICY_NONE" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_NONE") ? 'selected' : '') : ''}>
                ${message(code:'requestType.property.subjectPolicy.none')}
            </option>
            </g:if>
            <g:if test="${subjectPolicy? (!subjectPolicy.equals('SUBJECT_POLICY_NONE')) :false}">
            <option value="SUBJECT_POLICY_INDIVIDUAL" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_INDIVIDUAL") ? 'selected' : '') : ''}>
              ${message(code:'requestType.property.subjectPolicy.individual')}
              </option>
            <option value="SUBJECT_POLICY_ADULT" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_ADULT") ? 'selected' : '') : ''}>
              ${message(code:'requestType.property.subjectPolicy.adult')}
            </option>
            <option value="SUBJECT_POLICY_CHILD" ${subjectPolicy ? (subjectPolicy.equals("SUBJECT_POLICY_CHILD") ? 'selected' : '') : ''}>
              ${message(code:'requestType.property.subjectPolicy.child')}
            </option>
            </g:if>
        </select>
      </li>
      <li>
        <label for="isOfRegistrationKind" style="margin: 0;">${message(code:'requestType.property.isOfRegistrationKind')}</label>
        <input type="checkbox" value="active" ${isOfRegistrationKind ? 'checked' : ''} name="isOfRegistrationKind" id="isOfRegistrationKind"/>
      </li>
      <li ${!isOfRegistrationKind ? 'class="unactiveLi"' : ''} id="liSupportMultiple">
        <label for="supportMultiple" style="margin: 0;">${message(code:'requestType.property.supportMultiple')}</label>
        <input ${!isOfRegistrationKind ? 'disabled="disabled"' : ''} type="checkbox" value="active" ${supportMultiple ? 'checked' : ''} name="supportMultiple" id="supportMultiple"/>
      </li>
      </br>
      <li>
        <label for="supportUnregisteredCreation">${message(code:'requestType.property.supportUnregisteredCreation')}</label>
        <input type="checkbox" value="active" ${unregisteredCreation ? 'checked' : ''} name="supportUnregisteredCreation" id="supportUnregisteredCreation"/>
      </li>
      </br>
      <li>
        <label for="filingDelay" style="margin: 0;">${message(code:'requestType.property.filingDelay')}</label>
        <input type="number" value="${filingDelay}" name="filingDelay" id="filingDelay"/>
      </li>
    </ul>
    <input type="hidden" value="${id}" name="id" />
    </br>
    <input  id="saveRequestTypeRequestProperty" name="saveRequestTypeRequestProperty" class="submitStepChange bt" type="button" value="${message(code:'action.save')}" />
  </form>
</div>