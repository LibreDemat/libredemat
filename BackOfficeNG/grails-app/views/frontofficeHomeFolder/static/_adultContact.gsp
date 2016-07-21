<a href="${createLink(action:'adult', params:['id':adult.id, 'fragment':'contact'])}#contact" class="modify">
  ${message(code:'action.modify')}
</a>
<dl>
  <dt>${message(code:'homeFolder.adult.property.email')} : </dt>
  <dd>${adult.email}</dd>
  <dt>${message(code:'homeFolder.adult.property.homePhone')} : </dt>
  <dd>${adult.homePhone}</dd>
  <dt>${message(code:'homeFolder.adult.property.mobilePhone')} : </dt>
  <dd>${adult.mobilePhone}</dd>
  <dt>${message(code:'homeFolder.adult.property.officePhone')} : </dt>
  <dd>${adult.officePhone}</dd>
  <g:if test="${org.libredemat.security.SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()}">
  <dt>${message(code:'homeFolder.adult.property.smsPermission')} : </dt>
  <dd>${message(code:'action.'+ (adult.smsPermission==true ? 'yes':'no'))}</dd>
  </g:if>
</dl>
