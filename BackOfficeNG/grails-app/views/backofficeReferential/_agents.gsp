<ul id="agents" class="editableList">
  <g:each in="${agents}" var="agent">
    <li id="agent_${agent.id}">
      <a id="remove_${agent.id}" class="deleteItem"><span>deleteItem</span></a>
      <a id="editPassword_${agent.id}" class="editPassword"><span>editPassword</span></a>
      <a id="edit_${agent.id}" class="editItem"><span>editItem</span></a>
      <span class="name">${agent.firstName} ${agent.lastName} (${agent.login})</span>
      <g:if test="${agent.active}">
        <span class="tag-enable"><g:message code="property.active" /></span>
      </g:if>
      <g:else>
        <span class="tag-disable"><g:message code="property.inactive" /></span>
      </g:else>
      <div id="formContainer_${agent.id}"></div>
    </li>
  </g:each>
</ul>
