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
									R&eacute;servation le ${day.value.dateFormat} pour l'activit&eacute; ${service.value.label} de ${day.value.firstName}
									<% totalResa++ %> 
								</li>
							</g:if>
						</g:if>
					</g:each>
				</g:each>
			</g:each>
		</g:each>
	</ul>
	<g:if test="${totalResa == 0}">
           Aucune nouvelle réservation n'a &eacute;t&eacute; effectu&eacute;e.
    </g:if>
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
</div>