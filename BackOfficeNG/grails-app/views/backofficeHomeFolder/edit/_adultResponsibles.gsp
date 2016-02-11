<p>${message(code:'homeFolder.adult.child.property.legalResponsibles.help')}</p>
<form id="adultResponsibles_${adult.id}" method="post" action="${g.createLink(action:'adultResponsibles', id : adult.id)}">
  <g:each var="role" in="${roles}" status="index">
    <dt class="required">
      <select name="type_${role.childId}">
        <option value="">${message(code:'homeFolder.role.message.none')}</option>
        <g:each var="roleType" in="${org.libredemat.business.users.RoleType.childRoleTypes}">
          <option value="${roleType}" ${role?.role?.equals(roleType) ? 'selected="selected"' : ''}>
            ${g.libredematEnumToText(var:roleType, i18nKeyPrefix:'homeFolder.role.withParticle')}
          </option>
        </g:each>
      </select>
    </dt>
    <dd>
      ${message(code:'layout.from')}
      ${role.subjectName}
      <input type="hidden" name="child_${role.childId}" value="${role.childId}" />
    </dd>
  </g:each>
  <g:render template="edit/submit" model="['object': adult]" />
</form>
