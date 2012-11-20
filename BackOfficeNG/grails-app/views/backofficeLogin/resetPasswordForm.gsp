<html>
  <head>
    <title><g:message code="action.login" /></title>
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/backoffice/yui/reset-fonts-grids',file:'reset-fonts-grids.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice',file:'login.css')}" >
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice/common', file:'box.css')}" >
  </head>
  <body>
    <div id="all">
      <img class="logo-authority"
        src="${createLink(controller : 'localAuthorityResource', action : 'resource', id : 'logoFo')}"
        alt="Logo Collectivité""></img>
        <h1>Mot de passe perdu ?</h1>
        <form method="post" class="${error ? 'error' : ''}">
            <g:if test="${flash.errorMessage}"><div class="mainbox-orange"><p>${flash.errorMessage}</p></div></g:if>

            <p><g:message code="agent.reset.message.plzFillForm" /></p>

            <p>
              <label class="required" for="login"><g:message code="agent.property.login"/> * :</label>
              <input type="text" name="login" value="" />
            </p>

            <p>
              <label class="required" for="email"><g:message code="agent.property.email"/> * :</label>
              <input type="text" name="email" value="" />
            </p>

            <p>
              <label>&nbsp;</label>
              <input type="submit" value="${message(code:'agent.reset.action.reset')}" />
            </p>
        </form>
    </div>
  </body>
</html>
