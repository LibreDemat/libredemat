<g:if test="${adult.isHomeFolderResponsible()}">
  <a href="${createLink(action:'adult', params:['id':adult.id, 'fragment':'address'])}#address" class="modify">
    ${message(code:'action.modify')}
  </a>
</g:if>

<g:if test="${!adult.isHomeFolderResponsible()}">
   <p>L'adresse ne peut être modifiée que depuis la fiche du responsable du compte.</p>
</g:if>

<dl>
  <dt>${message(code:'homeFolder.individual.property.address')} : </dt>
  <dd>
    <div>
      <g:if test="${adult.address.additionalDeliveryInformation}">
        <p>${adult.address.additionalDeliveryInformation}</p>
      </g:if>
      <g:if test="${adult.address.additionalGeographicalInformation}">
        <p>${adult.address.additionalGeographicalInformation}</p>
      </g:if>
      <p>${adult.address.streetNumber} ${adult.address.streetName}</p>
      <g:if test="${adult.address.placeNameOrService}">
        <p>${adult.address.placeNameOrService}</p>
      </g:if>
      <p>${adult.address.postalCode} ${adult.address.city}</p>
      <g:if test="${adult.address.countryName}">
        <p>${adult.address.countryName}</p>
      </g:if>
    </div>
  </dd>
</dl>
