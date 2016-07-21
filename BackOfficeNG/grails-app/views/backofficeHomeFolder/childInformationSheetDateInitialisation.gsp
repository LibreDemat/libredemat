<html>
  <head>
    <title><g:message code="homeFolder.header.childInformationSheetDateInitialisation" /></title>
    <meta name="layout" content="main" />
    <script type="text/javascript" src="${resource(dir : 'js/backoffice', file : 'childInformationSheetDateInitialisation.js')}"></script>
  </head>
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <h1><g:message code="homeFolder.header.childInformationSheetDateInitialisation.description" /></h1>
        </div>
        <div id="fileBox" class="mainbox mainbox-yellow">
          <h3>
          	<g:message code="homeFolder.childInformationSheetDateInitialisation.information" />
          	<g:message code="homeFolder.childInformationSheetDateInitialisation.informationSuite" />
          </h3>
          <g:if test="${isInformationSheetDisplayed}">
            <form method="post" id="childInformationSheetDateInitialisationForm" action="${createLink(action : "childInformationSheetDateInitialisation")}">
              <div class="form-button">
                <input id="initialisation" type="button" value="${message(code:'action.reinitialiser')}" />
              </div>
            </form>
          </g:if>
          <g:else>
            <p class="message"><g:message code="homeFolder.childInformationSheetDateInitialisation.error.notDisplayed" /></p>
          </g:else>
        </div>
      </div>
      <g:if test="${informationSheetRequiredFieldsActived}">
      <div class="yui-b">
        <div id="fileBox" class="mainbox mainbox-yellow">
          <h2>
              ${message(code:'requestType.label.rule')} :
            </h2>
            <form method="post" id="rulesForm" class="localResourceUpload" action="${createLink(action : 'saveRule')}">
              <div class="error" id="setupFormErrors"></div>
              <g:if test="${rulesField}">
                <a target="_blank" href="${createLink(controller : 'localAuthorityResource', action : 'rule', params:[type:'PDF',requestTypeLabel:'childInformationSheet',filename:'acceptationReglementInterieur'])}">
                  ${message(code:'requestType.label.seeCurrentRule')}
                </a>
              </g:if>
              <g:else>
                ${message(code:'requestType.label.noRuleConfigured')}
              </g:else>
              <br />
              <label for="">
                ${message(code:'requestType.label.newRule')} :
              </label>
              <input type="file" class="required" name="rulesFile" />
              <input type="hidden" name="requestTypeId" value="" />
              <input type="hidden" name="rulesField" value="" />
              <input id="saveRule" name="save" type="button" value="${message(code:'action.save')}" />
            </form>
        </div>
      </div>
      </g:if>
    </div>
    
    
    <div id="narrow" class="yui-b">
      <menu:subMenu i18nPrefix="header" data="${subMenuEntries}" />
    </div>
  </body>
</html>