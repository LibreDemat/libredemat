<div id="parkingPermitTemporaryRelocation" class="mainbox mainbox-yellow">
    <h2><g:message code="requestType.configuration.pptrr.prices"/></h2>

    <div class="error" id="pptrrPricesFormError"></div>
    <form class="edit" method="post" id="pptrrPricesForm" action="${createLink(action: 'savePptrrPrices')}">
        <p class="field">
            <label for="authorizationWithoutPrestation" class="required">
                <g:message code="requestType.property.pptrr.authorizationWithoutPrestation"/> :
            </label>
            <input type="text" class="required validate-numeric" name="authorizationWithoutPrestation" id="authorizationWithoutPrestation"
                value="${authorizationWithoutPrestation?.getAsString()}" />
        </p>

        <p class="field">
            <label for="relocationWithPrestation" class="required">
                <g:message code="requestType.property.pptrr.relocationWithPrestation"/> :
            </label>
            <input type="text" class="required validate-numeric" name="relocationWithPrestation" id="relocationWithPrestation"
                value="${relocationWithPrestation?.getAsString()}" />
        </p>

        <div class="form-button">
            <input type="button" id="savePptrrPrices"
                   name="savePptrrPrices" value="${message(code: 'action.save')}"/>
            <input type="hidden" name="id" value="${requestType?.id}"/>
        </div>
    </form>

    <h2><g:message code="requestType.configuration.pptrr.dates"/></h2>

    <div class="error" id="pptrrDatesFormError"></div>
    <form class="edit" method="post" id="pptrrDatesForm" action="${createLink(action: 'savePptrrDates')}">
        <p class="field">
            <label for="minDaysBeforeRelocation">
                <g:message code="requestType.property.pptrr.minDaysBeforeRelocation"/> :
            </label>
            <input type="text" class="validate-number" name="minDaysBeforeRelocation" id="minDaysBeforeRelocation"
                   value="${minDaysBeforeRelocation?.getAsString()}" />
        </p>

        <div class="form-button">
            <input type="button" id="savePptrrDates"
                   name="savePptrrDates" value="${message(code: 'action.save')}"/>
            <input type="hidden" name="id" value="${requestType?.id}"/>
        </div>
    </form>
</div>
