<div class="list-box">
  <h2><g:message code="document.header.lastDocuments" /></h2>
  <g:if test="${dashBoard.documents?.records.size() > 0}">
    <ul>
      <g:each var="record" in="${dashBoard.documents.records}">
        <li>
          <g:libredematEnumToFlag var="${record.state}" i18nKeyPrefix="document.state" />
          <a href="${createLink(controller:'frontofficeDocument',action:'details',id:record.id)}">
            ${record.title}
            <g:message code="searchResult.id" />
            <span>${record.id}</span>
            <g:if test="${record.subject}">
              <g:message code="document.searchResult.subject" args="${[record.subject]}"/>
            </g:if>
            - <g:message code="document.searchResult.creationDate" 
                args="${[formatDate(date:record.creationDate,formatName:'format.date')]}" /> 
            <g:if test="${record.endValidityDate}">
              - <g:message code="document.searchResult.endValidityDate" 
                  args="${[formatDate(date:record.endValidityDate,formatName:'format.date')]}" /> 
            </g:if>
          </a>
        </li>
      </g:each>
    </ul>
    <p class="see-all">
      <a href="${createLink(controller:'frontofficeDocument')}" id="showAllDocuments">
        <g:message code="action.seeAll" />
      </a>
    </p>
  </g:if>
  <g:else>
    <p class="empty"><g:message code="message.noDocuments" /></p>
  </g:else>
</div>
