<style>
  form       { padding: 1.5em 0 !important;}
  form .tag-state { margin-right: .5em; }
  form p         { margin: 0 0 .5em !important; overflow: auto; }
  form label     { display: initial; }
  form input[type=submit] { margin: 0 !important; }
</style>
<a href="${createLink(action:'adult', params:['id':adult.id])}#responsibles" class="modify">
  ${message(code:'action.back')}
</a>
<form id="adultResponsibles_${adult.id}" method="post" action="${g.createLink(action:'adult')}">
    <g:if test="${flash.invalidFields}">
        <p class="error">${message(code:'homeFolder.adult.property.legalResponsibles.help')}<p>
    </g:if>
    ${adult.firstName} ${adult.lastName} ${message(code:'layout.is')} :
    <g:each var="child" in="${childrenModfied}">
        <p>
            <g:each var="role" in="${org.libredemat.business.users.RoleType.childRoleTypes}">
                    <input type="radio" name="roleType_${child.id}" value="${role}" ${child.role.equals(role) ? 'checked="checked"' : ''}/>
                    ${g.libredematEnumToFlag(var:role, i18nKeyPrefix:'homeFolder.role.adult')}
            </g:each>
            ${message(code:'layout.from')}
            <label>
                ${child.fullName}
            </label>
            <a href="${createLink(action:'unlink', params:['id':child.id, 'page':'adult', 'fragment':'responsibles','roleOwnerId':adult.id])}#responsibles">
                ${message(code:'action.removeRole')}
            </a>
        </p>
    </g:each>
    <g:render template="edit/submit" model="['individual':adult, 'fragment':'responsibles']" />
</form>
