<div class="mainbox mainbox-yellow" id="requestTypeDocuments">
  <h2>Etape pièces jointes obligatoire</h2>
  <form method="post" id="mandatoryDocumentStepForm" action="${createLink(action:'setMandatoryDocumentStep',params:['id': requestType.id,'label' : requestType.label])}">
    <div class="error" id="mandatoryDocumentStepErrors"></div>
    <p class="field">
      <label>
        Activer :
      </label>
      <span id="mandatoryDocumentStepInputDiv">
      <input id="mandatoryDocumentStepInput" type="radio" ${isMandatoryDocumentStep ? 'checked="checked"' : ''} value="1" name="mandatoryDocumentStep" />
        oui
      <input id="mandatoryDocumentStepInput" type="radio" ${isMandatoryDocumentStep ? '' : 'checked="checked"'} value="0" name="mandatoryDocumentStep" />
        non
      </span>
    </p>
  </form>
  <h2><g:message code="requestType.configuration.documents" /></h2>
  <div id="documentTypeFilterPanel" class="editableListSwithcher">
    <a id="showAssociatedDocuments">${message(code:'filter.viewBounded')}</a>/
    <a id="showUnassociatedDocuments" class="current">${message(code:'filter.viewUnbounded')}</a>/
    <a id="showAllDocuments">${message(code:'filter.viewAll')}</a>
  </div>
  <div id="documentList"></div>
</div>
