<form id="contact_${adult.id}" method="post" action="${g.createLink(action:'contact', id : adult.id)}">
  <dt class="required">${message(code:'homeFolder.adult.property.email')}</dt>
  <dd class="required">
    <input type="text" name="email" value="${adult.email}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.homePhone')}</dt>
  <dd>
    <input type="text" name="homePhone" value="${adult.homePhone}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.mobilePhone')}</dt>
  <dd>
    <input type="text" name="mobilePhone" value="${adult.mobilePhone}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.officePhone')}</dt>
  <dd>
    <input type="text" name="officePhone" value="${adult.officePhone}" />
  </dd>
  <g:if test="${org.libredemat.security.SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()}">
  <dt>${message(code:'homeFolder.adult.property.smsPermission')}</dt>
  <dd>
    <input type="hidden" name="_smsPermission">
    <input type="checkbox" id="smsPermission" name="smsPermission" class="required  validate-acceptance" ${adult?.smsPermission ?'checked' : ''} value="true">
  </dd>
  </g:if>
  <g:render template="edit/submit" model="['object': adult]" />
</form>
