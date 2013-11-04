

  <g:set var="listSize" value="${rqt.homeIntervenants.size()}" />
  <h3>
    <a class="addListItem" id="add_homeIntervenants[${listSize}]"></a>
    <span><g:message code="hccr.property.homeIntervenants.label" /></span>
  </h3>
  <g:each var="it" in="${rqt.homeIntervenants.reverse()}" status="index">
  <div class="collection-action">
    <a class="deleteListItem" id="delete_homeIntervenants[${listSize - 1 - index}]"></a>
  </div>
  <dl class="condition-isHomeIntervenant-filled">
    
      <dt class="required condition-isOtherHomeIntervant-trigger"><g:message code="hccr.property.homeIntervenantKind.label" /> * : </dt>
      <dd id="homeIntervenants[${listSize - 1 - index}].homeIntervenantKind" class="action-editField validate-libredematEnum required-true i18n-hccr.property.homeIntervenantKind javatype-org.libredemat.business.request.social.HccrHomeIntervenantKindType" >
        <g:libredematEnumToField var="${it?.homeIntervenantKind}" i18nKeyPrefix="hccr.property.homeIntervenantKind" />
      </dd>
    
      <dt class="required condition-isOtherHomeIntervant-filled"><g:message code="hccr.property.homeIntervenantDetails.label" /> * : </dt>
      <dd id="homeIntervenants[${listSize - 1 - index}].homeIntervenantDetails" class="action-editField validate- required-true i18n-hccr.property.homeIntervenantDetails maxLength-60" >
        <span>${it?.homeIntervenantDetails}</span>
      </dd>
    
  </dl>
  </g:each>
