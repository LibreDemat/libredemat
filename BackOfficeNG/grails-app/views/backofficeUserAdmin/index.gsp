<html>
  <head>
    <title><g:message code="localAuthority.header.userConfiguration" /></title>
    <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'requests.css')}" />
    <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'localAuthorityRequests.js')}"></script>
    <meta name="layout" content="main" />
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="localAuthority.header.userConfiguration" /></h1>
        </div>
        <div id="pendingBox" class="mainbox mainbox-yellow">
          <h2><g:message code="userAdmin.userPendings.title" /></h2>
          <form method="post" id="pendingForm" action="${createLink(action:'index')}">
            <div class="error" id="pendingFormErrors"> </div>
            <p class="field">
              <label for="pendingUsersLiveDuration">
                <g:message code="userAdmin.property.pendingUsersLiveDuration" /> :
                <span> (<g:message code="property.days" />) </span>
              </label>
              <input type="text" name="pendingUsersLiveDuration"
                value="${globalUserConfiguration.pendingUsersLiveDuration}"
              class="required validate-positiveInteger" />
            </p>
            <p class="field">
              <label for="pendingUserNotificationPurge">
                <g:message code="userAdmin.property.pendingUserNotificationPurge" /> :
                <span> (<g:message code="property.days" />) </span>
              </label>
              <input type="text" name="pendingUserNotificationPurge"
                value="${globalUserConfiguration.pendingUserNotificationPurge}"
                class="required validate-positiveInteger" />
            </p>
            <div class="form-button">
              <input id="save_pending" type="button" value="${message(code:'action.save')}" />
            </div>
          </form>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <menu:subMenu i18nPrefix="header" data="${subMenuEntries}" />
    </div>
  </body>
</html>
