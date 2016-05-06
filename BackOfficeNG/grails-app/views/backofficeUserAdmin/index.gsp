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

        <div id="platformConfigurationBox" class="mainbox mainbox-yellow">
          <h2><g:message code="localAuthority.header.userQosConfiguration" /></h2>
          <form method="post" id="platformConfigurationForm" action="${createLink(action : 'index')}">
            <div class="error" id="platformConfigurationFormErrors"></div>
  
            <p class="field">
              <label>
                <g:message code="requestAdmin.property.instructionAlertsEnabled" /> :
              </label>
              <input type="radio" class="required validate-one-required"
                name="instructionAlertsEnabled" value="1"
                <g:if test="${globalConfiguration.instructionAlertsEnabled == true}">
                  checked="checked"
                </g:if> />
              <g:message code="message.yes" />
              <input type="radio" class="required validate-one-required"
                name="instructionAlertsEnabled" value="0"
                <g:if test="${globalConfiguration.instructionAlertsEnabled == false}">
                  checked="checked"
                </g:if> />
              <g:message code="message.no" />
            </p>

            <p class="field">
              <label>
                <g:message code="requestAdmin.property.instructionAlertsDetailed" /> :
              </label>
              <input type="radio" class="required validate-one-required"
                name="instructionAlertsDetailed" value="1"
                <g:if test="${globalConfiguration.instructionAlertsDetailed == true}">
                  checked="checked"
                </g:if> />
              <g:message code="message.yes" />
              <input type="radio" class="required validate-one-required"
                name="instructionAlertsDetailed" value="0"
                <g:if test="${globalConfiguration.instructionAlertsDetailed == false}">
                  checked="checked"
                </g:if> />
              <g:message code="message.no" />
            </p>

            <p class="field">
              <label for="instructionAlertDelay">
                <g:message code="requestType.property.instructionAlertDelay" /> :
              </label>
              <input type="text" class="required validate-number" size="3"
                name="instructionAlertDelay" id="instructionAlertDelay"
                value="${globalConfiguration.instructionAlertDelay}" />
              <g:message code="property.days" />
            </p>

            <p class="field">
              <label for="instructionMaxDelay">
                <g:message code="requestType.property.instructionMaxDelay" /> :
              </label>
              <input type="text" class="required validate-number" name="instructionMaxDelay"
                id="instructionMaxDelay" size="3"
                value="${globalConfiguration.instructionMaxDelay}" />
              <g:message code="property.days" />
            </p>

            <div class="form-button">
              <input id="save_platformConfiguration" type="button" value="${message(code:'action.save')}" />
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