  <ul>
    <g:each var="record" in="${requests.records}">
      <li>
        <p>
          <g:capdematEnumToFlag var="${record.state}" i18nKeyPrefix="request.state" />
          <g:if test="${record.state == 'Draft'}">
            <span class="tag-state">
              <a href="${createLink(action:'deleteDraft',controller:'frontofficeRequest',id:record.id)}">
                <g:message code="action.remove"/>
              </a>
            </span>
          </g:if>
          <g:elseif test="${record.isEditable}">
            <span class="tag-state">
              <a href="${createLink(action:'edit',controller:'frontofficeRequest',id:record.id)}">
                <g:message code="action.modify"/>
              </a>
            </span>
          </g:elseif>
          <g:if test="${record.state != 'Archived'}">
            <a href="${createLink(controller:'frontofficeRequest',action:(record.state != 'Draft' ? 'summary' : 'edit'),id:record.id)}">
          </g:if>
            ${record.label}
            <g:message code="request.searchResult.requestId" />
            <span>${record.id}</span>
            - <g:message code="layout.from" /> 
            <span>${record.requesterName}</span>
            <g:if test="${record.subjectName && record.subjectName != ''}">
             <g:message code="layout.for" />
             ${record.subjectName}
            </g:if>
          <g:if test="${record.state != 'Archived'}">
            </a>
          </g:if>
        </p>
        <p>
          <g:message code="request.searchResult.creationDate" 
            args="${[formatDate(date:record.creationDate,formatName:'format.date')]}"/> 
          <g:if test="${record.lastModificationDate}">
            - <g:message code="request.property.lastModificationDate" />
            <g:formatDate date="${record.lastModificationDate}" formatName="format.date" />
            <g:if test="${record.lastInterveningUserId}">
              <g:message code="layout.by" />
              ${record.lastInterveningUserId}
            </g:if>
          </g:if> 
        </p>
        <g:if test="${record.state == 'Archived'}">
          <p class="agent-note">
            <g:message code="request.message.archived" />
          </p>
        </g:if>
        <g:if test="${record.externalInformations}">
          <p>
            <g:each status="i" var="externalInformation" in="${record.externalInformations}">
              <g:message code="${externalInformation.key}" /> : ${externalInformation.value + (i == record.externalInformations.size() - 1 ? "" : " - ")}
            </g:each>
          </p>
        </g:if>
        <g:if test="${record.lastAgentNote}">
          <p class="agent-note">
            <g:message code="request.property.lastAgentNote" />
            <g:if test="${record.lastAgentNote.date}">
                <g:message code="layout.on.date" /> <g:formatDate date="${record.lastAgentNote.date}" formatName="format.date" />
              </g:if>
            <g:message code="layout.by" />&nbsp;${record.lastAgentNote.user_name} :
            ${record.lastAgentNote.note}
          </p>
        </g:if>
      </li>
    </g:each>
  </ul>
