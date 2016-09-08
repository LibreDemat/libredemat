<html>
  <head>
    <title>${message(code:'activity.title.details')}</title>
    <meta name="layout" content="fo_main"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'reservation.css')}" />
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'reservation.js')}"></script>
  </head>

  <body>
  <g:if test="${commonInfo != null}">
    <div class="information-box"><h2>Information</h2>${commonInfo}</div>
  </g:if>
  <div id="yui-main">

    <div id="main" class="yui-b">
      <div class="main-box">
        <h2>
          <span style="display: inline;line-height: 0.5em;font-size: 1em;" class="activity planning ${isAdult ? 'adult':'child'}"><g:if test="${activityLabel}">
          ${activityLabel}
          </g:if>
          <g:else>
            <g:if test="${activityCode}">
              <g:translateRequestTypeLabel label="${activityCode}"/>
            </g:if>
          </g:else>
          </span>
           <g:message code="message.for" /> ${individual}
        </h2>
        <table class="paginate"><!-- two button to navigate between month -->
          <tr>
            <td class="pre">
               <form action="" method="get">
                <input type="hidden" name="month" value="${monthPre}" />
                <input type="hidden" name="year" value="${yearPre}" />
                <input id="activityCode" type="hidden" name="activityCode" value="${activityCode}" />
                <input type="hidden" name="childId" value="${childId}" />
                <input type="image" src="../../images/icons/prec.png" />
               </form>
             </td>
             <td class="monthName">${monthsNames[month.toInteger()]}</td>
            <td class="sui">
              <form action="" method="get">
                <input type="hidden" name="month" value="${monthSui}" />
                <input type="hidden" name="year" value="${yearSui}" />
                <input type="hidden" name="activityCode" value="${activityCode}" />
                <input type="hidden" name="childId" value="${childId}" />
                <input type="image" src="../../images/icons/suiv.png" />
              </form>
              </td>
          </tr>
        </table>
        <div class="icon-legend">
          <span class="legend-reservation-valid">&nbsp;</span><g:message code="reservation.validated" />
          <span class="legend-reservation-en-cours">&nbsp;</span><g:message code="reservation.inprogress" />
          <span class="legend-reservation-report-en-cours">&nbsp;</span><g:message code="report.inprogress" />
        </div>
        <!-- call the tag lib PlanningTagLib to dispaly planing  -->
        <g:activityPlanning  month="${month}" year="${year}" data="${data}" deadLine="${deadLine}"
        	childId="${childId}" activityCode="${activityCode}"/>
        <table class="paginate"><!-- two button to navigate between month -->
          <tr>
            <td class="pre">
               <form action="" method="get">
                <input type="hidden" name="month" value="${monthPre}" />
                <input type="hidden" name="year" value="${yearPre}" />
                <input id="activityCode" type="hidden" name="activityCode" value="${activityCode}" />
                <input type="hidden" name="childId" value="${childId}" />
                <input type="image" src="../../images/icons/prec.png" />
               </form>
             </td>
             <td class="monthName">${monthsNames[month.toInteger()]}</td>
            <td class="sui">
              <form action="" method="get">
                <input type="hidden" name="month" value="${monthSui}" />
                <input type="hidden" name="year" value="${yearSui}" />
                <input type="hidden" name="activityCode" value="${activityCode}" />
                <input type="hidden" name="childId" value="${childId}" />
                <input type="image" src="../../images/icons/suiv.png" />
              </form>
              </td>
          </tr>
        </table>

      </div>
    </div>
  </div>
  <!-- end of yui-main -->

  <div id="narrow" class="yui-b">
    <div class="narrow-box">
      <h3>
        <g:message code="header.planningCart" />
      </h3>
      <div class="body">
        <g:if test="${session.instantPayment != false}">
          Etat du compte avant validation :
          <g:formatNumber number="${new BigDecimal(session.amountInCent,2)}" type="currency" currencyCode="EUR"/>
        </g:if>
        <!-- todo get the reservation activity during session -->
        <div id="planingActivity" class="${session.homeFolderId}"></div>
        <!-- g:if test="${!BigDecimal.ZERO.equals(new BigDecimal(session.amountInCent))}" -->
          <g:if test="${session.instantPayment != false}">
            <input type="image" src="../../images/icons/confirmer.png" id="pay-${session.homeFolderId}" class="pay"/>
          </g:if>
          <g:else>
            <input type="image" src="../../images/icons/confirmer.png" id="update-${session.homeFolderId}" class="update"/>
          </g:else>
        <!-- /g:if -->
        <input type="image" src="../../images/icons/detail.png" id="detail-${session.homeFolderId}" class="detail"/>
        <input type="image" src="../../images/icons/annuler.png" id="cancel-${session.homeFolderId}-${(session.instantPayment != false)?('pre'):('post')}" class="cancel"/>
      </div>
    </div>
    <div class="narrow-box">
      <h3>
        <g:message code="header.display" />
      </h3>
      <div class="body">
        <a class="top-link" href="${createLink(action:'index', params : ['month' : month, 'year' : year]).encodeAsXML()}">
          <g:message code="activity.header.mainPanel" />
        </a>
      </div>
    </div>
    <g:render template="filter" />
    <div class="narrow-box">
      <h3>
        <g:message code="header.monthly.reservation" />
      </h3>
      <div class="body">
        <ul id="toolbar" class="legend">
        	<g:legendService data="${data}" month="${month}" year="${year}"
        		activityCode="${activityCode}" childId="${childId}" payment="${session.instantPayment}"/>
        </ul>
      </div>
    </div>
  </div>
  <!-- end of narrow -->
  <!-- Contruct all the panel we need for display -->
  <div id="dayPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  <div id="cancelPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  <div id="monthPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  <div id="detailPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  <div id="messagePanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
   <div id="messagePanelPay">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  <div id="waitPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
   <div id="errorPanel">
      <div class="hd"></div>
      <div class="bd"></div>
      <div class="ft"></div>
  </div>
  </body>
</html>
