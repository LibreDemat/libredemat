


  
    <fieldset class="required" id="DhrFamilyReferent">
    <legend><g:message code="dhr.property.dhrFamilyReferent.label" /></legend>
    
      
      <label class="required"><g:message code="dhr.property.dhrHaveFamilyReferent.label" /> *  <span><g:message code="dhr.property.dhrHaveFamilyReferent.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrHaveFamilyReferent') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="dhrHaveFamilyReferent_${it ? 'yes' : 'no'}" class="required condition-haveFamilyReferent-trigger  validate-one-required boolean" title="" value="${it}" name="dhrHaveFamilyReferent" ${it == rqt.dhrHaveFamilyReferent ? 'checked="checked"': ''} />
                <label for="dhrHaveFamilyReferent_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="dhrReferentName" class="required condition-haveFamilyReferent-filled"><g:message code="dhr.property.dhrReferentName.label" /> *  <span><g:message code="dhr.property.dhrReferentName.help" /></span></label>
            <input  type="text" id="dhrReferentName"
                   name="dhrReferentName"
                   value="${rqt.dhrReferentName?.toString()}"
                   class="required condition-haveFamilyReferent-filled  validate-lastName ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentName') ? 'validation-failed' : ''}"
                   title="<g:message code="dhr.property.dhrReferentName.validationError" />"  maxlength="38" />
            

    
      
      <label for="dhrReferentFirstName" class="required condition-haveFamilyReferent-filled"><g:message code="dhr.property.dhrReferentFirstName.label" /> *  <span><g:message code="dhr.property.dhrReferentFirstName.help" /></span></label>
            <input  type="text" id="dhrReferentFirstName"
                   name="dhrReferentFirstName"
                   value="${rqt.dhrReferentFirstName?.toString()}"
                   class="required condition-haveFamilyReferent-filled  validate-firstName ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentFirstName') ? 'validation-failed' : ''}"
                   title="<g:message code="dhr.property.dhrReferentFirstName.validationError" />"  maxlength="38" />
            

    
      
      <label class="required condition-haveFamilyReferent-filled"><g:message code="dhr.property.dhrReferentAddress.label" /> *  <span><g:message code="dhr.property.dhrReferentAddress.help" /></span></label>
            <div id="dhrReferentAddress" class="address required condition-haveFamilyReferent-filled  ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress') ? 'validation-failed' : ''}">
            <label for="dhrReferentAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.additionalDeliveryInformation}" maxlength="38" id="dhrReferentAddress.additionalDeliveryInformation" name="dhrReferentAddress.additionalDeliveryInformation" />  
            <label for="dhrReferentAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.additionalGeographicalInformation}" maxlength="38" id="dhrReferentAddress.additionalGeographicalInformation" name="dhrReferentAddress.additionalGeographicalInformation" />
            <label for="dhrReferentAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="dhrReferentAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input  type="text" class="line1 validate-streetNumber ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.streetNumber}" size="5" maxlength="5" id="dhrReferentAddress_streetNumber" name="dhrReferentAddress.streetNumber" autocomplete="off" />
            <input  type="text" class="line2 required validate-streetName ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.streetName}" maxlength="32" id="dhrReferentAddress_streetName" name="dhrReferentAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.dhrReferentAddress?.streetMatriculation}" id="dhrReferentAddress_streetMatriculation" name="dhrReferentAddress.streetMatriculation" />
            <input  type="hidden" value="${rqt.dhrReferentAddress?.streetRivoliCode}" id="dhrReferentAddress_streetRivoliCode" name="dhrReferentAddress.streetRivoliCode" />
            <label for="dhrReferentAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.placeNameOrService}" maxlength="38" id="dhrReferentAddress.placeNameOrService" name="dhrReferentAddress.placeNameOrService" />
            <label for="dhrReferentAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="dhrReferentAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input  type="text" class="line1 required validate-postalCode ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.postalCode}" size="5" maxlength="5" id="dhrReferentAddress_postalCode" name="dhrReferentAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" autocomplete="off" />
            <input  type="text" class="line2 required validate-city ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.city') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.city}" maxlength="32" id="dhrReferentAddress_city" name="dhrReferentAddress.city" title="<g:message code="address.property.city.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.dhrReferentAddress?.cityInseeCode}" id="dhrReferentAddress_cityInseeCode" name="dhrReferentAddress.cityInseeCode" />
            <label for="dhrReferentAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['familyReferent'].invalidFields.contains('dhrReferentAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.dhrReferentAddress?.countryName}" maxlength="38" id="dhrReferentAddress.countryName" name="dhrReferentAddress.countryName" />
            </div>
            

    
    </fieldset>
  

