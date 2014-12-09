<form id="payForm" action="${createLink(action:'getPayReservation')}" method="POST">
	<div class='list'>
		<% def totalResa = 0 %>
		<ul>
			<g:each in="${children}" var="line">
				<g:each in="${line.value}" var="activity">
					<g:each in="${activity.value}" var="service">
						<g:each in="${service.value}" var="day">
							<g:if test="${day.key != 'label'}">
								<g:if test="${!day.value.errorCode}">
									<li>
										${day.value.dateFormat} ${service.value.label} (<g:if
											test="${day.value.type.toLowerCase()=='reportencours'}"
										>d&eacute;command&eacute;</g:if>
										<g:else>r&eacute;servation</g:else>) pour ${day.value.firstName} 
										
										<g:if test="${day.value.dayAmount != 0}" >
											( <g:if
												test="${day.value.type.toLowerCase()=='reportencours'}"
											>-</g:if> <g:formatNumber number="${new BigDecimal(day.value.dayAmount,2)}"
												type="currency" currencyCode="EUR"
											/>) 
										</g:if>
										
										<input type="hidden" id="line"
										value="${day.value.childId}-${activity.key}-${service.key}-${day.value.dayAmount}-${day.key.replaceAll("-","/")}-${service.value.label}-${day.value.type.toLowerCase()=='reportencours'}"
										name="line"
									/> <%
								if(day.value.type.toLowerCase()=='reportencours'){
									totalResa = totalResa -  day.value.dayAmount.toInteger()
                                } else {
									totalResa = totalResa +  day.value.dayAmount.toInteger()
                                }
                                %>
									</li>
								</g:if>
							</g:if>
						</g:each>
					</g:each>
				</g:each>
			</g:each>
		</ul>
		<br />
		<br />
		<% def errorCount = 0 %>
		<g:each in="${children}" var="line">
			<g:each in="${line.value}" var="activity">
				<g:each in="${activity.value}" var="service">
					<g:each in="${service.value}" var="day">
						<g:if test="${day.key != 'label'}">
							<g:if test="${day.value.errorCode}">
								<% errorCount++ %>
							</g:if>
						</g:if>
					</g:each>
				</g:each>
			</g:each>
		</g:each>
		<g:if test="${errorCount > 0}">
            Les réservations suivantes ne sont pas validées : 
          </g:if>
		<ul>
			<g:each in="${children}" var="line">
				<g:each in="${line.value}" var="activity">
					<g:each in="${activity.value}" var="service">
						<g:each in="${service.value}" var="day">
							<g:if test="${day.key != 'label'}">
								<g:if test="${day.value.errorCode}">
									<li class="refuse"
										id="${day.value.childId}-${activity.key.encodeAsURL()}-${service.key.encodeAsURL()}-${day.key.replaceAll("-","/")}"
									>
										${day.value.dateFormat} ${service.value.label} pour ${day.value.firstName} en raison :
										${day.value.errorLabel}
									</li>
								</g:if>
							</g:if>
						</g:each>
					</g:each>
				</g:each>
			</g:each>
		</ul>
		<input type="hidden" name="amountNegative" value="${amountNegative}" />
	</div>
	<br />
	<h4 style='text-align: right;'>
		Solde du compte avant paiement :
		<g:formatNumber number="${new BigDecimal(amountNegative,2)}" type="currency"
			currencyCode="EUR"
		/>
	</h4>
	<input type="hidden" name="amountOrigin" value="${amount}" id="amountOrigin" /> <br />
	<br />
	<h4 style='text-align: right;'>
		Total des r&eacute;servations et reports :
		<g:formatNumber number="${new BigDecimal(totalResa,2)}" type="currency"
			currencyCode="EUR"
		/>
	</h4>
	<h4 style='text-align: right;'>
		Total à payer :
		<g:if test="${amount <= 0}">
			<g:formatNumber number="${new BigDecimal(0,2)}" type="currency" currencyCode="EUR" />
			<input type="hidden" name="amount" value="0" />
		</g:if>
		<g:else>
			<g:formatNumber number="${new BigDecimal(amount,2)}" type="currency" currencyCode="EUR" />
			<input type="hidden" name="amount" value="${amount}" />
		</g:else>
	</h4>
	<input type="hidden" name="homeFolderId" value="${homeFolderId}" /> <br />
</form>