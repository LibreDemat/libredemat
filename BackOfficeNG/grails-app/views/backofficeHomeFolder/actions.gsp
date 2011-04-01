<ul id="userActions">
  <g:each var="action" in="${actions}">
    <li>
      <dl class="action">
        <dt class="title">
          <span class="tag ${action.type.cssClass}">
            <g:message code="${action.type.i18nKey}" />
          </span>
        </dt>
        <dd class="title">
          <g:if test="${action.state}">
            <span class="tag ${action.state.cssClass}"><g:message code="${action.state.i18nKey}" /></span>
          </g:if>
          ${action.target}
        </dd>
        <dd class="title">
          <g:message code="searchResult.actionDate" /> :
          <strong><g:formatDate formatName="format.fullDate" date="${action.date}"/></strong>
          <g:if test="${action.username}">
            <g:message code="layout.by" />
            <strong>${action.username}</strong>
          </g:if>
        </dd>
        <g:if test="${action.responsible}">
          <g:if test="${action.responsible.deleted}">
            <dt style="text-decoration : line-through">
              <g:each var="type" in="${action.responsible.deleted}">
                <g:capdematEnumToFlag var="${type}" i18nKeyPrefix="homeFolder.role" />
              </g:each>
            </dt>
            <dd style="text-decoration : line-through">
              ${action.responsible.owner}
            </dd>
          </g:if>
          <g:else>
            <dt>
              <g:each var="type" in="${action.responsible.types}">
                <g:capdematEnumToFlag var="${type}" i18nKeyPrefix="homeFolder.role" />
              </g:each>
            </dt>
            <dd>
              ${action.responsible.owner}
            </dd>
          </g:else>
        </g:if>
        <g:each var="data" in="${action.data}">
          <dt>${data.key}</dt>
          <dd>${data.value}</dd>
        </g:each>
      </dl>
    </li>
  </g:each>
</ul>
