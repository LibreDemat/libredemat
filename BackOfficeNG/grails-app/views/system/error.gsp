<html>
  <head>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'error.css')}" />
  </head>
  <body>
    <div id="yui-main">
      <div class="error-box">
        <p>
          <g:message code="${i18nKey}" args="${i18nArgs}" />
        </p>
        <div class="links">
          <g:if test="${context == 'bo'}">
            <a href="${createLink(controller: 'backofficeLogin', action: 'login')}"><g:message code="action.goBack" /></a>
          </g:if>
          <g:if test="${context != 'bo'}">
            <a href="${createLink(controller: 'frontofficeHome', action: temporary ? 'logout' : '')}"><g:message code="action.goHome" /></a>
          </g:if>
        </div>
      </div>
    </div>
  </body>
</html>
