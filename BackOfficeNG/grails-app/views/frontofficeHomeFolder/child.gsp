<html>
  <head>
    <title>${message(code:'homeFolder.title.individual')}</title>
    <meta name="layout" content="fo_main" />
    <g:if test="${flash.addressesReferentialEnabled}">
      <link rel="stylesheet" type="text/css" href="${resource(dir:'css/common', file:'autocomplete.css')}" />
       <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/yui/calendar', file:'calendar.css')}" />
      <script type="text/javascript">
        zenexity.libredemat.contextPath = "${request.contextPath}";
      </script>
      <script type="text/javascript" src="${resource(dir:'js/common',file:'addressAutocomplete.js')}"></script>
      <script type="text/javascript" src="${resource(dir:'js/common',file:'autocomplete.js')}"></script>
      <script type="text/javascript" src="${resource(dir:'js/common',file:'validation.js')}"></script>
    </g:if>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice', file:'homeFolder.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'homeFolderCreation.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/common',file:'calendar_fo.js')}"></script>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'form.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'homefolder.css')}" />
  </head>
  <body>

    <g:if test="${child.id != null}">
      <g:render template="edit/backToRequest" />
    </g:if>

    <div class="individual box">
      <h2>
        <g:if test="${child.id != null}">
          ${child.born ? child.fullName : message(code:'request.subject.childNoBorn', args:[child.fullName])}
        </g:if>
        <g:else>
          ${message(code:'homeFolder.header.createChild')}
        </g:else>
      </h2>
      <div class="main ${flash.invalidFields ? 'Invalid' : child.state}">
        <g:if test="${child.id != null}">

          <div style="color:red">
            <g:if test="${flash.invalidFields}">
              <g:if test="${flash.invalidFields.size > 1}">${message(code:'homeFolder.individual.property.errors')} :</g:if>
              <g:else>${message(code:'homeFolder.individual.property.error')} :</g:else>
            </g:if>
            <br />
            <g:each in="${flash.invalidFields}" var="field" >
              <g:if test="${field == 'legalResponsibles'}" >
                  - ${message(code:'homeFolder.error.illegalLegalResponsiblesNumber', args:[child.lastName])} <br />
              </g:if>
              <g:else>
                - ${message(code:'homeFolder.individual.property.' + field)} <br />
              </g:else>
            </g:each>
          </div>

          <h3 id="generalInformations">${message(code:'homeFolder.individual.header.general')}</h3>
          <g:render template="${child.fragmentMode('general')}" />
          <h3 id="identity">${message(code:'homeFolder.individual.header.identity')}</h3>
          <g:render template="${child.fragmentMode('identity')}" />
          <h3 id="responsibles">${message(code:'homeFolder.individual.header.responsibles')}</h3>
          <g:render template="${child.fragmentMode('responsibles')}" />
          <!-- Fred Fabre - Inexine Hack : Fiche de renseignement et de sécurité enfant -->
          <g:if test="${informationSheetDisplayed}">
         	<h3 id="informationSheet">${message(code:'homeFolder.individual.header.informationSheet')}</h3>
          	<g:render template="${child.fragmentMode('informationSheet')}" />
          </g:if>
        </g:if>
        <g:else>
          <form action="${createLink(controller : 'frontofficeHomeFolder', action:'child')}" method="post">
            <g:render template="edit/childCommonFields" />
            <input type="submit" value="${message(code:'action.create')}" />
          </form>
        </g:else>
      </div>
      <div class="side">
        <g:if test="${child.id != null}">
          <g:if test="${params.creation == 'true'}">
          <div class="summary">
              ${message(code:'homeFolder.childAdded',args:[child.lastName,child.firstName?:""])}
              <a href="${createLink(action:'child')}">${message(code:'homeFolder.addOtherChild')}</a>
              <a href="${createLink(action:'adult')}">${message(code:'homeFolder.addOtherAdult')}</a>
            </div>
          </g:if>
          <div class="back">
            <a href="${createLink(action:'index')}">${message(code:'homeFolder.action.back')}</a>
          </div>
          <div class="action">
            <a href="#generalInformations">${message(code:'homeFolder.individual.header.general')}</a>
            <a href="#identity">${message(code:'homeFolder.individual.header.identity')}</a>
            <a href="#responsibles">${message(code:'homeFolder.individual.header.responsibles')}</a>
            <!-- Fred Fabre - Inexine Hack : Fiche de renseignement et de sécurité enfant -->
            <g:if test="${informationSheetDisplayed}">
            	<a href="#informationSheet">${message(code:'homeFolder.individual.header.informationSheet')}</a>
          	</g:if>
          </div>
        </g:if>
      </div>
    </div>
  </body>
</html>
