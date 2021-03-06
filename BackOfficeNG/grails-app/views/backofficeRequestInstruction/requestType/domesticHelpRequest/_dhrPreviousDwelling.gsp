

  <g:set var="listSize" value="${rqt.dhrPreviousDwelling.size()}" />
  <h3>
    <a class="addListItem" id="add_dhrPreviousDwelling[${listSize}]"></a>
    <span><g:message code="dhr.property.dhrPreviousDwelling.label" /></span>
  </h3>
  <g:each var="it" in="${rqt.dhrPreviousDwelling.reverse()}" status="index">
  <div class="collection-action">
    <a class="deleteListItem" id="delete_dhrPreviousDwelling[${listSize - 1 - index}]"></a>
  </div>
  <dl class="required">
    
      <dt class="required"><g:message code="dhr.property.dhrPreviousDwellingAddress.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingAddress" class="action-editField validate-address required-true i18n-dhr.property.dhrPreviousDwellingAddress" >
        <div><p class="additionalDeliveryInformation">${it?.dhrPreviousDwellingAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${it?.dhrPreviousDwellingAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${it?.dhrPreviousDwellingAddress?.streetNumber}</span> <span class="streetName">${it?.dhrPreviousDwellingAddress?.streetName}</span><g:if test="${!!it?.dhrPreviousDwellingAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${it?.dhrPreviousDwellingAddress?.streetMatriculation}</span></g:if><g:if test="${!!it?.dhrPreviousDwellingAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${it?.dhrPreviousDwellingAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${it?.dhrPreviousDwellingAddress?.placeNameOrService}</p><span class="postalCode">${it?.dhrPreviousDwellingAddress?.postalCode}</span> <span class="city">${it?.dhrPreviousDwellingAddress?.city}</span><p class="countryName">${it?.dhrPreviousDwellingAddress?.countryName}</p><g:if test="${!!it?.dhrPreviousDwellingAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${it?.dhrPreviousDwellingAddress?.cityInseeCode}</span></g:if></div>
      </dd>
    
      <dt class="required condition-isPreviousDwellingPlaceOfResidence-trigger"><g:message code="dhr.property.dhrPreviousDwellingKind.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingKind" class="action-editField validate-libredematEnum required-true i18n-dhr.property.dhrPreviousDwellingKind javatype-org.libredemat.business.request.social.DhrDwellingKindType" >
        <g:libredematEnumToField var="${it?.dhrPreviousDwellingKind}" i18nKeyPrefix="dhr.property.dhrPreviousDwellingKind" />
      </dd>
    
      <dt class="required condition-isPreviousDwellingPlaceOfResidence-filled"><g:message code="dhr.property.dhrPreviousDwellingStatus.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingStatus" class="action-editField validate-libredematEnum required-true i18n-dhr.property.dhrPreviousDwellingStatus javatype-org.libredemat.business.request.social.DhrDwellingStatusType" >
        <g:libredematEnumToField var="${it?.dhrPreviousDwellingStatus}" i18nKeyPrefix="dhr.property.dhrPreviousDwellingStatus" />
      </dd>
    
      <dt class="required"><g:message code="dhr.property.dhrPreviousDwellingArrivalDate.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingArrivalDate" class="action-editField validate-date required-true i18n-dhr.property.dhrPreviousDwellingArrivalDate" >
        <span><g:formatDate formatName="format.date" date="${it?.dhrPreviousDwellingArrivalDate}"/></span>
      </dd>
    
      <dt class="required"><g:message code="dhr.property.dhrPreviousDwellingDepartureDate.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingDepartureDate" class="action-editField validate-date required-true i18n-dhr.property.dhrPreviousDwellingDepartureDate" >
        <span><g:formatDate formatName="format.date" date="${it?.dhrPreviousDwellingDepartureDate}"/></span>
      </dd>
    
      <dt class="required"><g:message code="dhr.property.dhrPreviousDwellingComment.label" /> * : </dt>
      <dd id="dhrPreviousDwelling[${listSize - 1 - index}].dhrPreviousDwellingComment" class="action-editField validate-string required-true i18n-dhr.property.dhrPreviousDwellingComment" >
        <span>${it?.dhrPreviousDwellingComment}</span>
      </dd>
    
  </dl>
  </g:each>
