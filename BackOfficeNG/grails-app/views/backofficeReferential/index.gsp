<html>
  <head>
    <title><g:message code="referential.header.referentialList" /></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/backoffice',file:'configuration.css')}" />
    <script type="text/javascript" src="${createLinkTo(dir:'js/backoffice',file:'referential.js')}"></script>
    <script type="text/javascript">
      zenexity.capdemat.bong.Referential.type = "${type}";
    </script>
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="referential.header.referentialList" /></h1>
        </div>
        <div id="mainPanel">
          <div class="mainbox mainbox-yellow">
            <h2><g:message code="${'referential.type.' + type + 's'}" /></h2>
            <div class="editableListSwithcher">
              <a id="loadNewForm" class="createItem"><g:message code="action.create" /></a>
            </div>
            <div id="newFormContainer"></div>
            <div id="listContainer"></div>
          </div>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu id="secondMenu" i18nPrefix="type" data="${subMenuEntries}" />
    </div>
  </body>
</html>
