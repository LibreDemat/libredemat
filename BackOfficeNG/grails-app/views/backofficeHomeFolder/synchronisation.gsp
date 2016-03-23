<html>
  <head>
    <title><g:message code="homeFolder.header.synchronisation" /></title>
    <meta name="layout" content="main" />
    <script type="text/javascript" src="${resource(dir : 'js/backoffice', file : 'synchronisation.js')}"></script>
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="homeFolder.header.synchronisation" /></h1>
        </div>
        <div id="fileBox" class="mainbox mainbox-yellow">
          <h3>
            <g:message code="homeFolder.synchronisation.information" />
          </h3>
          <form method="post" id="synchronisation" action="${createLink(action : "synchronisation")}">
            <div class="form-group">
                <label>Adresse email pour l'envois du rapport :</label>
                <input type="email" name="email" value="${defaultEmail}" placeholder="Votre adresse email" required />
            </div>
            <div class="form-group">
                <label>Veuillez sélectionner un ou plusieurs services :</label>
                <g:select name="services" multiple="yes" from="${externalProviders}" />
                
            </div>
            <div class="form-button">
              <input id="initialisation" type="submit" value="${message(code:'action.sync')}" />
            </div>
          </form>
          <div class="invisible" id="response">
            <h3>
              <g:message code="homeFolder.synchronisation.posted" />
            </h3>
            <p class="padding" id="message"><g:message code="homeFolder.synchronisation.posted.info" /></p>
          </div>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu i18nPrefix="header" data="${subMenuEntries}" />
    </div>
  </body>
</html>