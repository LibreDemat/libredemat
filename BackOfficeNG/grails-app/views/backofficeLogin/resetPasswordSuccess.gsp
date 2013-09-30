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
        <h1><g:message code="agent.reset.title.resetPwd" /></h1>
        <form method="post" action="#" style="text-align: center">
        	<p style="font-weight: bold">${message}</p>
        	<p><a href="${createLink(controller : 'backofficeHome')}">Retour</a></p>
       	</form>
    </div>
  </body>
</html>
