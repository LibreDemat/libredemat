

  <g:set var="listSize" value="${rqt.parkImatriculation.size()}" />
  <h3>
    <a class="addListItem" id="add_parkImatriculation[${listSize}]"></a>
    <span><g:message code="pcr.property.parkImatriculation.label" /></span>
  </h3>
  <g:each var="it" in="${rqt.parkImatriculation.reverse()}" status="index">
  <div class="collection-action">
    <a class="deleteListItem" id="delete_parkImatriculation[${listSize - 1 - index}]"></a>
  </div>
  <dl class="required">
    
      <dt class="required"><g:message code="pcr.property.immatriculation.label" /> * : </dt>
      <dd id="parkImatriculation[${listSize - 1 - index}].immatriculation" class="action-editField validate-string required-true i18n-pcr.property.immatriculation" >
        <span>${it?.immatriculation}</span>
      </dd>
    
      <dt class=""><g:message code="pcr.property.tarif.label" />  : </dt>
      <dd id="parkImatriculation[${listSize - 1 - index}].tarif" class="action-editField validate-string i18n-pcr.property.tarif" >
        <span>${it?.tarif}</span>
      </dd>
    
  </dl>
  </g:each>
