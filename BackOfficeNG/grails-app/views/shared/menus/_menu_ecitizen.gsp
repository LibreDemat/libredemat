<div id="menu">
  <ul>
    <li><a href="${createLink(controller : 'frontofficeHome')}"
    class="${menu.current(elem : 'home')}">
    <span>
      <g:message code="menu.home" />
    </span>
  </a></li>
  <li><a href="${createLink(controller : 'frontofficeRequestType')}"
    class="${menu.current(elem : 'requesttype')}">
    <span>
      <g:message code="menu.services" />
    </span>
  </a></li>
  <li><a href="${createLink(controller : 'frontofficeHomeFolder')}"
    class="${menu.current(elem : 'homefolder') ? menu.current(elem : 'homefolder') : menu.current(elem : 'homefolderDocument')}">
    <span>
      <g:message code="menu.accounts" />
    </span>
  </a></li>
  <li><a href="${createLink(controller : 'frontofficeRequest')}"
    class="${menu.current(elem : 'request') ? menu.current(elem : 'request') : menu.current(elem : 'requestDocument')}">
    <span>
      <g:message code="menu.requests" />
    </span>
  </a></li>
  <li><a href="${createLink(controller : 'frontofficeDocument')}"
    class="${menu.current(elem : 'document')}">
    <span>
      <g:message code="menu.documents" />
    </span>
  </a></li>
  <g:if test="${session.additionalTabs.contains('Activities')}">
    <li><a href="${createLink(controller : 'frontofficeActivity')}"
      class="${menu.current(elem : 'activity')}">
      <span>
        <g:message code="menu.activities" />
      </span>
    </a></li>
  </g:if>
  <g:if test="${session.additionalTabs.contains('Planning')}">
    <li><a href="${createLink(controller : 'frontofficePlanning')}"
      class="${menu.current(elem : 'planning')}">
      <span>
        <g:message code="menu.planning" />
      </span>
    </a></li>
  </g:if>
  <g:if test="${session.additionalTabs.contains('Payments')}">
    <li><a href="${createLink(controller : 'frontofficePayment')}"
      class="${menu.current(elem : 'payment')}">
      <span>
        <g:message code="menu.payments" />
      </span>
    </a></li>
  </g:if>
</ul>
</div>
