<form id="dayForm" action="${createLink(action:'getItemInStock')}" method="POST">
<ul>
	<g:each in="${dayActivities}" var="activity">
		<li class="day-box">
		  <span class="legend-label-box${activity.value.association.get(1)}" id="${activity.key}-${activity.value.typeOfTheDay}" 
		        style="background-color: #${activity.value.color}">            
		  </span>
      ${activity.value.label}
		  <g:if test="${activity.value.reservation == 'true'}">
			  <a id="${activity.value.association.get(0)}Item-${activity.key}-${activity.value.date}-${activity.value.incompatible}" class="link ${activity.value.association.get(0)}">
				<span><g:message code="category.action.${activity.value.association.get(0)}" /></span>
			  </a>
			  <input type="hidden" id="${activity.key}" name="data" 
			         value="${childId}-${activityCode}-${activity.key}-${activity.value.date}-${activity.value.typeOfTheDay}-${activity.value.color}-${activity.value.label}" class="data" />
		     </li>
	     </g:if>
	</g:each>
</ul>
</form>
