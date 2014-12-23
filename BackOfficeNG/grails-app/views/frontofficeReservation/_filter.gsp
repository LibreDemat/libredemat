<div class="narrow-box">
  <h3>
    <g:message code="header.filterBy" />
  </h3>
  <div class="body">
    <form action="" method="get">
      <label class="title"><g:message code="property.date" /> :</label>
      <select name="month" class="month-filter">
        <g:each in="${(1 .. 12)}">
          <option value="${it}" ${(month == null && currentMonth == it) || month.equals(it.toString()) ? 'selected="selected"':''}>
            ${monthsNames[it]}
          </option>
        </g:each>
      </select>
      
      <g:select name="year" class="year-filter" from="${(currentYear -3) .. (currentYear + 2)}" 
      	value="${(year == null)?(year=currentYear):(year)}"/>
      
     
      <input id="cYear" type="hidden" name="cyear" value="${(year == null)?(year=currentYear):(year)}" />
      <input id="cMonth" type="hidden" name="cmonth" value="${(month == null)?(currentMonth):(month)}" />
      <input id="cActivityCode" type="hidden" name="activityCode" value="${activityCode}" />
      <input id="cChildId" type="hidden" name="childId" value="${childId}" />
      <input type="image" src="../../images/icons/filtrer.png" value="${message(code:'action.filter')}"/>
    </form>
  </div>
</div>
