<form id="draftForm" method="post" action="${createLink(action:'draft')}" class="exitActionForm">
  <g:if test="${draftedMessage}">
    <span class="drafted-message">${draftedMessage}</span>
  </g:if>
  <input type="hidden" name="requestTypeLabel" value="${requestTypeLabel}" />
  <input type="hidden" name="uuidString" value="${uuidString}" />
  <input type="submit" value="${message(code:'action.saveDraft')}"
         id="submitDraft" name="submitDraft" ${session.currentUser && draftVisible ? '' : 'disabled=\"disabled\"'}/>
</form>
<form action="${module.createLink(controller:'RequestCreationController',action:'condition')}" 
  method="post" id="conditionsForm">
  
  <input type="hidden" id="conditionsContainer" name="conditionsContainer" value="" />
  <input type="hidden" name="requestTypeLabel" value="${requestTypeLabel}" />
</form>
