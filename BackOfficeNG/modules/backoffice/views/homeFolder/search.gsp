<html>
  <head>
    <title><g:message code="homeFolder.header.search" /></title>
    <meta name="layout" content="main" />
    <script type="text/javascript" src="${createLinkTo(dir:'js/backoffice',file:'homeFolderSearch.js')}"></script>
  </head>
  <body>

    <div id="yui-main">
      <div class="yui-b">
        
        <div id="head" class="head">
          <g:render template="searchForm" />
        </div>

        <div id="search-results">
          <g:render template="searchResults" />
        </div>
        
      </div>
    </div>

    <!-- filters and sorters -->
    <div id="narrow" class="yui-b">
      <div class="nobox">
        <h3><g:message code="header.sortBy" /></h3>
        <div class="body">
          <form action="#" id="homeFolderSearchSorters">
            <ul>
              <li>
                <label for="sortLastName"><g:message code="property.individualLastName" /></label>
                <input type="radio" id="sortLastName" value="lastName" class="persistent sort"
                  name="orderBy" ${state.orderBy == 'lastName' ? 'checked="checked"' : ''} />
              </li>
              <li>
                <label for="sortLastName"><g:message code="property.individualId" /></label>
                <input type="radio" id="sortLastName" value="id" class="persistent sort"
                  name="orderBy" ${state.orderBy == 'id' ? 'checked="checked"' : ''} />
              </li>
              <li>
                <label for="sortHomeFolderId"><g:message code="property.homeFolderId" /></label>
                <input type="radio" id="sortHomeFolderId" value="homeFolder.id" class="persistent sort"
                  name="orderBy" ${state.orderBy == 'homeFolder.id' ? 'checked="checked"' : ''} />
              </li>
            </ul>
          </form>
        </div>
      </div>

      <div class="nobox">
        <h3><g:message code="header.filterBy" /></h3>
        <div class="body">
          
          <form action="#" id="individualFilters">
            <label for="actorState"><g:message code="property.actorState" /> :</label>
            <select name="actorState" class="persistent filter">
              <option value=""><g:message code="search.filter.defaultValue"/></option>
              <g:each in="${actorStates}" var="record">
                <option value="${record.name}" ${state.actorState == record.name ? 'selected="selected"' : ''}>
                  ${record.i18nKey}
                </option>
              </g:each>
            </select>
            
            <label for="homeFolderState"><g:message code="property.homeFolderState" /> :</label>
            <select name="homeFolderState" class="persistent filter"> 
              <option value=""><g:message code="search.filter.defaultValue"/></option>
              <g:each in="${homeFolderStates}" var="record">
                <option value="${record.name}" ${state.homeFolderState == record.name.toString() ? 'selected="selected"' : ''}>
                  ${record.i18nKey}
                </option>
              </g:each>
            </select>
          </form>

        </div>
      </div>
      
    </div>
  
  </body>
</html>