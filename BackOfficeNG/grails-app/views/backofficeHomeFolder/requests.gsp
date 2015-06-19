<g:if test="${requests.isEmpty()}">
  <strong><g:message code="message.noResultFound"/></strong>
</g:if>
<g:else>
  <ul class="search-results">
    <g:render template="/backofficeRequest/searchResult" var="record" collection="${requests}" />
  </ul>
</g:else>
