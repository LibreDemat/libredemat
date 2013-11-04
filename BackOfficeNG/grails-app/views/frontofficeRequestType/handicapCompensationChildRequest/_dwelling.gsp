


  
    <fieldset class="required">
    <legend><g:message code="hccr.property.dwelling.label" /></legend>
    
      <label for="dwellingKind" class="required"><g:message code="hccr.property.dwellingKind.label" /> *  <span><g:message code="hccr.property.dwellingKind.help" /></span></label>
            <select id="dwellingKind" name="dwellingKind" class="required condition-isNotPlaceOfResidence-trigger  validate-not-first ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingKind') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.dwellingKind.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['PLACE_OF_RESIDENCE','THIRD_PARTY_PLACE_OF_RESIDENCE','OTHER']}">
                <option value="${it}" ${it == rqt.dwellingKind?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="hccr.property.dwellingKind" /></option>
              </g:each>
            </select>
            

    
      <label for="dwellingPrecision" class="required condition-isNotPlaceOfResidence-filled"><g:message code="hccr.property.dwellingPrecision.label" /> *  <span><g:message code="hccr.property.dwellingPrecision.help" /></span></label>
            <textarea id="dwellingPrecision" name="dwellingPrecision" class="required condition-isNotPlaceOfResidence-filled  validate-textarea ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingPrecision') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.dwellingPrecision.validationError" />" rows="2" cols=""  maxlength="120">${rqt.dwellingPrecision}</textarea>
            

    
      <label class="required"><g:message code="hccr.property.dwellingEstablishmentReception.label" /> *  <span><g:message code="hccr.property.dwellingEstablishmentReception.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingEstablishmentReception') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="dwellingEstablishmentReception_${it ? 'yes' : 'no'}" class="required condition-isInEstablishmentReception-trigger  validate-one-required boolean" title="" value="${it}" name="dwellingEstablishmentReception" ${it == rqt.dwellingEstablishmentReception ? 'checked="checked"': ''} />
                <label for="dwellingEstablishmentReception_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      <label for="dwellingReceptionType" class="required condition-isInEstablishmentReception-filled"><g:message code="hccr.property.dwellingReceptionType.label" /> *  <span><g:message code="hccr.property.dwellingReceptionType.help" /></span></label>
            <select id="dwellingReceptionType" name="dwellingReceptionType" class="required condition-isInEstablishmentReception-filled  validate-not-first ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionType') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.dwellingReceptionType.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['INTERNSHIP','CLERKSHIP']}">
                <option value="${it}" ${it == rqt.dwellingReceptionType?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="hccr.property.dwellingReceptionType" /></option>
              </g:each>
            </select>
            

    
      <label for="dwellingReceptionNaming" class="required condition-isInEstablishmentReception-filled"><g:message code="hccr.property.dwellingReceptionNaming.label" /> *  <span><g:message code="hccr.property.dwellingReceptionNaming.help" /></span></label>
            <input type="text" id="dwellingReceptionNaming" name="dwellingReceptionNaming" value="${rqt.dwellingReceptionNaming?.toString()}" 
                    class="required condition-isInEstablishmentReception-filled   ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionNaming') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.dwellingReceptionNaming.validationError" />"  maxlength="80" />
            

    
      <label class="required condition-isInEstablishmentReception-filled"><g:message code="hccr.property.dwellingReceptionAddress.label" /> *  <span><g:message code="hccr.property.dwellingReceptionAddress.help" /></span></label>
            <div id="dwellingReceptionAddress" class="address required condition-isInEstablishmentReception-filled  ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress') ? 'validation-failed' : ''}">
            <label for="dwellingReceptionAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.additionalDeliveryInformation}" maxlength="38" id="dwellingReceptionAddress.additionalDeliveryInformation" name="dwellingReceptionAddress.additionalDeliveryInformation" />  
            <label for="dwellingReceptionAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.additionalGeographicalInformation}" maxlength="38" id="dwellingReceptionAddress.additionalGeographicalInformation" name="dwellingReceptionAddress.additionalGeographicalInformation" />
            <label for="dwellingReceptionAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="dwellingReceptionAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.streetNumber}" size="5" maxlength="5" id="dwellingReceptionAddress_streetNumber" name="dwellingReceptionAddress.streetNumber" />
            <input type="text" class="line2 required validate-streetName ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.streetName}" maxlength="32" id="dwellingReceptionAddress_streetName" name="dwellingReceptionAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <input type="hidden" value="${rqt.dwellingReceptionAddress?.streetMatriculation}" id="dwellingReceptionAddress_streetMatriculation" name="dwellingReceptionAddress.streetMatriculation" />
            <input type="hidden" value="${rqt.dwellingReceptionAddress?.streetRivoliCode}" id="dwellingReceptionAddress_streetRivoliCode" name="dwellingReceptionAddress.streetRivoliCode" />
            <label for="dwellingReceptionAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.placeNameOrService}" maxlength="38" id="dwellingReceptionAddress.placeNameOrService" name="dwellingReceptionAddress.placeNameOrService" />
            <label for="dwellingReceptionAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="dwellingReceptionAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.postalCode}" size="5" maxlength="5" id="dwellingReceptionAddress_postalCode" name="dwellingReceptionAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.city') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.city}" maxlength="32" id="dwellingReceptionAddress_city" name="dwellingReceptionAddress.city" title="<g:message code="address.property.city.validationError" />" />
            <input type="hidden" value="${rqt.dwellingReceptionAddress?.cityInseeCode}" id="dwellingReceptionAddress_cityInseeCode" name="dwellingReceptionAddress.cityInseeCode" />
            <label for="dwellingReceptionAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingReceptionAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.dwellingReceptionAddress?.countryName}" maxlength="38" id="dwellingReceptionAddress.countryName" name="dwellingReceptionAddress.countryName" />
            </div>
            

    
      <label class="required"><g:message code="hccr.property.dwellingSocialReception.label" /> *  <span><g:message code="hccr.property.dwellingSocialReception.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReception') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="dwellingSocialReception_${it ? 'yes' : 'no'}" class="required condition-isInSocialReception-trigger  validate-one-required boolean" title="" value="${it}" name="dwellingSocialReception" ${it == rqt.dwellingSocialReception ? 'checked="checked"': ''} />
                <label for="dwellingSocialReception_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      <label for="dwellingSocialReceptionNaming" class="required condition-isInSocialReception-filled"><g:message code="hccr.property.dwellingSocialReceptionNaming.label" /> *  <span><g:message code="hccr.property.dwellingSocialReceptionNaming.help" /></span></label>
            <input type="text" id="dwellingSocialReceptionNaming" name="dwellingSocialReceptionNaming" value="${rqt.dwellingSocialReceptionNaming?.toString()}" 
                    class="required condition-isInSocialReception-filled   ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionNaming') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.dwellingSocialReceptionNaming.validationError" />"  maxlength="80" />
            

    
      <label class="required condition-isInSocialReception-filled"><g:message code="hccr.property.dwellingSocialReceptionAddress.label" /> *  <span><g:message code="hccr.property.dwellingSocialReceptionAddress.help" /></span></label>
            <div id="dwellingSocialReceptionAddress" class="address required condition-isInSocialReception-filled  ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress') ? 'validation-failed' : ''}">
            <label for="dwellingSocialReceptionAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.additionalDeliveryInformation}" maxlength="38" id="dwellingSocialReceptionAddress.additionalDeliveryInformation" name="dwellingSocialReceptionAddress.additionalDeliveryInformation" />  
            <label for="dwellingSocialReceptionAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.additionalGeographicalInformation}" maxlength="38" id="dwellingSocialReceptionAddress.additionalGeographicalInformation" name="dwellingSocialReceptionAddress.additionalGeographicalInformation" />
            <label for="dwellingSocialReceptionAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="dwellingSocialReceptionAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.streetNumber}" size="5" maxlength="5" id="dwellingSocialReceptionAddress_streetNumber" name="dwellingSocialReceptionAddress.streetNumber" />
            <input type="text" class="line2 required validate-streetName ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.streetName}" maxlength="32" id="dwellingSocialReceptionAddress_streetName" name="dwellingSocialReceptionAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <input type="hidden" value="${rqt.dwellingSocialReceptionAddress?.streetMatriculation}" id="dwellingSocialReceptionAddress_streetMatriculation" name="dwellingSocialReceptionAddress.streetMatriculation" />
            <input type="hidden" value="${rqt.dwellingSocialReceptionAddress?.streetRivoliCode}" id="dwellingSocialReceptionAddress_streetRivoliCode" name="dwellingSocialReceptionAddress.streetRivoliCode" />
            <label for="dwellingSocialReceptionAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.placeNameOrService}" maxlength="38" id="dwellingSocialReceptionAddress.placeNameOrService" name="dwellingSocialReceptionAddress.placeNameOrService" />
            <label for="dwellingSocialReceptionAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="dwellingSocialReceptionAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.postalCode}" size="5" maxlength="5" id="dwellingSocialReceptionAddress_postalCode" name="dwellingSocialReceptionAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.city') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.city}" maxlength="32" id="dwellingSocialReceptionAddress_city" name="dwellingSocialReceptionAddress.city" title="<g:message code="address.property.city.validationError" />" />
            <input type="hidden" value="${rqt.dwellingSocialReceptionAddress?.cityInseeCode}" id="dwellingSocialReceptionAddress_cityInseeCode" name="dwellingSocialReceptionAddress.cityInseeCode" />
            <label for="dwellingSocialReceptionAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 ${rqt.stepStates['dwelling'].invalidFields.contains('dwellingSocialReceptionAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.dwellingSocialReceptionAddress?.countryName}" maxlength="38" id="dwellingSocialReceptionAddress.countryName" name="dwellingSocialReceptionAddress.countryName" />
            </div>
            

    
    </fieldset>
  

