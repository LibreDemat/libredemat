

  <g:set var="listSize" value="${rqt.otherFolders.size()}" />
  <h3>
    <a class="addListItem" id="add_otherFolders[${listSize}]"></a>
    <span><g:message code="hccr.property.otherFolders.label" /></span>
  </h3>
  <g:each var="it" in="${rqt.otherFolders.reverse()}" status="index">
  <div class="collection-action">
    <a class="deleteListItem" id="delete_otherFolders[${listSize - 1 - index}]"></a>
  </div>
  <dl class="condition-isOtherFolders-filled">
    
      <dt class="required"><g:message code="hccr.property.otherFolderName.label" /> * : </dt>
      <dd id="otherFolders[${listSize - 1 - index}].otherFolderName" class="action-editField validate- required-true i18n-hccr.property.otherFolderName maxLength-60" >
        <span>${it?.otherFolderName}</span>
      </dd>
    
      <dt class=""><g:message code="hccr.property.otherFolderNumber.label" />  : </dt>
      <dd id="otherFolders[${listSize - 1 - index}].otherFolderNumber" class="action-editField validate- i18n-hccr.property.otherFolderNumber maxLength-30" >
        <span>${it?.otherFolderNumber}</span>
      </dd>
    
      <dt class=""><g:message code="hccr.property.otherFolderDepartment.label" />  : </dt>
      <dd id="otherFolders[${listSize - 1 - index}].otherFolderDepartment" class="action-editField validate-departmentCode i18n-hccr.property.otherFolderDepartment maxLength-2" >
        <span>${it?.otherFolderDepartment}</span>
      </dd>
    
  </dl>
  </g:each>
