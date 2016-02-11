<g:each var="role" in="${roles}">
    <dt class="required">
        <g:if test="${role.role}">
            ${g.libredematEnumToFlag(var:role.role, i18nKeyPrefix:'homeFolder.role')}
        </g:if>
        <g:else>
            ${message(code:'homeFolder.role.message.none')}
        </g:else>
    </dt>
    <dd class="required">
        ${message(code:'layout.'+ (role.role?'from':'for'))}
        ${role.subjectName}
    </dd>
</g:each>
