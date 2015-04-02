


  
    <label for="requester" class="required"><g:message code="request.property.requester.label" /> *  <span><g:message code="request.property.requester.help" /></span></label>
            <label>${requester.firstName} ${requester.lastName}</label>
            <input type="hidden" value="${requester.id}" id="requesterId" name="requesterId"
              <g:if test="${!rqt.requesterAddress}" > class='autofill-requesterFilling-trigger' </g:if> />
            <br />
            

  

  
    <label class="required"><g:message code="pptrr.property.isCompany.label" /> *  <span><g:message code="pptrr.property.isCompany.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['relocation'].invalidFields.contains('isCompany') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="isCompany_${it ? 'yes' : 'no'}" class="required condition-isCompany-trigger  validate-one-required boolean" title="" value="${it}" name="isCompany" ${it == rqt.isCompany ? 'checked="checked"': ''} />
                <label for="isCompany_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    <fieldset class="required condition-isCompany-filled">
    <legend><g:message code="pptrr.property.companyInformation.label" /></legend>
    
      <label for="siretNumber" class="required condition-isCompany-filled"><g:message code="pptrr.property.siretNumber.label" /> *  <span><g:message code="pptrr.property.siretNumber.help" /></span></label>
            <input  type="text" id="siretNumber" name="siretNumber" value="${rqt.siretNumber?.toString()}" 
                    class="required condition-isCompany-filled  validate-regex ${rqt.stepStates['relocation'].invalidFields.contains('siretNumber') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.siretNumber.validationError" />" regex="^[0-9]{14}$" maxlength="14" />
            

    
      <label for="apeCode" class="required condition-isCompany-filled"><g:message code="pptrr.property.apeCode.label" /> *  <span><g:message code="pptrr.property.apeCode.help" /></span></label>
            <input  type="text" id="apeCode" name="apeCode" value="${rqt.apeCode?.toString()}" 
                    class="required condition-isCompany-filled  validate-regex ${rqt.stepStates['relocation'].invalidFields.contains('apeCode') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.apeCode.validationError" />" regex="^[0-9]{4}[a-zA-Z]{1}$" maxlength="5" />
            

    
    </fieldset>
  

  
    <label class="required"><g:message code="pptrr.property.desiredService.label" /> *  <span><g:message code="pptrr.property.desiredService.help" /></span></label>
            <g:set var="desiredServiceIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'desiredService', 'i18nPrefixCode':'pptrr.property.desiredService', 'htmlClass':'required  ', 
                              'lrEntries': lrTypes.desiredService.entries, 'depth':0]" />
            

  

  
    <label class="required"><g:message code="pptrr.property.requesterAddress.label" /> *  <span><g:message code="pptrr.property.requesterAddress.help" /></span></label>
            <div id="requesterAddress" class="address required autofill-requesterFilling-listener-Address ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress') ? 'validation-failed' : ''}">
            <label for="requesterAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.additionalDeliveryInformation}" maxlength="38" id="requesterAddress.additionalDeliveryInformation" name="requesterAddress.additionalDeliveryInformation" />  
            <label for="requesterAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.additionalGeographicalInformation}" maxlength="38" id="requesterAddress.additionalGeographicalInformation" name="requesterAddress.additionalGeographicalInformation" />
            <label for="requesterAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="requesterAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input  type="text" class="line1 validate-streetNumber ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.streetNumber}" size="5" maxlength="5" id="requesterAddress_streetNumber" name="requesterAddress.streetNumber" />
            <input  type="text" class="line2 required validate-streetName ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.streetName}" maxlength="32" id="requesterAddress_streetName" name="requesterAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <input  type="hidden" value="${rqt.requesterAddress?.streetMatriculation}" id="requesterAddress_streetMatriculation" name="requesterAddress.streetMatriculation" />
            <input  type="hidden" value="${rqt.requesterAddress?.streetRivoliCode}" id="requesterAddress_streetRivoliCode" name="requesterAddress.streetRivoliCode" />
            <label for="requesterAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.placeNameOrService}" maxlength="38" id="requesterAddress.placeNameOrService" name="requesterAddress.placeNameOrService" />
            <label for="requesterAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="requesterAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input  type="text" class="line1 required validate-postalCode ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.postalCode}" size="5" maxlength="5" id="requesterAddress_postalCode" name="requesterAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input  type="text" class="line2 required validate-city ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.city') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.city}" maxlength="32" id="requesterAddress_city" name="requesterAddress.city" title="<g:message code="address.property.city.validationError" />" />
            <input  type="hidden" value="${rqt.requesterAddress?.cityInseeCode}" id="requesterAddress_cityInseeCode" name="requesterAddress.cityInseeCode" />
            <label for="requesterAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['relocation'].invalidFields.contains('requesterAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.requesterAddress?.countryName}" maxlength="38" id="requesterAddress.countryName" name="requesterAddress.countryName" />
            </div>
            

  

  
    <label for="periodeStart" class="required"><g:message code="pptrr.property.periodeStart.label" /> *  <span><g:message code="pptrr.property.periodeStart.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="periodeStart" name="periodeStart" class="required validate-calendar"
                <g:if test="${rqt.periodeStart}">value="<g:formatDate formatName='format.date' date='${rqt.periodeStart}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="periodeStartShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="false">disabledWith_null</g:if> <g:if test="true">mindayouvre_10</g:if> <g:if test="false">notBeforeDate_null</g:if>"
                ><span id="periodeStartCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

  

  
    <label for="periodeEnd" class="required"><g:message code="pptrr.property.periodeEnd.label" /> *  <span><g:message code="pptrr.property.periodeEnd.help" /></span></label>
            <table><tr>
            <td>
                <input type="text" id="periodeEnd" name="periodeEnd" class="required validate-calendar"
                <g:if test="${rqt.periodeEnd}">value="<g:formatDate formatName='format.date' date='${rqt.periodeEnd}'/>"</g:if>>
            </td>
            <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
                <img id="periodeEndShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}"
                    class="calendar <g:if test="true">disabledWith_periodeStart</g:if> <g:if test="false">mindayouvre_null</g:if> <g:if test="true">notBeforeDate_periodeStart</g:if>"
                ><span id="periodeEndCalContainer" class="yui-cal yui-calcontainer"></span>
            </td>
            </tr></table>
            

  

  
    <label for="heureStart" class="required"><g:message code="pptrr.property.heureStart.label" /> *  <span><g:message code="pptrr.property.heureStart.help" /></span></label>
            <select id="heureStart" name="heureStart" class="required  validate-not-first ${rqt.stepStates['relocation'].invalidFields.contains('heureStart') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.heureStart.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['K0','K1','K2','K3','K4','K5','K6','K7','K8','K9','K10','K11','K12','K13','K14','K15','K16','K17','K18','K19','K20','K21','K22','K23']}">
                <option value="${it}" ${it == rqt.heureStart?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="pptrr.property.heureStart" /></option>
              </g:each>
            </select>
            

  

  
    <label for="heureEnd" class="required"><g:message code="pptrr.property.heureEnd.label" /> *  <span><g:message code="pptrr.property.heureEnd.help" /></span></label>
            <select id="heureEnd" name="heureEnd" class="required  validate-not-first ${rqt.stepStates['relocation'].invalidFields.contains('heureEnd') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.heureEnd.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['K0','K1','K2','K3','K4','K5','K6','K7','K8','K9','K10','K11','K12','K13','K14','K15','K16','K17','K18','K19','K20','K21','K22','K23']}">
                <option value="${it}" ${it == rqt.heureEnd?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="pptrr.property.heureEnd" /></option>
              </g:each>
            </select>
            

  

  
    <fieldset class="required">
    <legend><g:message code="pptrr.property.equipmentUsed.label" /></legend>
    
      <label for="vehicleType" class="required"><g:message code="pptrr.property.vehicleType.label" /> *  <span><g:message code="pptrr.property.vehicleType.help" /></span></label>
            <input  type="text" id="vehicleType" name="vehicleType" value="${rqt.vehicleType?.toString()}" 
                    class="required  validate-string ${rqt.stepStates['relocation'].invalidFields.contains('vehicleType') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.vehicleType.validationError" />"   />
            

    
      <label for="longeur" class="required"><g:message code="pptrr.property.longeur.label" /> *  <span><g:message code="pptrr.property.longeur.help" /></span></label>
            <input  type="text" id="longeur" name="longeur" value="${rqt.longeur?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['relocation'].invalidFields.contains('longeur') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.longeur.validationError" />"   />
            

    
      <label for="immatriculation" class=""><g:message code="pptrr.property.immatriculation.label" />   <span><g:message code="pptrr.property.immatriculation.help" /></span></label>
            <input  type="text" id="immatriculation" name="immatriculation" value="${rqt.immatriculation?.toString()}" 
                    class="  validate-regex ${rqt.stepStates['relocation'].invalidFields.contains('immatriculation') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.immatriculation.validationError" />" regex="^[\w\W]{0,255}$" maxlength="255" />
            

    
      <label class="required"><g:message code="pptrr.property.furnitureLifting.label" /> *  <span><g:message code="pptrr.property.furnitureLifting.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['relocation'].invalidFields.contains('furnitureLifting') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="furnitureLifting_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="furnitureLifting" ${it == rqt.furnitureLifting ? 'checked="checked"': ''} />
                <label for="furnitureLifting_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      <label for="other" class=""><g:message code="pptrr.property.other.label" />   <span><g:message code="pptrr.property.other.help" /></span></label>
            <input  type="text" id="other" name="other" value="${rqt.other?.toString()}" 
                    class="  validate-string ${rqt.stepStates['relocation'].invalidFields.contains('other') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.other.validationError" />"   />
            

    
    </fieldset>
  

  
    <label for="observations" class=""><g:message code="pptrr.property.observations.label" />   <span><g:message code="pptrr.property.observations.help" /></span></label>
            <textarea id="observations" name="observations" class="  validate-textarea ${rqt.stepStates['relocation'].invalidFields.contains('observations') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.observations.validationError" />" rows="3" cols=""  >${rqt.observations}</textarea>
            

  

