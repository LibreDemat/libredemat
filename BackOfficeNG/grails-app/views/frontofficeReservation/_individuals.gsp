<g:if test="${reservations?.keySet()}">
  <g:each in="${reservations}" var="${child}">
    <div class="main-box data-detail ${child.value.ch instanceof org.libredemat.business.users.Adult ? 'adult' : 'child'}">
      <h2><g:message code="activity.header.briefReviewOf"/>&nbsp;${child.value.ch.firstName}&nbsp; pour ${(month==null)?(monthsNames[currentMonth.toInteger()]):(monthsNames[month.toInteger()])}</h2>
      
      		<g:if test="${child.value.childInformationSheetFilled == false}">				
				<div class="yui-g">
      				<g:message code="homeFolder.individual.header.informationSheetFilled1"/>
      				<span class="help">${child.value.ch.firstName}</span>
      				<g:message code="homeFolder.individual.header.informationSheetFilled2"/>
      				<g:message code="homeFolder.individual.header.informationSheetFilledSansPrenom"/>
      				<br/>
		            <a href="${createLink(controller:'frontofficeHomeFolder', action:'child', id:child.value.ch.id , params:['childId':child.value.ch.id], 'fragment':'informationSheet')}">
		                ${message(code:'homeFolder.individual.header.informationSheet')}
		            </a>      				
      			</div>				
			</g:if>

      <g:each in="${child.value.activities}" var="activities">
       	<div class="yui-g">       	
			<g:if test="${activities.value.label != null}">				
				<h3 class="activityLabel">${activities.value.label}</h3>
			</g:if>
			<g:else>
				<h3><g:translateRequestTypeLabel label="${activities.value.code}"/></h3>
			</g:else>
			 <div class="details-panel">
			 	<g:if test="${child.value.childInformationSheetFilled}">
	          	    <a href="${createLink(action: 'details', params:['activityCode':activities.value.code.encodeAsURL(),
						  'childId':child.value.ch.id, 'month': (month) ? (month) : (currentMonth), 
						  'year':(year) ? (year) : (currentYear)])}">
		              <g:message code="action.seeDetailsReservation"/>
		            </a>
	            </g:if>
             </div>
			 <div class="yui-u first">
			<g:each in="${activities.value.activityServices}" status="inxo" var="activityServices">
			<g:if test="${inxo % 2 == 0}">
			<div class="yui-g" style="float:none">
			  <h4><g:if test="${activityServices.value.type != 'single'}">${activityServices.value.groupLabel} :</g:if></h4>
		        <div class="yui-u first">
		          <ul>
				  <g:each in="${activityServices.value.asd}" status="inx" var="asd">
				  <g:if test="${inx % 2 == 0}">
				  	<g:each in="${asd.value}" var="asf">
				  		<li>${asf.value.label} : ${asf.value.counting}</li>
				  	</g:each>				  	
				  </g:if>				  
				  </g:each>
				   </ul>
			    </div>
			    <div class="yui-u">
			    <ul>
				    <g:each in="${activityServices.value.asd}" status="inx" var="asd">
				    	<g:if test="${inx % 2 != 0}">				    
					 <g:each in="${asd.value}" var="asf">
				  	<li>${asf.value.label} : ${asf.value.counting}</li>
				  		
				  	</g:each>
					  </g:if>
					  </g:each>
				 </ul> 
			    </div>			   
			  </div>
			  </g:if>				
	        </g:each>  
	        </div>
	        <div class="yui-u">
			<g:each in="${activities.value.activityServices}" status="inxo" var="activityServices">
			<g:if test="${inxo % 2 != 0}">
			<div class="yui-g">
			  <h4><g:if test="${activityServices.value.type != 'single'}">${activityServices.value.groupLabel} :</g:if></h4>
		        <div class="yui-u first">
		        <ul>
				  <g:each in="${activityServices.value.asd}" status="inx" var="asd">
				  <g:if test="${inx % 2 == 0}">
				  	<g:each in="${asd.value}" var="asf">
				  		<li>${asf.value.label} : ${asf.value.counting}</li>
				  	</g:each>
				  </g:if>
				  </g:each>
				   </ul>
			    </div>
			    <div class="yui-u">
			    <ul>
				    <g:each in="${activityServices.value.asd}" status="inx" var="asd">
				    	<g:if test="${inx % 2 != 0}">				    
					 <g:each in="${asd.value}" var="asf">
				  		<li>${asf.value.label} : ${asf.value.counting}</li>				  		
				  	</g:each>
					  </g:if>
					  </g:each>
				 </ul> 
			    </div>			   
			  </div>
			  </g:if>				
	        </g:each>  
	        </div>        	
	        </div>  
	      </g:each>
     </div>
  </g:each>
</g:if>
<g:else>
  <div class="information-box">
  	<g:message code="reservation.message.noActivities" />
  </div>
</g:else>
