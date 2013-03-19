<div id="menu">
  <a href="${createLink(controller : 'frontofficeRequestType')}"
    class="${menu.current(elem : 'requesttype')}" accesskey="1">
    <span>
      <g:message code="menu.services" />
    </span>
  </a>
  <g:if test="${session.additionalTabs.contains('Planning')}">
    <a href="${createLink(controller : 'frontofficePlanning')}"
      class="${menu.current(elem : 'planning')}">
      <span>
        <g:message code="menu.planning" />
      </span>
    </a>
  </g:if>
</div>
