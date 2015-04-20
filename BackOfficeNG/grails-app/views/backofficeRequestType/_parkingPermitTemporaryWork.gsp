<div id="parkingPermitTemporaryWork" class="mainbox mainbox-yellow">
    <h2><g:message code="requestType.configuration.pptwr.prices"/></h2>

    <div class="error" id="pptwrPricesFormError"></div>
    <form class="edit" method="post" id="pptwrPricesForm" action="${createLink(action: 'savePptwrPrices')}">
        <p class="field">
            <label for="scaffoldingPrice" class="required">
                <g:message code="requestType.property.pptwr.scaffoldingPrice"/> :
            </label>
            <input type="text" class="required validate-numeric" name="scaffoldingPrice" id="scaffoldingPrice"
                value="${scaffoldingPrice?.getAsString()}" />
        </p>

        <p class="field">
            <label for="floorOccupationPrice" class="required">
                <g:message code="requestType.property.pptwr.floorOccupationPrice"/> :
            </label>
            <input type="text" class="required validate-numeric" name="floorOccupationPrice" id="floorOccupationPrice"
                value="${floorOccupationPrice?.getAsString()}" />
        </p>

        <p class="field">
            <label for="fixedChargePrice" class="required">
                <g:message code="requestType.property.pptwr.fixedChargePrice"/> :
            </label>
            <input type="text" class="required validate-numeric" name="fixedChargePrice" id="fixedChargePrice"
                   value="${fixedChargePrice?.getAsString()}" />
        </p>

        <p class="field">
            <label for="exceedingPrice" class="required">
                <g:message code="requestType.property.pptwr.exceedingPrice"/> :
            </label>
            <input type="text" class="required validate-numeric" name="exceedingPrice" id="exceedingPrice"
                   value="${exceedingPrice?.getAsString()}" />
        </p>

        <div class="form-button">
            <input type="button" id="savePptwrPrices"
                   name="savePptwrPrices" value="${message(code: 'action.save')}"/>
            <input type="hidden" name="id" value="${requestType?.id}"/>
        </div>
    </form>

    <h2><g:message code="requestType.configuration.pptwr.dates"/></h2>

    <div class="error" id="pptwrDatesFormError"></div>
    <form class="edit" method="post" id="pptwrDatesForm" action="${createLink(action: 'savePptwrDates')}">
        <p class="field">
            <label for="minDaysBeforeScaffolding">
                <g:message code="requestType.property.pptwr.minDaysBeforeScaffolding"/> :
            </label>
            <input type="text" class="validate-number" name="minDaysBeforeScaffolding" id="minDaysBeforeScaffolding"
                   value="${minDaysBeforeScaffolding?.getAsString()}" />
        </p>

        <p class="field">
            <label for="minDaysBeforeFloorOccupation">
                <g:message code="requestType.property.pptwr.minDaysBeforeFloorOccupation"/> :
            </label>
            <input type="text" class="validate-number" name="minDaysBeforeFloorOccupation" id="minDaysBeforeFloorOccupation"
                   value="${minDaysBeforeFloorOccupation?.getAsString()}" />
        </p>

        <div class="form-button">
            <input type="button" id="savePptwrDates"
                   name="savePptwrDates" value="${message(code: 'action.save')}"/>
            <input type="hidden" name="id" value="${requestType?.id}"/>
        </div>
    </form>
</div>
