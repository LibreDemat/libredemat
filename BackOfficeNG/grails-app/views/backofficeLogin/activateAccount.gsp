<html>
  <head>
    <title><g:message code="action.login" /></title>
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/backoffice/yui/reset-fonts-grids',file:'reset-fonts-grids.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice',file:'login.css')}" >
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice/common', file:'box.css')}" >
  </head>
  <body>
    <div id="all">
      <a href="${createLink()}">
        <img class="logo-authority" src="${createLink(controller : 'localAuthorityResource', action : 'resource', id : 'logoFo')}" alt="Logo Collectivité" />
      </a>
        <h1><g:message code="agent.reset.title.activate" /></h1>
        <form method="post" class="${error ? 'error' : ''}">
            <g:if test="${flash.errorMessage}"><div class="mainbox-orange"><p>${flash.errorMessage}</p></div></g:if>

            <p><g:message code="agent.reset.message.descActivate1" /></p>
            <p><g:message code="agent.reset.message.descActivate2" /></p>

            <p><g:message code="agent.reset.message.activateFollow" /> <a href="${createLink(action: 'resetPassword')}"><g:message code="agent.reset.message.activateFollowLink" /></a>.</p>
        </form>
    </div>
  </body>
</html>
