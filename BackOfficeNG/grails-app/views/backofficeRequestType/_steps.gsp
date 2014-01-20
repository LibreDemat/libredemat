<div class="mainbox mainbox-yellow" id="requestTypeSteps">
  <h2><g:message code="requestType.configuration.steps" /></h2>
  <form class="edit"
        action="${createLink(action:"saveSteps")}"
        id="requestTypeStepsForm"
        method="post">
    <div class="error" id="dialogRequestTypeStepsFormError"></div>
    <p class="field">
      <label for="stepAccountCompletion">
        <g:message code="requestType.property.homeFolder" /> :
        <span>
          (<g:message code="requestType.property.homeFolder.help" />)
        </span>
      </label>
      <input type="checkbox"
             value="active"
             ${requestType.getStepAccountCompletion() ? 'checked' : ''}
             ${supportUnregisteredCreation ? 'disabled' : ''}
             name="stepAccountCompletion"
             id="stepAccountCompletion">
    </p>
    <div class="form-button">
      <input type="hidden" value="${id}" name="id">
      <input id="saveRequestTypeSteps"
             name="saveRequestTypeSteps"
             class="submitStepChange"
             type="button"
             value="${message(code:'action.save')}">
    </div>
  </form>
</div>
