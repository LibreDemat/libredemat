<style>
label.label_child{ display:initial; }
</style>
<%
  //Hack:
  if (flash.invalidFields) invalidFields = flash.invalidFields
  if (flash.adult) adult = flash.adult
%>
<input type="hidden" name="id" value="${adult?.id}" />
<input type="hidden" name="mode" value="create" />

<label for="title" class="required">
  ${message(code:'homeFolder.adult.property.title')} *
</label>
<select id="title" name="title"
    class="required validate-not-first ${invalidFields?.contains('title') ? 'validation-failed' : ''}"
    title="${message(code:'homeFolder.adult.property.title.validationError')}">
  <option value="">
    ${message(code:'message.select.defaultOption')}
  </option>
  <g:each in="${org.libredemat.business.users.TitleType.allTitleTypes}">
    <option value="${it.name()}"
        ${it == adult?.title ? 'selected="selected"' : ''}>
      <g:libredematEnumToText var="${it}" i18nKeyPrefix="homeFolder.adult.title" />
    </option>
  </g:each>
</select>

<label for="lastName" class="required">
  ${message(code:'homeFolder.individual.property.lastName')} *
</label>
<input type="text" id="lastName" name="lastName" value="${adult?.lastName}"
    class="required validate-lastName ${invalidFields?.contains('lastName') ? 'validation-failed' : ''}"
    title="${message(code:'homeFolder.individual.property.lastName.validationError')}" />

<label for="firstName" class="required">
  ${message(code:'homeFolder.individual.property.firstName')} *
</label>
<input type="text" id="firstName" name="firstName" value="${adult?.firstName}"
    class="required validate-firstName ${invalidFields?.contains('firstName') ? 'validation-failed' : ''}"
    title="${message(code:'homeFolder.individual.property.firstName.validationError')}" />

<g:if test="${hideAddressFields == null && (adult == null || adult.isHomeFolderResponsible() || adult.getId() == null)}">
<label class="required">${message(code:'homeFolder.individual.property.address')} *</label>
<div id="adultAddress" class="address required ${invalidFields?.contains('address') ? 'validation-failed' : ''}">
  <label for="adultAddress_additionalDeliveryInformation">${message(code:'address.property.additionalDeliveryInformation')}</label>
  <input type="text" class="validate-addressLine38 ${invalidFields?.contains('address.additionalDeliveryInformation') ? 'validation-failed' : ''}" maxlength="38" id="adultAddress_additionalDeliveryInformation" name="address.additionalDeliveryInformation"
      value="${adult?.address?.additionalDeliveryInformation}" />
  <label for="adultAddress_additionalGeographicalInformation">${message(code:'address.property.additionalGeographicalInformation')}</label>
  <input type="text" class="validate-addressLine38 ${invalidFields?.contains('address.additionalGeographicalInformation') ? 'validation-failed' : ''}" maxlength="38" id="adultAddress_additionalGeographicalInformation" name="address.additionalGeographicalInformation"
      value="${adult?.address?.additionalGeographicalInformation}" />
  <label for="adultAddress_streetNumber">${message(code:'address.property.streetNumber')}</label> -
  <label for="adultAddress_streetName" class="required">${message(code:'address.property.streetName')} *</label><br />
  <input type="text" class="line1 validate-streetNumber ${invalidFields?.contains('address.streetNumber') ? 'validation-failed' : ''}" size="5" maxlength="5" id="adultAddress_streetNumber" name="address.streetNumber"
      value="${adult?.address?.streetNumber}" autocomplete="off" />
  <input type="text" class="line2 required ${isAddresseReferentialCityRestriction != null && isAddresseReferentialCityRestriction ? 'validate-streetNameReferential' : 'validate-streetName'} ${invalidFields?.contains('address.streetName') ? 'validation-failed' : ''}" maxlength="32" id="adultAddress_streetName" name="address.streetName" title="${message(code:'address.property.streetName.validationError')}"
      value="${adult?.address?.streetName}" autocomplete="off" />
  <label for="adultAddress_placeNameOrService">${message(code:'address.property.placeNameOrService')}</label>
  <input type="text" class="validate-addressLine38 ${invalidFields?.contains('address.placeNameOrService') ? 'validation-failed' : ''}" maxlength="38" id="adultAddress_placeNameOrService" name="address.placeNameOrService"
      value="${adult?.address?.placeNameOrService}" />
  <label for="adultAddress_postalCode" class="required">${message(code:'address.property.postalCode')} * </label> -
  <label for="adultAddress_city" class="required">${message(code:'address.property.city')} *</label><br />
  <input type="text" class="line1 required validate-postalCode ${invalidFields?.contains('address.postalCode') ? 'validation-failed' : ''}" size="5" maxlength="5" id="adultAddress_postalCode" name="address.postalCode" title="${message(code:'address.property.postalCode.validationError')}"
      value="${adult?.address?.postalCode}" autocomplete="off" />
  <input type="text" class="line2 required validate-city ${invalidFields?.contains('address.city') ? 'validation-failed' : ''}" maxlength="32" id="adultAddress_city" name="address.city" title="${message(code:'address.property.city.validationError')}"
      value="${adult?.address?.city}" autocomplete="off" />
  <label for="adultAddress_countryName">${message(code:'address.property.countryName')}</label>
  <input type="text" class="validate-addressLine38 ${invalidFields?.contains('address.countryName') ? 'validation-failed' : ''}" maxlength="38" id="adultAddress_countryName" name="address.countryName"
      value="${adult?.address?.countryName}" />
  <input type="hidden" id="adultAddress_streetMatriculation" name="address.streetMatriculation" value="${adult?.address?.streetMatriculation}" />
  <input type="hidden" id="adultAddress_streetRivoliCode" name="address.streetRivoliCode" value="${adult?.address?.streetRivoliCode}" />
  <input type="hidden" id="adultAddress_cityInseeCode" name="address.cityInseeCode" value="${adult?.address?.cityInseeCode}" />
