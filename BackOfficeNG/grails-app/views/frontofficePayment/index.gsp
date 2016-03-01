<html>
  <head>
    <title>${message(code:'payment.title')}</title>
    <meta name="layout" content="fo_main"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'payment.css')}" />
    <script type="text/javascript" src="${resource(dir:'js/frontoffice', file:'payment.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice', file:'docShow.js')}"></script>
  </head>
  <body>
    <div id="yui-main">
      <div id="main" class="yui-b">
        <g:if test="${errorMessageKey}">
            <div class="error-box"><g:message code="${errorMessageKey}" /></div>
        </g:if>
        <g:if test="${paymentPopUp}">
            <div class="information-box">
                <g:message code="payment.message.checkPopup" />
            </div>
        </g:if>
        <g:if test="${displayedMessage}">
          <div class="information-box">${displayedMessage}</div>
        </g:if>
        <g:else>
          <g:if test="${!invoices.isEmpty()}">
            <div id="invoices" class="list-box">
              <h2><g:message code="payment.header.invoices"/></h2>
              <g:render template="invoices"/>
            </div>
          </g:if>
          <g:if test="${!depositAccounts.isEmpty()}">
            <div id="depositAccounts" class="list-box">
              <h2><g:message code="payment.header.depositAccounts"/></h2>
              <g:render template="depositAccounts"/>
            </div>
          </g:if>
          <g:if test="${!ticketingContracts.isEmpty()}">
            <div id="ticketingContracts" class="list-box">
              <h2><g:message code="payment.header.ticketingContracts"/></h2>
              <g:render template="ticketingContracts" />
            </div>
          </g:if>
          <g:if test="${!paymentsToPay.isEmpty()}">
            <div id="paymentsToPay">
              <g:render template="paymentList"
                        model="${[payments : paymentsToPay, paginate : false,
                                  title : 'payment.header.topay',
                                  noPaymentsMsg : 'payment.message.topay']}" />
            </div>
          </g:if>
          <g:if test="${invoices.isEmpty() && depositAccounts.isEmpty() && ticketingContracts.isEmpty()
                        && paymentsToPay.isEmpty()}">
            <div class="information-box">
              <g:message code="payment.message.noElementsToPay" />
            </div>
          </g:if>
        </g:else>
        <g:if test="${paymentPopUp && params.paymentUrl}">
          <script type="text/javascript">
            var popPayment=window.open('${params.paymentUrl}','_blank','height=700,width=900,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,directories=no,status=no');
            var timer = setInterval(function() {
                if(popPayment.closed) {
                  clearInterval(timer);
                  window.location.href='status';
                }
            }, 500);
          </script>
        </g:if>
      </div>
    </div>
    <!-- end of yui-main -->
    <div id="narrow" class="yui-b">
      <g:if test="${!displayedMessage}">
        <g:render template="cart"/>
      </g:if>
      <div class="narrow-box">
        <h3>
          <g:message code="header.display"/>
        </h3>
        <div class="body">
          <a class="top-link" href="${createLink(action: 'history')}">
            <g:message code="payment.header.paymentsHistory"/>
          </a>
        </div>
      </div>
    </div>
    <!-- end of narrow -->
    <div id="showBill">
      <div class="hd">Facture</div>
      <div class="bd">
        <div id="showPanelBody"></div>
      </div>
      <div class="ft"></div>
    </div>
  </body>
</html>
