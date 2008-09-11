<div id="newRequests">
<div class="central-content-area">
		 <h2>
		 	<g:if test="${type == 'cvq.tasks.pending'}">Nouvelles demandes</g:if>
		 	<g:if test="${type == 'cvq.tasks.opened'}">Demandes en cours</g:if>
		 	<g:if test="${type == 'cvq.tasks.validated'}">Demandes valid�es</g:if>
		 </h2>
         <ul id="listContainer">
				<g:each in="${requests}" status="i" var="request" >
					<g:if test="${i < 10}">
						<li>
							<b>id:</b> ${request.id}
						 	<b>Label :</b>
						 	<g:translate label="${request.requestType.label}"/>
						    <b>Date cr�ation :</b>
						   	<g:formatDate format="dd-MM-yyyy hh:mm" date="${request.creationDate}"/>
						   	<b>Nom du demandeur:</b> 
						   	${request.requester.lastName} ${request.requester.firstName}
						   	<b>Date de derniere modification :</b>
						   	<g:formatDate format="dd-MM-yyyy hh:mm" date="${request.lastModificationDate}"/>
						    <br><br>
						</li>
					</g:if>	
	  			 </g:each>
	      </ul>
</div>

	<g:if test="${type == 'cvq.tasks.pending'}">
	<g:link  controller="request" action="search" params="[state:'Pending']">Toutes les Nouvelles demandes</g:link></g:if>
	
	<g:if test="${type == 'cvq.tasks.opened'}">
			 
		  <g:link  controller="request" action="search" params="[state:'Complete']">Toutes les demandes compl�tes </g:link><br>  
		  <g:link  controller="request" action="search" params="[state:'Uncomplete']">Toutes les demandes incompl�tes</g:link><br>  
		  <g:link  controller="request" action="search" params="[state:'Rejected']">Toutes les demandes rejeter</g:link><br> 
		  <g:link  controller="request" action="search" params="[state:'Cancelled']">Toutes les demandes annul�es</g:link><br> 
	
	</g:if>
	
	<g:if test="${type == 'cvq.tasks.validated'}">
	<g:link  controller="request" action="search" params="[state:'Validated']">Toutes les demandes valid�es</g:link></g:if>

		 <div id="dt-pag-nav"></div> 
   
<div>&nbsp;</div>
</div>