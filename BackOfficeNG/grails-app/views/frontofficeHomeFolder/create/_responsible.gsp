<%
  //Hack:
  if (flash.invalidFields) invalidFields = flash.invalidFields
  if (flash.adult) adult = flash.adult
%>
<form action="${createLink(controller : 'frontofficeHomeFolder', action : 'create', params :callback.params ) }" method="post">
  <div>
  
    <input type="hidden" name="callback" value="${params.callback}" />
    <g:if test="${invalidFields?.any()}">
      <g:if test="${invalidFields?.contains('user.duplicate.finding')}">
      <p class="error">${message(code:'user.duplicate.finding')}</p>
      </g:if>
      <g:else>
      <p class="error">${message(code:'form.error.invalidFields')}</p>
      </g:else>
    </g:if>
    <g:render template="/frontofficeHomeFolder/edit/adultCommonFields" />
    <g:if test="${temporary}">
      <g:if test="${!createOnlyTemporaryAccountInTS}">

        <p>${message(code:'request.step.validation.help.followRequest')}</p>
        <label class="required">
          ${message(code:'request.step.validation.label.followRequest')}
        </label>
        <ul class="yes-no required">
          <li>
            <input type="radio" class="required" value="false"
              name="temporary" id="temporary_off"
              ${params.boolean != null && params.boolean('temporary') ? '' : 'checked="checked"'} />
            <label for="temporary_off">
              <g:message code="message.yes" />
            </label>
          </li>
          <li>
            <input type="radio" class="required" value="true"
              name="temporary" id="temporary_on"
              ${params.boolean == null || params.boolean('temporary') ? 'checked="checked"' : ''} />
            <label for="temporary_on">
              <g:message code="message.no" />
            </label>
          </li>
        </ul>
      </g:if>
      <g:else>
        <input type="hidden" value="true" name="temporary" id="temporary_on" />
      </g:else>
      <div id="loginInformations" class="${params.boolean == null || params.boolean('temporary') ? 'hidden' : ''}">
    </g:if>

    <label for="password" class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'}">
      ${message(code:'request.step.validation.label.choosePassword')} *
      <span>(${message(code:'request.step.validation.help.choosePassword')})</span>
    </label>
    <input type="password" id="password" name="password" value="${adult?.password}" autocomplete="off"
      class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'} ${invalidFields?.contains('password') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.password.validationError')}" />
    <label for="confirmPassword" class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'}">${message(code:'request.step.validation.label.confirmPassword')} *</label>
    <input type="password" id="confirmPassword" name="confirmPassword" value="${adult?.confirmPassword}" autocomplete="off"
      class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'} ${invalidFields?.contains('confirmPassword') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.password.validationError')}" />
    <p>${message(code:'homeFolder.adult.help.question')}</p>
    <label for="question" class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'}">
      ${message(code:'homeFolder.adult.property.question')} *
    </label>
    <select id="question" name="question"
      class="${temporary && !createOnlyTemporaryAccountInTS ? 'required validate-not-first' : ''} ${invalidFields?.contains('question') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.question.validationError')}">
      <option value="">${message(code:'message.select.defaultOption')}</option>
      <g:each in="${['q1','q2','q3','q4']}">
        <option value="${message(code:'homeFolder.adult.question.' + it)}"
          <g:if test="${message(code:'homeFolder.adult.question.' + it).equals(adult?.question)}">
            selected="selected"
          </g:if>>
          ${message(code:'homeFolder.adult.question.' + it)}
        </option>
      </g:each>
    </select>
    <label for="answer" class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'}">${message(code:'homeFolder.adult.property.answer')} *</label>
    <input type="text" id="answer" name="answer" value="${adult?.answer}"
      class="${temporary && createOnlyTemporaryAccountInTS ? '' : 'required'} ${invalidFields?.contains('answer') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.answer.validationError')}" />
    <g:if test="${temporary}">
      </div>
    </g:if>
    <label for="captchaText" class="required">
      ${message(code:'request.step.validation.label.typeTextInImage')} *
    </label>
    <jcaptcha:jpeg name="captchaImage" alt="captcha" />
    <input type="text" id="captchaText" name="captchaText" autocomplete="off"
      class="required ${invalidFields?.contains('captchaText') ? 'validation-failed' : ''}" />
    <input type="hidden" name="currentStep" value="${currentStep}"/>
    <p style="text-align: center;">
      <input type="submit" style="font-size:1.2em;" value="${message(code : submitCode)}" />
    </p>
  </div>
</form>
