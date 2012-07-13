<ul id="documentTypes" class="editableList">
    <g:each in="${documentTypes}" var="documentType">
        <li id="documentType_${documentType.id}">
            <a id="deleteDocumentType_${documentType.id}" class="deleteItem"><span>deleteItem</span></a>
            <a id="editDocumentType_${documentType.id}" class="editItem"><span>editItem</span></a>
            <span class="name">${documentType.name}</span>
            <div id="editDocumentTypeFormContainer_${documentType.id}"></div>
        </li>
    </g:each>
</ul>
