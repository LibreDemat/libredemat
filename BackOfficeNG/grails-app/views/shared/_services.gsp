<g:each var="group" in="${groups}">
  <g:if test="${!group.value.get('requests').isEmpty()}">
    <div class="group-box">
      <h3>${group.value.get('label')}</h3>
      <img src="${createLink(controller:'localAuthorityResource', action:'resource',  params:[type:'DISPLAY_GROUP_IMAGE',filename:group.key]).encodeAsXML()}" alt="${message(code:'displayGroup.header.logo')} ${group.value.get('label')}" />
      <ul>
        <g:each var="requestType" in="${group.value.get('requests')}">
          <li>
            <g:if test="${requestType.enabled}">
              <a href="${createLink(controller:'frontofficeRequestType', action : 'start', id : requestType.label)}">
                <g:translateRequestTypeLabel label="${requestType.label}"/>
              </a>
              <g:if test="${requestType.seasons?.size() == 1}">
                <span class="notice">
                  ${requestType.seasons.iterator().next().label}
                </span>
              </g:if>
              <g:if test="${requestType.countDraft > 0}">
                &nbsp;
                <a href="${createLink(controller:'frontofficeRequest', params:[stateFilter:'Draft',typeFilter:requestType.id])}" class="tag-draft">${message(code:'requestType.message.draftNumber', args:[requestType.countDraft])}</a>
              </g:if>
            </g:if>
            <g:else>
              <g:translateRequestTypeLabel label="${requestType.label}"/>
              <g:if test="${requestType.countDraft > 0}">
                &nbsp;
                <a href="${createLink(controller:'frontofficeRequest', params:[stateFilter:'Draft',typeFilter:requestType.id])}" class="tag-draft">${message(code:'requestType.message.draftNumber', args:[requestType.countDraft])}</a>
              </g:if>
              <g:if test="${requestType.message}">
                <span class="notice">
                  <g:message code="${requestType.message}"/>
                </span>
              </g:if>
            </g:else>
          </li>
        </g:each>
      </ul>
    </div>
  </g:if>
</g:each>
