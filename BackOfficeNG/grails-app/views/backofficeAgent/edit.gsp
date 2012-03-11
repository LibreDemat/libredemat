<html>
  <head>
  <title>
    <g:message code="agent.header.configuration" args="${[agent.lastName, agent.firstName]}" />
  </title>
  <meta name="layout" content="main" />
  <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" >
  <link rel="stylesheet" href="${resource(dir:'css/backoffice/hacks',file:'agentProfile.css')}" >
  <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'agentEdit.js')}"></script>
  <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'agentCategories.js')}"></script>
  <script type="text/javascript">
    zenexity.capdemat.bong.agentId = '${agent?.id}';
  </script>
  </head>
  
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <div class="txt-right">
            <a href="${createLink(controller:'backofficeCategory', action:'list')}">${message(code:'category.header.list')}</a>
            | <a href="${createLink(controller:'backofficeAgent', action:'list')}">${message(code:'agent.header.list')}</a>
          </div>
          <h1>
            <g:message code="agent.header.configuration" args="${[agent.lastName, agent.firstName]}" />
          </h1>
        </div>
      
        <div class="mainbox mainbox-yellow">
          <h2><g:message code="agent.header.agent" /></h2>
          <dl id="agent" class="tableDisplay">
            <dt><g:message code="agent.property.lastName" /> : </dt>
            <dd>${agent.lastName}</dd>
            
            <dt><g:message code="agent.property.firstName" /> : </dt>
            <dd>${agent.firstName}</dd>
            
            <dt><g:message code="agent.property.login" /> : </dt>
            <dd>${agent.login}</dd>

            <dt><g:message code="agent.property.sitesRoles" /> : </dt>
            <dd id="agentProfileDD">
              <form id="agentProfileForm" action="${createLink(action:'edit')}" method="post">
                <select name="siteProfile">
                  <g:each in="${siteProfiles}" var="siteProfile">
                    <option value="${siteProfile.toString()}"
                      <g:if test="${agent.sitesRoles.iterator().next().profile.equals(siteProfile)}">
                        selected="selected"
                      </g:if>>
                      <g:message code="agent.siteProfile.${siteProfile.toString().toLowerCase()}" />
                    </option>
                  </g:each>
                </select>
                <input type="hidden" name="id" value="${agent.id}" />
                <input id="saveProfile"
                  class="submitEditItem form-button first-button"
                  type="button" value="${message(code:'action.save')}" />
              </form>
            </dd>
          </dl>
        </div>
        
        <div id="agentCategoriesBox" class="mainbox mainbox-yellow">
          <h2><g:message code="agent.header.categories" /></h2>
          <div class="editableListSwithcher">
            <form id="sortCategoryForm" method="post" action="<g:createLink action="categories" />" />
              <input type="hidden" name="id" value="${agent?.id}" />

              <a class="viewCategories current" id="viewCategories_Agent">
                <g:message code="filter.viewBounded" />
              </a> / 
              <a  class="viewCategories" id="viewCategories_All">
                <g:message code="filter.viewAll" />
              </a>
            </form>
          </div>
          <ul id="agentCategories" class="editableList">
          </ul>
        </div>

      </div>
      
    </div>  
    
    <div id="narrow" class="yui-b">
      <div class="nobox">
        <h3><g:message code="agent.header.switchAgent" /></h3>
        <div class="body">
          <g:if test="${agents.size == 0}">
            <g:message code="agent.message.noAgentDefined" />
          </g:if>
          <g:if test="${agents.size > 0}">
            <form action="<g:createLink action="edit" />">
              <select name="agentId" id="agentId">
                <g:each in="${agents}" var="agent">
                  <option value="${agent.id}" ${agent.id == Long.valueOf(params.id) ? 'selected' : ''}>
                    ${agent.lastName} ${agent.firstName}
                  </option>>
                </g:each>
              </select>
            </form>
          </g:if>
        </div>
      </div>
    </div>

  </body>
</html>
