<%@ taglib prefix="g" uri="/web-app/WEB-INF/tld/grails.tld" %>
<html>
  <head>
    <title><g:message code="category.header.categoryList" /></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'configuration.css')}" >
    <script type="text/javascript" src="${createLinkTo(dir:'js',file:'categoryList.js')}"></script>
  </head>
  
  <body>
    <div id="yui-main">
      <div class="yui-b">
        <div class="head">
          <div class="txt-right">
            <a href="${createLink(action:'create')}"><g:message code="action.create"/></a> 
          </div>
          <h1><g:message code="category.header.categoryList" /></h1>
        </div>
      
        <g:if test="${categories.size == 0}">
          <g:message code="category.message.noCategoryDefined" />
        </g:if>
        <g:else>
          <ul class="overviewConfigurationList" id="categoriesList">
            <g:each in="${categories}" var="category">
            <li id="category-${category.id}">
              <h3>
                <a href="${createLink(action:'edit',id:category.id)}">${category.name}</a>
                <span>- ${category.primaryEmail}</span>
                <span onclick="askCategoryDeleteConfirmation('${category.id}','${category.name}', '${message(code:'category.message.askConfirmDelete',args:[category.name])}');">
                  <img src="${createLinkTo(dir:'images',file:'delete.png')}" 
                      alt="<g:message code="request.action.removeCategory" />">
                </span>
              </h3>
              <div>
                <g:each in="${category.requestTypes}" var="requestType">
                  <g:if test="${requestType.active}">
                    <strong class="enabled">
                      <g:translateRequestTypeLabel label="${requestType.label}" />
                    </strong>
                  </g:if>
                  <g:else>
                    <span class="disabled">
                      <g:translateRequestTypeLabel label="${requestType.label}" />
                    </span>
                  </g:else>
                  - 
                </g:each>
              </div>
            </li>
            </g:each>
          </ul>
        </g:else>
      </div>
    </div>

    <div id="narrow" class="yui-b">   
      <div class="nobox">
        <h3><g:message code="category.header.overview" /></h3>
        <div class="body">
          <p style="margin-bottom:0.5em;">
            Cette page pr�sente les cat�gories actuellement d�finies et les t�l�services 
            qui leur sont rattach�s.
          </p>
          <p style="margin-bottom:0.5em;">
            Les t�l�services marqu�s en <span style="color:red;">rouge</span> sont inactifs.
            Ceux marqu�s en <span style="color:green">vert</span> sont actifs.
          </p>
          <p style="margin-bottom:0.5em;">
            En cliquant sur une cat�gorie, vous pourrez voir et modifier le d�tail de sa
            configuration.
          </p>
       </div>
      </div>
    </div>

  </body>
</html>


