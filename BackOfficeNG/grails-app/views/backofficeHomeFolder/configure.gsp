<html>
  <head>
    <title>${message(code:'homeFolder.title.configuration')}</title>
    <meta name="layout" content="main" />
    <script type="text/javascript" src="${resource(dir:'js/backoffice', file:'homeFolderConfiguration.js')}"></script>
    <!-- Needed for document management -->
    <script type="text/javascript" src="${resource(dir:'js/backoffice', file:'requestTypeConfigure.js')}"></script>
        <link rel="stylesheet" href="${resource(dir:'css/backoffice', file:'configuration.css')}" />
    <!-- / -->
    <link rel="stylesheet" href="${resource(dir:'css/backoffice/yui/editor',file:'simpleeditor.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css/backoffice/common/yui-skin',file:'simpleeditor.css')}" />

    <link rel="stylesheet" href="${resource(dir:'css/backoffice/common/yui-skin',file:'container.css')}" ></link>

    <script type="text/javascript" src="${resource(dir:'js/yui/editor',file:'simpleeditor-min.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/common',file:'editor.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/common',file:'calendar.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'requestType' + org.apache.commons.lang3.StringUtils.capitalize(params.action) + '.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/backoffice', file:'requestTypeDocuments.js')}"></script>
    <script type="text/javascript">
      zenexity.libredemat.tools.namespace('zenexity.libredemat.bong.requesttype');
      <!-- Hack Inexine -->
      <!-- Pour garder la compatibilité des scripts js, il faut laisser 0 -->
      <!-- requesttype n'est pas utilisé pour les courriers sur les comptes -->
      zenexity.libredemat.bong.requesttype.currentId = '0';
    </script>
    <script type="text/javascript" src="${resource(dir:'js/backoffice', file:'requestTypeForms.js')}"></script>
  </head>
  <body>

    <div id="yui-main">

      <div class="yui-b">
        <div class="head">
          <h1>${message(code:'homeFolder.title.configuration')}</h1>
        </div>
        <div id="configuration" class="yui-navset yellow-yui-tabview">

          <div class="yui-content">
            <g:render template="forms" />
            <div id="homeFolderCreation">

              <div id="notification"></div>

              <h2>Création de compte autonome</h2>
              <form method="post" id="independentCreationForm" action="${createLink(action:'setIndependentCreation')}">
                <div class="error" id="independentCreationErrors"></div>
                <p class="field">
                  <label>
                    Activer :
                  </label>
                  <input id="independentCreationInput" type="radio" ${independentCreationEnabled ? 'checked="checked"' : ''} value="1" name="independentCreation" class="required validate-one-required"/>
                    oui
                  <input id="independentCreationInput" type="radio" ${independentCreationEnabled ? '' : 'checked="checked"'} value="0" name="independentCreation" class="required validate-one-required"/>
                    non
                </p>
              </form>

              <h2>Blocage des doublons<span> à la création de compte</span></h2>
              <form method="post" id="blockDuplicateCreationForm" action="${createLink(action:'setBlockDuplicateCreation')}">
                <div class="error" id="blockDuplicateCreationErrors"></div>
                <p class="field">
                  <label>
                    Activer :
                  </label>
                  <input id="blockDuplicateCreationInput" type="radio" ${blockDuplicateCreationEnabled ? 'checked="checked"' : ''} value="1" name="blockDuplicateCreation" class="required validate-one-required"/>
                    oui
                  <input id="blockDuplicateCreationInput" type="radio" ${blockDuplicateCreationEnabled ? '' : 'checked="checked"'} value="0" name="blockDuplicateCreation" class="required validate-one-required"/>
                    non
                </p>
              </form>

              <h2>Pièces justificatives<span> à associer lors d'une création de compte</span></h2>
              <div id="mainPanel">
                <div id="documentTypeFilterPanel" class="editableListSwithcher">
                  <a id="showAssociatedDocuments">${message(code:'filter.viewBounded')}</a>/
                  <a id="showUnassociatedDocuments" class="current">${message(code:'filter.viewUnbounded')}</a>/
                  <a id="showAllDocuments">${message(code:'filter.viewAll')}</a>
                </div>
                <div id="documentList"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="narrow" class="yui-b">
      <menu:subMenu i18nPrefix="header" data="${subMenuEntries}" />
    </div>
  </body>
</html>
