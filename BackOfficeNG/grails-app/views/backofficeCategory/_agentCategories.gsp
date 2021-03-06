<g:each in="${categories}" var="category">
  <g:if test="${category?.agentHasProfile}">
    <li id="category_${category.id}">
    <a class="unassociate"><span><g:message code="category.action.unassociate" /></span></a>
    <a class="editItem"><span><g:message code="category.action.edit" /></span></a>
  </g:if>
  <g:else>
    <li id="category_${category.id}" class="notBelong">
    <a class="associate"><span><g:message code="category.action.associate" /></span></a>
  </g:else>
  <span class="itemName">
    ${category.name}
  </span>
  <span class="${category?.agentProfile?.cssClass}"><g:message code="${category?.agentProfile?.i18nKey}"/></span
  </li>
</g:each>
