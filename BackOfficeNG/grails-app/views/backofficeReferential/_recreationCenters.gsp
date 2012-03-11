<ul id="recreationCenters" class="editableList">
  <g:each in="${recreationCenters}" var="recreationCenter">
    <li id="recreationCenter_${recreationCenter.id}">
      <a id="remove_${recreationCenter.id}" class="deleteItem"><span>deleteItem</span></a>
      <a id="edit_${recreationCenter.id}" class="editItem"><span>editItem</span></a>
      <span class="name">${recreationCenter.name} (id : ${recreationCenter.id})</span>
      <g:if test="${recreationCenter.active}">
        <span class="tag-enable"><g:message code="property.active" /></span>
      </g:if>
      <g:else>
        <span class="tag-disable"><g:message code="property.inactive" /></span>
      </g:else>
      <div id="formContainer_${recreationCenter.id}"></div>
    </li>
  </g:each>
</ul>
