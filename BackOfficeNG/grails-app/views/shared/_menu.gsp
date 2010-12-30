<h1><a class="logo">CapDémat</a></h1>
<div id="userInfo">
  <span style="font-weight:bold;">${session.currentUser}</span> 
  - <a href="${createLink(controller:'backofficeLogin',action:'logout')}">
      <g:message code="action.logout" />
    </a>
  - <a href="${createLink(controller:'localAuthorityResource',action:'resource',id:'helpBo')}"
       target="blank"><g:message code="header.help" /></a>
</div>
<div id="menu">
  <g:if test="${session.currentCredentialBean.hasSiteAgentRole()}">
    <a id="taskBoardMenuItem" href="${createLink(controller:'backofficeRequest',action:'taskBoard')}">
      <g:message code="menu.taskBoard" />
    </a>
    <a id="requestMenuItem" href="${createLink(controller:'backofficeRequest')}">
      <g:message code="menu.search" />
    </a>
    <g:if test="${session.isACategoryManager}">
      <a id="requestTypeMenuItem" href="${createLink(controller:'backofficeRequestType')}">
       <g:message code="menu.requestTypes" />
      </a>
      <a id="statisticsMenuItem" href="${createLink(controller:'backofficeStatistic')}">
        <g:message code="menu.statistics" />
      </a>
    </g:if>
  </g:if>
  <g:if test="${session.currentCredentialBean.hasSiteAdminRole()}">
    <a id="localAuthorityMenuItem" href="${createLink(controller:'backofficeLocalAuthority')}">
      <g:message code="menu.localAuthority" />
    </a>
     <a id="requestAdminMenuItem" href="${createLink(controller:'backofficeRequestAdmin')}">
      <g:message code="menu.requestsAdmin" />
    </a>
    <a id="citizenMenuItem" href="${createLink(controller:'backofficeHomeFolder', action:'importHomeFolders')}">
      <g:message code="menu.citizens" />
    </a>
    <a id="paymentMenuItem" href="${createLink(controller:'backofficePayment')}">
      <g:message code="menu.payments" />
    </a>
  </g:if>
</div>

