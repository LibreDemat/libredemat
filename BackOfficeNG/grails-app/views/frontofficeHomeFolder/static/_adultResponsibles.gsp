<a href="${createLink(action:'adult', params:['id':adult.id, 'fragment':'responsibles'])}#responsibles" class="modify">
  ${message(code:'action.modify')}
</a>
<!-- Hack for maintain the layout -->
<style>
  form#adultResponsibles p         { margin: 0 0 .5em !important; overflow: auto; }
  form#adultResponsibles label     { display: initial; }
</style>

<dl>
    <dt>${adult.firstName} ${adult.lastName} :</dt>
    <dd></dd>
    <g:each var="child" in="${childrenModfied}">
        <dt>
            <g:if test="${child.role}">
                ${message(code:'layout.is')}
                ${g.libredematEnumToFlag(var:child.role, i18nKeyPrefix:'homeFolder.role.adult')}
                ${message(code:'layout.from')}
            </g:if>
            <g:else>
                ${message(code:'layout.a')}
                ${message(code:'homeFolder.role.message.none')}
                ${message(code:'layout.for')}
            </g:else>
        </dt>
        <dd>
            ${child.fullName}
        </dd>
    </g:each>
</dl>
