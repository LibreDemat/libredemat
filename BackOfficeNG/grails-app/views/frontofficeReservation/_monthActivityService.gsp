<g:if test="${isActiveGlobalReservation}" >
<div class=hd>
Réservation jusqu'au ${endDateRegistration}
</div>
<form id="allReservationForm" action="${createLink(action:'getAllReservation')}" method="POST">
<div class=bd>

<!--  date  -->
<g:if test="${weeklySchedule.monday}" > 
<input id="ch_lu" name="ch_lu" type="checkbox" value="Lundi" >Lundi</input>
</g:if>
<g:if test="${weeklySchedule.tuesday}" > 
<input id="ch_ma" name="ch_ma" type="checkbox" value="Mardi">Mardi</input>
</g:if>
<g:if test="${weeklySchedule.wednesday}" > 
<input id="ch_me" name="ch_me" type="checkbox" value="Mercredi">Mercredi</input>
</g:if>
<g:if test="${weeklySchedule.thursday}" > 
<input id="ch_je" name="ch_je" type="checkbox" value="Jeudi">Jeudi</input>
</g:if>
<g:if test="${weeklySchedule.friday}" > 
<input id="ch_ve" name="ch_ve" type="checkbox" value="Vendredi">Vendredi</input>
</g:if>
<br/>
<g:if test="${weeklySchedule.saturday}" > 
<input id="ch_sa" name="ch_sa" type="checkbox" value="Samedi">Samedi</input>
</g:if>
<g:if test="${weeklySchedule.sunday}" > monthActivityService
<input id="ch_di" name="ch_di" type="checkbox" value="Dimanche">Dimanche</input>
</g:if>
<br/>

<input id="payment"  name="payment" type="hidden" value="${payment}" />
<input id="childId"  name="childId" type="hidden" value="${childId}" />
<input id="activityCode"  name="activityCode" type="hidden" value="${activityCode}" />
<input id="activityServiceCode"  name="activityServiceCode" type="hidden" value="${activityServiceCode}" />

</div>
<div class=ft>
<input id="allReservation" class="default" type="submit" />
</div>

</form>
</g:if>

<div class=hd>
Réservation au mois
</div>
<form id="monthForm" action="${createLink(action:'getItemInStockMonth')}" method="POST">
<div class=bd>


 <g:if test="${isActiveCocheAllInReservationPlaningMonth}" ><a id="linkAll" class="linkAll unassociateCheckBox" >Tout cocher / tout décocher</a> </g:if>
 <div class="icon-legend">
          <span class="legend-reservation-valid">&nbsp;</span><g:message code="reservation.validated" />
          <span class="legend-reservation-en-cours">&nbsp;</span><g:message code="reservation.inprogress" />
          <span class="legend-reservation-report-en-cours">&nbsp;</span><g:message code="report.inprogress" />
        </div>
   <g:miniPlanning  month="${month}" year="${year}" data="${monthActivities}" deadLine="${deadLine}" 
                  childId="${childId}" activityCode="${activityCode}" incompatibility="${incompatibility}" />

</div>

</form>
<div class=ft>
<input id="monthReservation" type="submit" />
</div>