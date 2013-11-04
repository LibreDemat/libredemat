<html>
  <head>
    <title><g:message code="localAuthority.header.configuration" /></title>
    <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" />
    <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'localAuthorityAspect.js')}"></script>
    <meta name="layout" content="main" />
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="localAuthority.header.configuration" /></h1>
        </div>
        <div id="cssFoBox" class="mainbox mainbox-yellow"></div>
        <div id="bannerBox" class="mainbox mainbox-yellow"></div>
        <div id="logoFoBox" class="mainbox mainbox-yellow"></div>
        <div id="logoBoBox" class="mainbox mainbox-yellow"></div>
        <div id="logoPdfBox" class="mainbox mainbox-yellow"></div>
        <div id="footerPdfBox" class="mainbox mainbox-yellow"></div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu id="secondMenu" i18nPrefix="header" data="${subMenuEntries}" />
    </div>
    <g:if test="${flash.successMessage}">
      <script type="text/javascript">
        zenexity.libredemat.tools.Notifier.processMessage('success', "${flash.successMessage}");
      </script>
    </g:if>
  </body>
</html>
