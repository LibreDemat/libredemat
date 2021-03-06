


  
    <fieldset class="required" id="Folders">
    <legend><g:message code="hcar.property.folders.label" /></legend>
    
      
      <label class="required"><g:message code="hcar.property.foldersMdph.label" /> *  <span><g:message code="hcar.property.foldersMdph.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['folders'].invalidFields.contains('foldersMdph') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="foldersMdph_${it ? 'yes' : 'no'}" class="required condition-isMDPH-trigger  validate-one-required boolean" title="" value="${it}" name="foldersMdph" ${it == rqt.foldersMdph ? 'checked="checked"': ''} />
                <label for="foldersMdph_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="foldersMdphNumber" class="condition-isMDPH-filled"><g:message code="hcar.property.foldersMdphNumber.label" />   <span><g:message code="hcar.property.foldersMdphNumber.help" /></span></label>
            <input  type="text" id="foldersMdphNumber"
                   name="foldersMdphNumber"
                   value="${rqt.foldersMdphNumber?.toString()}"
                   class="condition-isMDPH-filled   ${rqt.stepStates['folders'].invalidFields.contains('foldersMdphNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersMdphNumber.validationError" />"  maxlength="30" />
            

    
      
      <label for="foldersMdphDepartment" class="condition-isMDPH-filled"><g:message code="hcar.property.foldersMdphDepartment.label" />   <span><g:message code="hcar.property.foldersMdphDepartment.help" /></span></label>
            <input  type="text" id="foldersMdphDepartment"
                   name="foldersMdphDepartment"
                   value="${rqt.foldersMdphDepartment?.toString()}"
                   class="condition-isMDPH-filled  validate-departmentCode ${rqt.stepStates['folders'].invalidFields.contains('foldersMdphDepartment') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersMdphDepartment.validationError" />"  maxlength="2" />
            

    
      
      <label class="required"><g:message code="hcar.property.foldersCotorep.label" /> *  <span><g:message code="hcar.property.foldersCotorep.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['folders'].invalidFields.contains('foldersCotorep') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="foldersCotorep_${it ? 'yes' : 'no'}" class="required condition-isCOTOREP-trigger  validate-one-required boolean" title="" value="${it}" name="foldersCotorep" ${it == rqt.foldersCotorep ? 'checked="checked"': ''} />
                <label for="foldersCotorep_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="foldersCotorepNumber" class="condition-isCOTOREP-filled"><g:message code="hcar.property.foldersCotorepNumber.label" />   <span><g:message code="hcar.property.foldersCotorepNumber.help" /></span></label>
            <input  type="text" id="foldersCotorepNumber"
                   name="foldersCotorepNumber"
                   value="${rqt.foldersCotorepNumber?.toString()}"
                   class="condition-isCOTOREP-filled   ${rqt.stepStates['folders'].invalidFields.contains('foldersCotorepNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersCotorepNumber.validationError" />"  maxlength="30" />
            

    
      
      <label for="foldersCotorepDepartment" class="condition-isCOTOREP-filled"><g:message code="hcar.property.foldersCotorepDepartment.label" />   <span><g:message code="hcar.property.foldersCotorepDepartment.help" /></span></label>
            <input  type="text" id="foldersCotorepDepartment"
                   name="foldersCotorepDepartment"
                   value="${rqt.foldersCotorepDepartment?.toString()}"
                   class="condition-isCOTOREP-filled  validate-departmentCode ${rqt.stepStates['folders'].invalidFields.contains('foldersCotorepDepartment') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersCotorepDepartment.validationError" />"  maxlength="2" />
            

    
      
      <label class="required"><g:message code="hcar.property.foldersCdes.label" /> *  <span><g:message code="hcar.property.foldersCdes.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['folders'].invalidFields.contains('foldersCdes') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="foldersCdes_${it ? 'yes' : 'no'}" class="required condition-isCDES-trigger  validate-one-required boolean" title="" value="${it}" name="foldersCdes" ${it == rqt.foldersCdes ? 'checked="checked"': ''} />
                <label for="foldersCdes_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="foldersCdesNumber" class="condition-isCDES-filled"><g:message code="hcar.property.foldersCdesNumber.label" />   <span><g:message code="hcar.property.foldersCdesNumber.help" /></span></label>
            <input  type="text" id="foldersCdesNumber"
                   name="foldersCdesNumber"
                   value="${rqt.foldersCdesNumber?.toString()}"
                   class="condition-isCDES-filled   ${rqt.stepStates['folders'].invalidFields.contains('foldersCdesNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersCdesNumber.validationError" />"  maxlength="30" />
            

    
      
      <label for="foldersCdesDepartment" class="condition-isCDES-filled"><g:message code="hcar.property.foldersCdesDepartment.label" />   <span><g:message code="hcar.property.foldersCdesDepartment.help" /></span></label>
            <input  type="text" id="foldersCdesDepartment"
                   name="foldersCdesDepartment"
                   value="${rqt.foldersCdesDepartment?.toString()}"
                   class="condition-isCDES-filled  validate-departmentCode ${rqt.stepStates['folders'].invalidFields.contains('foldersCdesDepartment') ? 'validation-failed' : ''}"
                   title="<g:message code="hcar.property.foldersCdesDepartment.validationError" />"  maxlength="2" />
            

    
      
      <label class="required"><g:message code="hcar.property.foldersOtherFolders.label" /> *  <span><g:message code="hcar.property.foldersOtherFolders.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['folders'].invalidFields.contains('foldersOtherFolders') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="foldersOtherFolders_${it ? 'yes' : 'no'}" class="required condition-isOtherFolders-trigger  validate-one-required boolean" title="" value="${it}" name="foldersOtherFolders" ${it == rqt.foldersOtherFolders ? 'checked="checked"': ''} />
                <label for="foldersOtherFolders_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
    </fieldset>
  

  
    <div class="collection condition-isOtherFolders-filled summary-box">
      <h4 class="condition-isOtherFolders-filled"><g:message code="hcar.property.otherFolders.label" /> 
        <span><g:message code="hcar.property.otherFolders.help" /></span>
      </h4>

    <g:if test="${true && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit',            params:['id':rqt.id, 'currentStep':'folders', 'currentCollection':'otherFolders',
                    'collectionIndex':(rqt.otherFolders ? rqt.otherFolders.size() : 0),
                    'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}"
           style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.otherFolders}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="hcar.property.otherFolders.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'folders', 'currentCollection':'otherFolders', 'collectionIndex':index, 'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'folders', 'currentCollection':'otherFolders', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="hcar.property.otherFolderName.label" /></dt>
        <dd class="${rqt.stepStates['folders'].invalidFields.contains('otherFolders[' + index + '].otherFolderName') ? 'validation-failed' : ''}">${it.otherFolderName?.toString()}</dd>
    
        <dt><g:message code="hcar.property.otherFolderNumber.label" /></dt>
        <dd class="${rqt.stepStates['folders'].invalidFields.contains('otherFolders[' + index + '].otherFolderNumber') ? 'validation-failed' : ''}">${it.otherFolderNumber?.toString()}</dd>
    
        <dt><g:message code="hcar.property.otherFolderDepartment.label" /></dt>
        <dd class="${rqt.stepStates['folders'].invalidFields.contains('otherFolders[' + index + '].otherFolderDepartment') ? 'validation-failed' : ''}">${it.otherFolderDepartment?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

