<ul id="schools" class="editableList">
  <g:each in="${schools}" var="school">
    <li id="school_${school.id}">
      <a id="remove_${school.id}" class="deleteItem"><span>deleteItem</span></a>
      <a id="edit_${school.id}" class="editItem"><span>editItem</span></a>
      <span class="name">${school.name} (id : ${school.id})</span>
      <g:if test="${school.active}">
        <span  class="tag-enable"><g:message code="property.active" /></span>
      </g:if>
      <g:else>
        <span class="tag-disable"><g:message code="property.inactive" /></span>
      </g:else>
      <div id="formContainer_${school.id}"></div>
    </li>
  </g:each>
</ul>
