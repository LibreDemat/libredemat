<html>
  <head>
    <title><g:message code="action.login" /></title>
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/backoffice/yui/reset-fonts-grids',file:'reset-fonts-grids.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice',file:'login.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice/common', file:'box.css')}" />
  </head>
  <body>
    <div id="all">
      <img class="logo-authority" 
        src="${createLink(controller : 'localAuthorityResource', action : 'resource', id : 'logoFo')}"
        alt="Logo Collectivité""></img>
        <h1><g:message code="action.login.mairie24" /></h1>
        <form method="post" class="${error ? 'error' : ''}">
            <p class="error"><g:message code="${error}" /></p>
            <g:if test="${flash.successMessage}"><p class="success">${flash.successMessage}</p></g:if>
            
            <p>
              <label class="required" for="login"><g:message code="agent.property.login"/> * :</label>
              <input type="text" name="login" value="${enteredLogin}" />
            </p>

            <p>
              <label class="required" for="password"><g:message code="agent.property.password"/> * :</label>
              <input type="password" name="password" value="" />
            </p>

            <p>
              <label>&nbsp;</label>
              <input type="submit" value="${message(code:'action.login')}"/> &nbsp;
              <a href="${createLink(controller: 'backofficeLogin', action: 'resetPassword')}" class="forgot">${message(code:'agent.reset.action.lost')}</a>
            </p>
        </form>
    </div>
  </body>
</html>
