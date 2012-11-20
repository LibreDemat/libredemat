<html>
  <head>
    <title><g:message code="agent.reset.title.resetPwd" /></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice',file:'login.css')}" />
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="agent.reset.title.resetPwd" /></h1>
        </div>
        <div class="mainbox mainbox-yellow">
          <h2><g:message code="agent.reset.title.chooseNewPwd" /></h2>

          <form class="editable-list-form" action="${createLink(action:'newPassword')}" method="post">

            <g:if test="${flash.errorMessage}"><div class="mainbox-orange"><p>${flash.errorMessage}</p></div></g:if>

            <input type="hidden" name="login" value="${params.login}" />
            <input type="hidden" name="key" value="${params.key}" />

            <label class="required" for="newPassword"><g:message code="agent.property.password"/> * :</label>
            <input type="password" name="newPassword" id="newPassword" value="" class="required" required="required" />
            <span class="legend"><g:message code="property.individualPassword.legend" args="${[passwordMinLength]}"/></span>
            <label class="required" for="newPasswordConfirmation"><g:message code="property.individualPasswordConfirmation"/> * :</label>
            <input type="password" name="newPasswordConfirmation" id="newPasswordConfirmation" value="" class="required" required="required" />
            <span class="legend"><g:message code="property.individualPassword.legend" args="${[passwordMinLength]}"/></span>
            <label></label>
            <input type="submit" class="submitEditItem form-button first-button" value="${message(code:'action.validate')}" />

          </form>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b"></div>
  </body>
</html>