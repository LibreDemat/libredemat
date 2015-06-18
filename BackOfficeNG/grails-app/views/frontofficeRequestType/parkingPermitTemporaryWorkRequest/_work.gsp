


  
    
    <label for="requester" class="required"><g:message code="request.property.requester.label" /> *  <span><g:message code="request.property.requester.help" /></span></label>
            <label>${requester.firstName} ${requester.lastName}</label>
            <br />
            

  

  
    
    <label class="required"><g:message code="pptwr.property.isCompany.label" /> *  <span><g:message code="pptwr.property.isCompany.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['work'].invalidFields.contains('isCompany') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="isCompany_${it ? 'yes' : 'no'}" class="required condition-isCompany-trigger  validate-one-required boolean" title="" value="${it}" name="isCompany" ${it == rqt.isCompany ? 'checked="checked"': ''} />
                <label for="isCompany_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    <fieldset class="required condition-isCompany-filled" id="CompanyInformation">
    <legend><g:message code="pptwr.property.companyInformation.label" /></legend>
    
      
      <label for="siretNumber" class="required condition-isCompany-filled"><g:message code="pptwr.property.siretNumber.label" /> *  <span><g:message code="pptwr.property.siretNumber.help" /></span></label>
            <input  type="text" id="siretNumber"
                   name="siretNumber"
                   value="${rqt.siretNumber?.toString()}"
                   class="required condition-isCompany-filled  validate-regex ${rqt.stepStates['work'].invalidFields.contains('siretNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="pptwr.property.siretNumber.validationError" />" regex="^[0-9]{14}$" maxlength="14" />
            

    
      
      <label for="apeCode" class="required condition-isCompany-filled"><g:message code="pptwr.property.apeCode.label" /> *  <span><g:message code="pptwr.property.apeCode.help" /></span></label>
            <input  type="text" id="apeCode"
                   name="apeCode"
                   value="${rqt.apeCode?.toString()}"
                   class="required condition-isCompany-filled  validate-regex ${rqt.stepStates['work'].invalidFields.contains('apeCode') ? 'validation-failed' : ''}"
                   title="<g:message code="pptwr.property.apeCode.validationError" />" regex="^[0-9]{4}[a-zA-Z]{1}$" maxlength="5" />
            

    
    </fieldset>
  

  
    
    <label class="required"><g:message code="pptwr.property.desiredService.label" /> *  <span><g:message code="pptwr.property.desiredService.help" /></span></label>
            <g:set var="desiredServiceIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'desiredService', 'i18nPrefixCode':'pptwr.property.desiredService', 'htmlClass':'required condition-desiredService-trigger  ', 
                              'lrEntries': lrTypes.desiredService.entries, 'depth':0]" />
            

  

  
    <fieldset class="required condition-desiredService-filled" id="ParkingPermitForWorkInformation">
    <legend><g:message code="pptwr.property.parkingPermitForWorkInformation.label" /></legend>
    
      
      <label for="siteAddress" class="required"><g:message code="pptwr.property.siteAddress.label" /> *  <span><g:message code="pptwr.property.siteAddress.help" /></span></label>
            <textarea id="siteAddress" name="siteAddress" class="required  validate-textarea ${rqt.stepStates['work'].invalidFields.contains('siteAddress') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.siteAddress.validationError" />" rows="3" cols=""  >${rqt.siteAddress}</textarea>
            

    
      
      <label for="workNature" class="required"><g:message code="pptwr.property.workNature.label" /> *  <span><g:message code="pptwr.property.workNature.help" /></span></label>
            <textarea id="workNature" name="workNature" class="required  validate-textarea ${rqt.stepStates['work'].invalidFields.contains('workNature') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.workNature.validationError" />" rows="3" cols=""  >${rqt.workNature}</textarea>
            

    
      
      <label class="required"><g:message code="pptwr.property.workOnBuilding.label" /> *  <span><g:message code="pptwr.property.workOnBuilding.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['work'].invalidFields.contains('workOnBuilding') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="workOnBuilding_${it ? 'yes' : 'no'}" class="required condition-workOnBuilding-trigger  validate-one-required boolean" title="" value="${it}" name="workOnBuilding" ${it == rqt.workOnBuilding ? 'checked="checked"': ''} />
                <label for="workOnBuilding_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="constructLicenseNumber" class="required condition-workOnBuilding-filled"><g:message code="pptwr.property.constructLicenseNumber.label" /> *  <span><g:message code="pptwr.property.constructLicenseNumber.help" /></span></label>
            <input  type="text" id="constructLicenseNumber"
                   name="constructLicenseNumber"
                   value="${rqt.constructLicenseNumber?.toString()}"
                   class="required condition-workOnBuilding-filled  validate-string ${rqt.stepStates['work'].invalidFields.contains('constructLicenseNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="pptwr.property.constructLicenseNumber.validationError" />"   />
            

    
      
      <label for="usedVehicles" class=""><g:message code="pptwr.property.usedVehicles.label" />   <span><g:message code="pptwr.property.usedVehicles.help" /></span></label>
            <input  type="text" id="usedVehicles"
                   name="usedVehicles"
                   value="${rqt.usedVehicles?.toString()}"
                   class="  validate-string ${rqt.stepStates['work'].invalidFields.contains('usedVehicles') ? 'validation-failed' : ''}"
                   title="<g:message code="pptwr.property.usedVehicles.validationError" />"   />
            

    
    </fieldset>
  

  
    <fieldset class="required condition-desiredService-unfilled" id="ExistingLicenseExtensionInformation">
    <legend><g:message code="pptwr.property.existingLicenseExtensionInformation.label" /></legend>
    
      
      <label for="referenceNumber" class="required"><g:message code="pptwr.property.referenceNumber.label" /> *  <span><g:message code="pptwr.property.referenceNumber.help" /></span></label>
            <input  type="text" id="referenceNumber"
                   name="referenceNumber"
                   value="${rqt.referenceNumber?.toString()}"
                   class="required  validate-string ${rqt.stepStates['work'].invalidFields.contains('referenceNumber') ? 'validation-failed' : ''}"
                   title="<g:message code="pptwr.property.referenceNumber.validationError" />"   />
            

    
    </fieldset>
  

  
    
    <label class="required"><g:message code="pptwr.property.scaffolding.label" /> *  <span><g:message code="pptwr.property.scaffolding.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['work'].invalidFields.contains('scaffolding') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="scaffolding_${it ? 'yes' : 'no'}" class="required condition-scaffolding-trigger  validate-one-required boolean" title="" value="${it}" name="scaffolding" ${it == rqt.scaffolding ? 'checked="checked"': ''} />
                <label for="scaffolding_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    <fieldset class="required condition-scaffolding-filled" id="ScaffoldingInformation">
    <legend><g:message code="pptwr.property.scaffoldingInformation.label" /></legend>
    
      
      <label for="scaffoldingLength" class="required"><g:message code="pptwr.property.scaffoldingLength.label" /> *  <span><g:message code="pptwr.property.scaffoldingLength.help" /></span></label>
            <input type="text" id="scaffoldingLength" name="scaffoldingLength" value="${formatNumber(number: rqt.scaffoldingLength, type: 'number')}"
                    class="required  validate-double ${rqt.stepStates['work'].invalidFields.contains('scaffoldingLength') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.scaffoldingLength.validationError" />"   />
            

    
      
      <label for="scaffoldingStartDate" class="required"><g:message code="pptwr.property.scaffoldingStartDate.label" /> *  <span><g:message code="pptwr.property.scaffoldingStartDate.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="scaffoldingStartDate" name="scaffoldingStartDate" class="required validate-calendar"
                <g:if test="${rqt.scaffoldingStartDate}">value="<g:formatDate formatName='format.date' date='${rqt.scaffoldingStartDate}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="scaffoldingStartDateShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="false">disabledWith_null</g:if> <g:if test="false">mindayouvre_null</g:if> <g:if test="false">notBeforeDate_null</g:if>"
                ><span id="scaffoldingStartDateCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

    
      
      <label for="scaffoldingEndDate" class="required"><g:message code="pptwr.property.scaffoldingEndDate.label" /> *  <span><g:message code="pptwr.property.scaffoldingEndDate.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="scaffoldingEndDate" name="scaffoldingEndDate" class="required validate-calendar"
                <g:if test="${rqt.scaffoldingEndDate}">value="<g:formatDate formatName='format.date' date='${rqt.scaffoldingEndDate}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="scaffoldingEndDateShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="true">disabledWith_scaffoldingStartDate</g:if> <g:if test="false">mindayouvre_null</g:if> <g:if test="true">notBeforeDate_scaffoldingStartDate</g:if>"
                ><span id="scaffoldingEndDateCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

    
    </fieldset>
  

  
    
    <label class="required"><g:message code="pptwr.property.vehicleParkingOrFloorOccupation.label" /> *  <span><g:message code="pptwr.property.vehicleParkingOrFloorOccupation.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['work'].invalidFields.contains('vehicleParkingOrFloorOccupation') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="vehicleParkingOrFloorOccupation_${it ? 'yes' : 'no'}" class="required condition-vehicleParkingOrFloorOccupation-trigger  validate-one-required boolean" title="" value="${it}" name="vehicleParkingOrFloorOccupation" ${it == rqt.vehicleParkingOrFloorOccupation ? 'checked="checked"': ''} />
                <label for="vehicleParkingOrFloorOccupation_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    <fieldset class="required condition-vehicleParkingOrFloorOccupation-filled" id="VehicleParkingOrFloorOccupationInformation">
    <legend><g:message code="pptwr.property.vehicleParkingOrFloorOccupationInformation.label" /></legend>
    
      
      <label for="occupation" class="required"><g:message code="pptwr.property.occupation.label" /> *  <span><g:message code="pptwr.property.occupation.help" /></span></label>
            <input type="text" id="occupation" name="occupation" value="${formatNumber(number: rqt.occupation, type: 'number')}"
                    class="required  validate-double ${rqt.stepStates['work'].invalidFields.contains('occupation') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.occupation.validationError" />"   />
            

    
      
      <label for="occupationStartDate" class="required"><g:message code="pptwr.property.occupationStartDate.label" /> *  <span><g:message code="pptwr.property.occupationStartDate.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="occupationStartDate" name="occupationStartDate" class="required validate-calendar"
                <g:if test="${rqt.occupationStartDate}">value="<g:formatDate formatName='format.date' date='${rqt.occupationStartDate}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="occupationStartDateShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="false">disabledWith_null</g:if> <g:if test="false">mindayouvre_null</g:if> <g:if test="false">notBeforeDate_null</g:if>"
                ><span id="occupationStartDateCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

    
      
      <label for="occupationEndDate" class="required"><g:message code="pptwr.property.occupationEndDate.label" /> *  <span><g:message code="pptwr.property.occupationEndDate.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="occupationEndDate" name="occupationEndDate" class="required validate-calendar"
                <g:if test="${rqt.occupationEndDate}">value="<g:formatDate formatName='format.date' date='${rqt.occupationEndDate}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="occupationEndDateShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="true">disabledWith_occupationStartDate</g:if> <g:if test="false">mindayouvre_null</g:if> <g:if test="true">notBeforeDate_occupationStartDate</g:if>"
                ><span id="occupationEndDateCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

    
      
      <label for="occupationOtherAddress" class=""><g:message code="pptwr.property.occupationOtherAddress.label" />   <span><g:message code="pptwr.property.occupationOtherAddress.help" /></span></label>
            <textarea id="occupationOtherAddress" name="occupationOtherAddress" class="  validate-textarea ${rqt.stepStates['work'].invalidFields.contains('occupationOtherAddress') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.occupationOtherAddress.validationError" />" rows="3" cols=""  >${rqt.occupationOtherAddress}</textarea>
            

    
    </fieldset>
  

  
    
        <div class="field-header-information" id="Observations-header-information">${message(code:'pptwr.property.observations.headerInformation')}</div>
    
    <label for="observations" class=""><g:message code="pptwr.property.observations.label" />   <span><g:message code="pptwr.property.observations.help" /></span></label>
            <textarea id="observations" name="observations" class="  validate-textarea ${rqt.stepStates['work'].invalidFields.contains('observations') ? 'validation-failed' : ''}" title="<g:message code="pptwr.property.observations.validationError" />" rows="3" cols=""  >${rqt.observations}</textarea>
            

  

