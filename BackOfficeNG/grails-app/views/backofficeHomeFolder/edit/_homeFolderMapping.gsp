<form method="post"
  action="${g.createLink(action:'homeFolderMapping', params:['id':individualId, 'mappingId':mapping.id, 'externalServiceLabel':mapping.externalServiceLabel])}"
  id="form_${mapping.externalServiceLabel}_${mapping.homeFolderId}_${mapping.externalId}">
  <dt class="required">${message(code:'homeFolder.property.externalAccountId')}</dt>
  <dd class="required">
    <input type="text" name="externalId" value="${mapping.externalId}" />
  </dd>
  <input type="hidden" name="homeFolderId" value="${mapping.homeFolderId}" />
  <dt>&nbsp;</dt>
  <dd style="font-size: .95em;">
   <input type="hidden" name="mode" value="${!mapping.id ? 'add' : 'modify'}" />
   <input type="submit" name="submit" value="${message(code:'action.save')}" class="save" />
   <a class="cancel${!mapping.id ? 'Add' : ''}">
     ${message(code:'action.cancel')}
   </a>
 </dd>
</form>
