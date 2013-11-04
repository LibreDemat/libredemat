

  <g:set var="listSize" value="${rqt.familyDependents.size()}" />
  <h3>
    <a class="addListItem" id="add_familyDependents[${listSize}]"></a>
    <span><g:message code="hcar.property.familyDependents.label" /></span>
  </h3>
  <g:each var="it" in="${rqt.familyDependents.reverse()}" status="index">
  <div class="collection-action">
    <a class="deleteListItem" id="delete_familyDependents[${listSize - 1 - index}]"></a>
  </div>
  <dl class="required condition-isFamilyDependents-filled">
    
      <dt class="required"><g:message code="hcar.property.familyDependentLastName.label" /> * : </dt>
      <dd id="familyDependents[${listSize - 1 - index}].familyDependentLastName" class="action-editField validate-lastName required-true i18n-hcar.property.familyDependentLastName maxLength-38" >
        <span>${it?.familyDependentLastName}</span>
      </dd>
    
      <dt class="required"><g:message code="hcar.property.familyDependentFirstName.label" /> * : </dt>
      <dd id="familyDependents[${listSize - 1 - index}].familyDependentFirstName" class="action-editField validate-firstName required-true i18n-hcar.property.familyDependentFirstName maxLength-38" >
        <span>${it?.familyDependentFirstName}</span>
      </dd>
    
      <dt class="required"><g:message code="hcar.property.familyDependentBirthDate.label" /> * : </dt>
      <dd id="familyDependents[${listSize - 1 - index}].familyDependentBirthDate" class="action-editField validate-date required-true i18n-hcar.property.familyDependentBirthDate" >
        <span><g:formatDate formatName="format.date" date="${it?.familyDependentBirthDate}"/></span>
      </dd>
    
      <dt class="required"><g:message code="hcar.property.familyDependentActualSituation.label" /> * : </dt>
      <dd id="familyDependents[${listSize - 1 - index}].familyDependentActualSituation" class="action-editField validate-libredematEnum required-true i18n-hcar.property.familyDependentActualSituation javatype-org.libredemat.business.request.social.HcarFamilyDependentActualSituationType" >
        <g:libredematEnumToField var="${it?.familyDependentActualSituation}" i18nKeyPrefix="hcar.property.familyDependentActualSituation" />
      </dd>
    
  </dl>
  </g:each>
