<ul id="displayGroupRequestTypes" class="editableList">
    <g:each in="${agents}" var="agent">
        <g:if test="${agent.isSanitaire}">
            <li id="agent_${agent.id}">
                <a id="unassociate_${agent.id}" class="unassociate"><span>${message(code:'action.unassociate')}</span></a>
        </g:if>
        <g:else>
            <li id="agent_${agent.id}" class="notBelong">
                 <a id="associate_${agent.id}" class="associate"><span>${message(code:'action.associate')}</span></a>
        </g:else>
                <g:if test="${agent.isSanitaire}">
                    <strong>${agent.lastName + " " + agent.firstName}</strong>
                </g:if>
                <g:else>
                    <span>${agent.lastName + " " + agent.firstName}</span>
                </g:else>
            </li>
    </g:each>
</ul>