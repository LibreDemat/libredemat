<div id="child_${child.id}" class="individual collapse">
  <g:if test="${child?.state?.toString() != 'Archived' && !unarchivable}">
  <a class="confirmRemoveIndividual" style="float: right;">${message(code:'action.delete')}</a>
  </g:if>
  <a class="toggle">${message(code:'action.expand')} / ${message(code:'action.collapse')}</a>
  <dl class="required">
    <g:render template="static/id" model="['user':child]" />
  </dl>
  <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} individual-state required collapse">
    <g:render template="static/state" model="['user':child]" />
  </dl>
  <h3>${message(code:'homeFolder.individual.header.identity')}</h3>
  <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} individual-identity required collapse">
    <g:render template="static/childIdentity" model="['individual':child]" />
  </dl>
  <h3>${message(code:'homeFolder.individual.header.responsibles')}</h3>
  <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} child-responsibles required collapse">
    <g:render template="static/responsibles" model="['child':child, 'roleOwners': roleOwners]" />
  </dl>
  <g:each var="homeMapping" in="${homeMappings}">
   <g:set var="individualsMappings" value="${homeMapping.individualsMappings.groupBy { it.individualId }}" />
    <g:each var="mapping" in="${individualsMappings[child.id]}">
    <h3>${homeMapping.externalServiceLabel}</h3>
     <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} individual-${homeMapping.externalServiceLabel.replace(" ", "#")}-mapping required collapse">
       <g:render template="static/mapping" model="['mapping':mapping]" />
      </dl>
    </g:each>
  </g:each>
  <!-- Pour rajouter la modification de la fiche de renseignement : -->
  <!-- rajouter child-childInformationSheet  -->
  <!-- et -->
  <!-- ${child?.state?.toString() != 'Archived' ? 'edit' : ''} -->
  <!-- dans la class du <dl> -->
  <!-- : -->
  <!-- <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} child-childInformationSheet collapse"> -->
  <g:if test="${informationSheetDisplayed}" >
	  <h3>${message(code:'homeFolder.individual.header.informationSheet')}</h3>
	  <dl class="${child?.state?.toString() != 'Archived' ? 'edit' : ''} child-childInformationSheet collapse">
	    <g:render template="static/childInformationSheet" model="['child':child]" />
	  </dl>
  </g:if>
</div>
