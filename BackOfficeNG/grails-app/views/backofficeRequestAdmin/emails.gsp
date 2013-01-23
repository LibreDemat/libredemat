<html>
  <head>
    <title><g:message code="localAuthority.header.emailsConfiguration" /></title>
    <link rel="stylesheet" href="${resource(dir:'css/backoffice', file:'configuration.css')}" />
    <meta name="layout" content="main" />
    <script src="${resource(dir:'js/backoffice', file:'email-editor.js')}"></script>
    <script src="${resource(dir:'js/backoffice/ckeditor-317c37a', file:'ckeditor.js')}"></script>
    <script src="${resource(dir:'js/backoffice', file:'requestAdminEmails.js')}"></script>
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="requestAdmin.header.emails" /></h1>
        </div>
        <div class="mainbox">
          <g:render template="/backofficeRequestType/emails" />
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu id="secondMenu" i18nPrefix="header" data="${subMenuEntries}" />
    </div>
  </body>
</html>
