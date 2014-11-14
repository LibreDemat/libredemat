<div class="icon-legend">
   <span class="legend-reservation-valid">&nbsp;</span><g:message code="reservation.validated" />
   <span class="legend-reservation-en-cours">&nbsp;</span><g:message code="reservation.inprogress" />
   <span class="legend-reservation-report-en-cours">&nbsp;</span><g:message code="report.inprogress" />
</div>
<div class="listContainer">
<ul>
<g:each in="${items}" var="item">
  <li class="cart">
  
  <g:detailFormat day="${item.day}" type="${item.dayType}" color="${item.color}" 
                    childId="${item.childId}" activityCode="${item.activityCode}" 
                    activityServiceCode="${item.activityServiceCode}" 
                    labelService="${item.activityServiceLabel}"
                    homeFolderId="${item.homeFolderId}" id="${item.id}"
                    name ="${mapFirstName}" />                    
  </li>
</g:each>
</ul>
</div>