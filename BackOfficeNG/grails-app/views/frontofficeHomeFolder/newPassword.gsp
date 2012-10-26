<html>
  <head>
    <title>${message(code:'homeFolder.title.admin')}</title>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'form.css')}" />
  </head>
  <body>
    <g:if test="${flash.errorMessage}"><div class="error-box"><p>${flash.errorMessage}</p></div></g:if>
    <div class="yui-g">
      <div class="yui-u first main-box">
        <h2><g:message code="${message(code:'homeFolder.action.resetPwd.title')}"/></h2>
        <form action="${createLink(action:'newPassword')}" method="post">

          <label class="required" for="newPassword">
            <g:message code="homeFolder.adult.property.newPassword"/> * :
            <span class="help">
              (<g:message code="homeFolder.adult.property.newPassword.legend" args="${[passwordMinLength]}"/>)
            </span>
          </label>
          <input type="password" id="newPassword" name="newPassword" value="" class="required" />

          <label class="required" for="newPasswordConfirmation"><g:message code="homeFolder.adult.property.newPasswordConfirmation"/> * :</label>
          <input type="password" id="newPasswordConfirmation" name="newPasswordConfirmation" value="" class="required" />

		  <label for="question" class="required"><g:message code="homeFolder.adult.property.question" /> * :</label>
          <select id="question" name="question" class="required validate-not-first">
            <option value=""><g:message code="message.select.defaultOption" /></option>
            <g:each in="${['q1','q2','q3','q4']}">
              <option value="<g:message code='homeFolder.adult.question.${it}' />"${message(code:'homeFolder.adult.question.' + it) == question.toString() ? 'selected="selected"': ''}>
                <g:message code="homeFolder.adult.question.${it}" />
              </option>
            </g:each>
          </select>

          <label for="answer" class="required"><g:message code="homeFolder.adult.property.answer" /> * :</label>
          <input type="text" id="answer" name="answer" value="${answer}" class="required" />

          <input type="hidden" name="login" value="${params.login}" />
          <input type="hidden" name="key" value="${params.key}" />

          <p class="submit-form">
            <input type="submit" value="${message(code:'action.validate')}" />
          </p>
        </form>
      </div>
    </div>
  </body>
</html>
