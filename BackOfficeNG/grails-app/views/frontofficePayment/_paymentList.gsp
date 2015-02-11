<g:if test="${payments?.all?.size() > 0}">
<div class="list-box">
  <h2><g:message code="${title}" /></h2>
    <g:if test="${paginate}">
      <p class="paginator">
        <g:paginate action="history" total="${payments.count}" max="${maxRows}" next="&gt;" prev="&lt;" params="${['ps':pageState]}"  />
      </p>
    </g:if>
    <ul>
      <g:each var="record" in="${payments.all}">
        <li>
            <g:libredematEnumToFlag var="${record.state}" i18nKeyPrefix="payment.state" />
            <h3>
              <g:message code="payment.property.payment" /> 
              <g:if test="${record.bankReference}">
                ${record.bankReference}
              </g:if>
              <g:message code="payment.searchResult.amount" /> 
              <span><g:formatNumber number="${record.amount / 100}" formatName="format.currency"/></span>
            </h3>
            <p>
              <g:message code="payment.searchResult.initializationDate" /> 
              <g:formatDate date="${record.initializationDate}" formatName="format.fullDate"/>
                -
                <g:if test="${record.state.toString().toLowerCase() == 'topay'}">
                <span>
                    <a href="${createLink(controller:'frontofficePayment',action:'newPayment',id:record.id, 'state' : state)}">
                        <g:message code="payment.action.topay"/>
                        <g:formatNumber number="${record.amount / 100}" type="currency" currencyCode="EUR" />
                        <g:message code="payment.action.to"/>
                        ${record.paymentMode}
                    </a>
                </span>
                </g:if>
                <g:elseif test="${record.state.toString().toLowerCase() == 'initialized'}">
                    <span>
                        <g:message code="payment.searchResult.initializedBy"/> ${record.paymentMode}
                    </span>
                </g:elseif>
                <g:elseif test="${record.state.toString().toLowerCase() == 'cancelled'}">
                    <span>
                        <g:message code="payment.searchResult.cancelledBy"/> ${record.paymentMode}
                    </span>
                </g:elseif>
                <g:else>
                    <span><g:message code="payment.searchResult.paidBy" /> ${record.paymentMode}</span>
                </g:else>
            </p>
            <g:if test="${record.isExternalItem() || record.state.toString().toLowerCase() != 'topay'}">
              <p>
                <a href="${createLink(action:'paymentDetails', id:record.id, 'state': state)}">
                  <g:message code="payment.action.seePaymentDetails" />
                </a>
              </p>
            </g:if>
        </li>
      </g:each>
    </ul>
    <g:if test="${paginate}">
      <p class="paginator">
        <g:paginate action="history" total="${payments.count}" max="${maxRows}" next="&gt;" prev="&lt;" params="${['ps':pageState]}"  />
      </p>
    </g:if>
</div>
</g:if>
<g:else>
  <div class="information-box">
  	<g:message code="${noPaymentsMsg}" />
  </div>
</g:else>
