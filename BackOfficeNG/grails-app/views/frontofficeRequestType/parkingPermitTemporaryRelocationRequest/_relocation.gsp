


  
    <label for="requester" class="required"><g:message code="request.property.requester.label" /> *  <span><g:message code="request.property.requester.help" /></span></label>
            <label>${requester.firstName} ${requester.lastName}</label>
            <input type="hidden" value="${requester.id}" id="requesterId" name="requesterId"
              <g:if test="${!rqt.requesterAddress}" > class='autofill-requesterFilling-trigger' </g:if> />
            <br />
            

  

  
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
            

  

  
    <label class="required"><g:message code="pptrr.property.performChoice.label" /> *  <span><g:message code="pptrr.property.performChoice.help" /></span></label>
            <g:set var="performChoiceIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'performChoice', 'i18nPrefixCode':'pptrr.property.performChoice', 'htmlClass':'required  ', 
                              'lrEntries': lrTypes.performChoice.entries, 'depth':0]" />
            

  

  
    <label class="required"><g:message code="pptrr.property.equipmentUsed.label" /> *  <span><g:message code="pptrr.property.equipmentUsed.help" /></span></label>
            <g:set var="equipmentUsedIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'equipmentUsed', 'i18nPrefixCode':'pptrr.property.equipmentUsed', 'htmlClass':'required  ', 
                              'lrEntries': lrTypes.equipmentUsed.entries, 'depth':0]" />
            

  

  
    <label for="marque" class="required"><g:message code="pptrr.property.marque.label" /> *  <span><g:message code="pptrr.property.marque.help" /></span></label>
            <input  type="text" id="marque" name="marque" value="${rqt.marque?.toString()}" 
                    class="required  validate-regex ${rqt.stepStates['relocation'].invalidFields.contains('marque') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.marque.validationError" />" regex="^[\w\W]{0,255}$" maxlength="255" />
            

  

  
    <label for="immatriculation" class="required"><g:message code="pptrr.property.immatriculation.label" /> *  <span><g:message code="pptrr.property.immatriculation.help" /></span></label>
            <input  type="text" id="immatriculation" name="immatriculation" value="${rqt.immatriculation?.toString()}" 
                    class="required  validate-regex ${rqt.stepStates['relocation'].invalidFields.contains('immatriculation') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.immatriculation.validationError" />" regex="^[\w\W]{0,255}$" maxlength="255" />
            

  

  
    <label for="longeur" class="required"><g:message code="pptrr.property.longeur.label" /> *  <span><g:message code="pptrr.property.longeur.help" /></span></label>
            <input  type="text" id="longeur" name="longeur" value="${rqt.longeur?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['relocation'].invalidFields.contains('longeur') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.longeur.validationError" />"   />
            

  

  
    <label for="largeur" class="required"><g:message code="pptrr.property.largeur.label" /> *  <span><g:message code="pptrr.property.largeur.help" /></span></label>
            <input  type="text" id="largeur" name="largeur" value="${rqt.largeur?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['relocation'].invalidFields.contains('largeur') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.largeur.validationError" />"   />
            

  

  
    <label for="tonnage" class="required"><g:message code="pptrr.property.tonnage.label" /> *  <span><g:message code="pptrr.property.tonnage.help" /></span></label>
            <input  type="text" id="tonnage" name="tonnage" value="${rqt.tonnage?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['relocation'].invalidFields.contains('tonnage') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.tonnage.validationError" />"   />
            

  

  
    <label for="volume" class="required"><g:message code="pptrr.property.volume.label" /> *  <span><g:message code="pptrr.property.volume.help" /></span></label>
            <input  type="text" id="volume" name="volume" value="${rqt.volume?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['relocation'].invalidFields.contains('volume') ? 'validation-failed' : ''}" title="<g:message code="pptrr.property.volume.validationError" />"   />
            

  