</div>
</g:if>

<label for="email" class="required">
  ${message(code:'homeFolder.adult.property.email')} *
  <span>
    ${message(code:'homeFolder.adult.property.email.help')}
  </span>
</label>
<input type="text" id="email" name="email" value="${adult?.email}"
    class="required validate-email ${invalidFields?.contains('email') ? 'validation-failed' : ''}"
    title="${message(code:'homeFolder.adult.property.email.validationError')}" />
<label class="required">
  ${message(code:'homeFolder.adult.label.phones')} *
  <span>
    (${message(code:'homeFolder.adult.label.phones.help')})
  </span>
</label>
<div id="adultPhones"
  class="address ${invalidFields?.contains('adultPhones') ? 'validation-failed' : ''}">
  <label for="homePhone">
    ${message(code:'homeFolder.adult.property.homePhone')}
    <span>
      ${message(code:'homeFolder.adult.property.homePhone.help')}
    </span>
  </label>
  <input type="text" id="homePhone" name="homePhone" value="${adult?.homePhone}"
      class="validate-phone ${invalidFields?.contains('homePhone') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.homePhone.validationError')}" />
  <label for="mobilePhone">
    ${message(code:'homeFolder.adult.property.mobilePhone')}
    <span>
      ${message(code:'homeFolder.adult.property.mobilePhone.help')}
    </span>
  </label>
  <input type="text" id="mobilePhone" name="mobilePhone" value="${adult?.mobilePhone}"
      class="validate-mobilePhone ${invalidFields?.contains('mobilePhone') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.mobilePhone.validationError')}" />
  <label for="officePhone">
    ${message(code:'homeFolder.adult.property.officePhone')}
    <span>
      ${message(code:'homeFolder.adult.property.officePhone.help')}
    </span>
  </label>
  <input type="text" id="officePhone" name="officePhone" value="${adult?.officePhone}"
      class="validate-phone ${invalidFields?.contains('officePhone') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.adult.property.officePhone.validationError')}" />
</div>
<g:if test="${children!=null && !children.isEmpty()}">
    <label for="relation">
        ${message(code:'homeFolder.adult.property.relation')}
    </label>
    <g:each var="child" in="${children}">
        <p>
            ${message(code:'homeFolder.adult.property.relation.label')}
            <g:each var="role" in="${org.libredemat.business.users.RoleType.childRoleTypes}">
                <input type="radio" name="roleType_${child.id}" value="${role}"/>
                ${g.libredematEnumToFlag(var:role, i18nKeyPrefix:'homeFolder.role.adult')}
            </g:each>
            ${message(code:'layout.from')}
            <label class="label_child">
                ${child.fullName}
            </label>
        </p>
    </g:each>
</g:if>
