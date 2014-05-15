<html>
  <head>
    <title><g:message code="localAuthority.header.requestsConfiguration" /></title>
    <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" />
    <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'documentTypes.js')}"></script>
    <meta name="layout" content="main" />
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="referential.header.referentialList" /></h1>
        </div>
        <div id="mainPanel">
          <div id="documentTypes" class="mainbox mainbox-yellow">
            <h2><g:message code="documentType.list" /></h2>

            <div class="editableListSwithcher">
              <a id="linkShowDatasheet" class="createItem"><g:message code="action.create" /></a>
            </div>
            <div id="newDocumentTypeFormContainer"></div>

            <div id="documentTypesList"></div>
          </div>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu id="secondMenu" i18nPrefix="type" data="${subMenuEntries}" />
    </div>
  </body>
</html>
