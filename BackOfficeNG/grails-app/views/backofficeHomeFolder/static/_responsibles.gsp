<g:each var="role" in="${roles}">
    <g:each var="rol" in="${role}">
        <g:if test="${rol.childId == child.id}" >
            <dt class="required">
                ${rol.adultFullName}
            </dt>
            <dd class="required">
                <g:if test="${rol.role}" >
                    ${g.libredematEnumToFlag(var:rol.role, i18nKeyPrefix:'homeFolder.role.withParticle')}
                    
                </g:if>
                <g:else>
                    ${message(code:'homeFolder.role.message.none')}
                </g:else>
               
            </dd>
        </g:if>
    </g:each>
</g:each>
