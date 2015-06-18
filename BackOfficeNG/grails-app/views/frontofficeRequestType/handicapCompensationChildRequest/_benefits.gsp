


  
    <fieldset class="required" id="Benefits">
    <legend><g:message code="hccr.property.benefits.label" /></legend>
    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabilityRecognition.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityRecognition.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityRecognition') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabilityRecognition_${it ? 'yes' : 'no'}" class="required condition-isDisabilityRecognition-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsDisabilityRecognition" ${it == rqt.benefitsDisabilityRecognition ? 'checked="checked"': ''} />
                <label for="benefitsDisabilityRecognition_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsDisabilityRatio" class="required condition-isDisabilityRecognition-filled"><g:message code="hccr.property.benefitsDisabilityRatio.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityRatio.help" /></span></label>
            <input  type="text" id="benefitsDisabilityRatio"
                   name="benefitsDisabilityRatio"
                   value="${rqt.benefitsDisabilityRatio?.toString()}"
                   class="required condition-isDisabilityRecognition-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityRatio') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsDisabilityRatio.validationError" />"  maxlength="3" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabilityCard.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityCard.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityCard') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabilityCard_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsDisabilityCard" ${it == rqt.benefitsDisabilityCard ? 'checked="checked"': ''} />
                <label for="benefitsDisabilityCard_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsPainfulStandingCard.label" /> *  <span><g:message code="hccr.property.benefitsPainfulStandingCard.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsPainfulStandingCard') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsPainfulStandingCard_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsPainfulStandingCard" ${it == rqt.benefitsPainfulStandingCard ? 'checked="checked"': ''} />
                <label for="benefitsPainfulStandingCard_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsParkingCard.label" /> *  <span><g:message code="hccr.property.benefitsParkingCard.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsParkingCard') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsParkingCard_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsParkingCard" ${it == rqt.benefitsParkingCard ? 'checked="checked"': ''} />
                <label for="benefitsParkingCard_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabledWorkerRecognition.label" /> *  <span><g:message code="hccr.property.benefitsDisabledWorkerRecognition.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabledWorkerRecognition') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabledWorkerRecognition_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsDisabledWorkerRecognition" ${it == rqt.benefitsDisabledWorkerRecognition ? 'checked="checked"': ''} />
                <label for="benefitsDisabledWorkerRecognition_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsProfessionalOrientation.label" /> *  <span><g:message code="hccr.property.benefitsProfessionalOrientation.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsProfessionalOrientation') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsProfessionalOrientation_${it ? 'yes' : 'no'}" class="required condition-isProfessionalOrientation-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsProfessionalOrientation" ${it == rqt.benefitsProfessionalOrientation ? 'checked="checked"': ''} />
                <label for="benefitsProfessionalOrientation_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsProfessionalOrientationDetails" class="required condition-isProfessionalOrientation-filled"><g:message code="hccr.property.benefitsProfessionalOrientationDetails.label" /> *  <span><g:message code="hccr.property.benefitsProfessionalOrientationDetails.help" /></span></label>
            <input  type="text" id="benefitsProfessionalOrientationDetails"
                   name="benefitsProfessionalOrientationDetails"
                   value="${rqt.benefitsProfessionalOrientationDetails?.toString()}"
                   class="required condition-isProfessionalOrientation-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsProfessionalOrientationDetails') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsProfessionalOrientationDetails.validationError" />"  maxlength="60" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabledAdultAllocation.label" /> *  <span><g:message code="hccr.property.benefitsDisabledAdultAllocation.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabledAdultAllocation') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabledAdultAllocation_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsDisabledAdultAllocation" ${it == rqt.benefitsDisabledAdultAllocation ? 'checked="checked"': ''} />
                <label for="benefitsDisabledAdultAllocation_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsIncreaseForIndependentLiving.label" /> *  <span><g:message code="hccr.property.benefitsIncreaseForIndependentLiving.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsIncreaseForIndependentLiving') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsIncreaseForIndependentLiving_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsIncreaseForIndependentLiving" ${it == rqt.benefitsIncreaseForIndependentLiving ? 'checked="checked"': ''} />
                <label for="benefitsIncreaseForIndependentLiving_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsEducationAllocationOfDisabledChildren.label" /> *  <span><g:message code="hccr.property.benefitsEducationAllocationOfDisabledChildren.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsEducationAllocationOfDisabledChildren') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsEducationAllocationOfDisabledChildren_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsEducationAllocationOfDisabledChildren" ${it == rqt.benefitsEducationAllocationOfDisabledChildren ? 'checked="checked"': ''} />
                <label for="benefitsEducationAllocationOfDisabledChildren_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsEducationOfDisabledChildren.label" /> *  <span><g:message code="hccr.property.benefitsEducationOfDisabledChildren.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsEducationOfDisabledChildren') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsEducationOfDisabledChildren_${it ? 'yes' : 'no'}" class="required condition-isEducationOfDisabledChildren-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsEducationOfDisabledChildren" ${it == rqt.benefitsEducationOfDisabledChildren ? 'checked="checked"': ''} />
                <label for="benefitsEducationOfDisabledChildren_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsEducationOfDisabledChildrenDetails" class="required condition-isEducationOfDisabledChildren-filled"><g:message code="hccr.property.benefitsEducationOfDisabledChildrenDetails.label" /> *  <span><g:message code="hccr.property.benefitsEducationOfDisabledChildrenDetails.help" /></span></label>
            <input  type="text" id="benefitsEducationOfDisabledChildrenDetails"
                   name="benefitsEducationOfDisabledChildrenDetails"
                   value="${rqt.benefitsEducationOfDisabledChildrenDetails?.toString()}"
                   class="required condition-isEducationOfDisabledChildren-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsEducationOfDisabledChildrenDetails') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsEducationOfDisabledChildrenDetails.validationError" />"  maxlength="60" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsSupplementForSingleParents.label" /> *  <span><g:message code="hccr.property.benefitsSupplementForSingleParents.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsSupplementForSingleParents') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsSupplementForSingleParents_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsSupplementForSingleParents" ${it == rqt.benefitsSupplementForSingleParents ? 'checked="checked"': ''} />
                <label for="benefitsSupplementForSingleParents_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsThirdPersonCompensatoryAllowance.label" /> *  <span><g:message code="hccr.property.benefitsThirdPersonCompensatoryAllowance.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsThirdPersonCompensatoryAllowance') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsThirdPersonCompensatoryAllowance_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsThirdPersonCompensatoryAllowance" ${it == rqt.benefitsThirdPersonCompensatoryAllowance ? 'checked="checked"': ''} />
                <label for="benefitsThirdPersonCompensatoryAllowance_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsThirdPartyCompensatoryAllowance.label" /> *  <span><g:message code="hccr.property.benefitsThirdPartyCompensatoryAllowance.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsThirdPartyCompensatoryAllowance') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsThirdPartyCompensatoryAllowance_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsThirdPartyCompensatoryAllowance" ${it == rqt.benefitsThirdPartyCompensatoryAllowance ? 'checked="checked"': ''} />
                <label for="benefitsThirdPartyCompensatoryAllowance_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsCompensatoryAllowanceForExpenses.label" /> *  <span><g:message code="hccr.property.benefitsCompensatoryAllowanceForExpenses.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsCompensatoryAllowanceForExpenses') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsCompensatoryAllowanceForExpenses_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsCompensatoryAllowanceForExpenses" ${it == rqt.benefitsCompensatoryAllowanceForExpenses ? 'checked="checked"': ''} />
                <label for="benefitsCompensatoryAllowanceForExpenses_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabilityCompensation.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityCompensation.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityCompensation') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabilityCompensation_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsDisabilityCompensation" ${it == rqt.benefitsDisabilityCompensation ? 'checked="checked"': ''} />
                <label for="benefitsDisabilityCompensation_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDisabilityPension.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityPension.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityPension') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDisabilityPension_${it ? 'yes' : 'no'}" class="required condition-isDisabilityPension-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsDisabilityPension" ${it == rqt.benefitsDisabilityPension ? 'checked="checked"': ''} />
                <label for="benefitsDisabilityPension_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsDisabilityPensionCategory" class="required condition-isDisabilityPension-filled"><g:message code="hccr.property.benefitsDisabilityPensionCategory.label" /> *  <span><g:message code="hccr.property.benefitsDisabilityPensionCategory.help" /></span></label>
            <input  type="text" id="benefitsDisabilityPensionCategory"
                   name="benefitsDisabilityPensionCategory"
                   value="${rqt.benefitsDisabilityPensionCategory?.toString()}"
                   class="required condition-isDisabilityPension-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDisabilityPensionCategory') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsDisabilityPensionCategory.validationError" />"  maxlength="60" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsWorkAccidentAnnuity.label" /> *  <span><g:message code="hccr.property.benefitsWorkAccidentAnnuity.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsWorkAccidentAnnuity') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsWorkAccidentAnnuity_${it ? 'yes' : 'no'}" class="required condition-isWorkAccidentAnnuity-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsWorkAccidentAnnuity" ${it == rqt.benefitsWorkAccidentAnnuity ? 'checked="checked"': ''} />
                <label for="benefitsWorkAccidentAnnuity_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsWorkAccidentAnnuityRatio" class="required condition-isWorkAccidentAnnuity-filled"><g:message code="hccr.property.benefitsWorkAccidentAnnuityRatio.label" /> *  <span><g:message code="hccr.property.benefitsWorkAccidentAnnuityRatio.help" /></span></label>
            <input  type="text" id="benefitsWorkAccidentAnnuityRatio"
                   name="benefitsWorkAccidentAnnuityRatio"
                   value="${rqt.benefitsWorkAccidentAnnuityRatio?.toString()}"
                   class="required condition-isWorkAccidentAnnuity-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsWorkAccidentAnnuityRatio') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsWorkAccidentAnnuityRatio.validationError" />"  maxlength="3" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsSocialWelfare.label" /> *  <span><g:message code="hccr.property.benefitsSocialWelfare.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsSocialWelfare') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsSocialWelfare_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsSocialWelfare" ${it == rqt.benefitsSocialWelfare ? 'checked="checked"': ''} />
                <label for="benefitsSocialWelfare_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsUnemploymentBenefits.label" /> *  <span><g:message code="hccr.property.benefitsUnemploymentBenefits.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsUnemploymentBenefits') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsUnemploymentBenefits_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsUnemploymentBenefits" ${it == rqt.benefitsUnemploymentBenefits ? 'checked="checked"': ''} />
                <label for="benefitsUnemploymentBenefits_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsDailyAllowances.label" /> *  <span><g:message code="hccr.property.benefitsDailyAllowances.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsDailyAllowances') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsDailyAllowances_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsDailyAllowances" ${it == rqt.benefitsDailyAllowances ? 'checked="checked"': ''} />
                <label for="benefitsDailyAllowances_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsThirdPartySupplement.label" /> *  <span><g:message code="hccr.property.benefitsThirdPartySupplement.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsThirdPartySupplement') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsThirdPartySupplement_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="benefitsThirdPartySupplement" ${it == rqt.benefitsThirdPartySupplement ? 'checked="checked"': ''} />
                <label for="benefitsThirdPartySupplement_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsSupportedByAnInstitution.label" /> *  <span><g:message code="hccr.property.benefitsSupportedByAnInstitution.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsSupportedByAnInstitution') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsSupportedByAnInstitution_${it ? 'yes' : 'no'}" class="required condition-isSupportedByAnInstitution-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsSupportedByAnInstitution" ${it == rqt.benefitsSupportedByAnInstitution ? 'checked="checked"': ''} />
                <label for="benefitsSupportedByAnInstitution_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label for="benefitsSupportedByAnInstitutionDetails" class="required condition-isSupportedByAnInstitution-filled"><g:message code="hccr.property.benefitsSupportedByAnInstitutionDetails.label" /> *  <span><g:message code="hccr.property.benefitsSupportedByAnInstitutionDetails.help" /></span></label>
            <input  type="text" id="benefitsSupportedByAnInstitutionDetails"
                   name="benefitsSupportedByAnInstitutionDetails"
                   value="${rqt.benefitsSupportedByAnInstitutionDetails?.toString()}"
                   class="required condition-isSupportedByAnInstitution-filled   ${rqt.stepStates['benefits'].invalidFields.contains('benefitsSupportedByAnInstitutionDetails') ? 'validation-failed' : ''}"
                   title="<g:message code="hccr.property.benefitsSupportedByAnInstitutionDetails.validationError" />"  maxlength="60" />
            

    
      
      <label class="required"><g:message code="hccr.property.benefitsOtherBenefits.label" /> *  <span><g:message code="hccr.property.benefitsOtherBenefits.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['benefits'].invalidFields.contains('benefitsOtherBenefits') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="benefitsOtherBenefits_${it ? 'yes' : 'no'}" class="required condition-isOtherBenefits-trigger  validate-one-required boolean" title="" value="${it}" name="benefitsOtherBenefits" ${it == rqt.benefitsOtherBenefits ? 'checked="checked"': ''} />
                <label for="benefitsOtherBenefits_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
    </fieldset>
  

  
    <div class="collection condition-isOtherBenefits-filled summary-box">
      <h4 class="condition-isOtherBenefits-filled"><g:message code="hccr.property.otherBenefits.label" /> 
        <span><g:message code="hccr.property.otherBenefits.help" /></span>
      </h4>

    <g:if test="${true && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit',            params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'otherBenefits',
                    'collectionIndex':(rqt.otherBenefits ? rqt.otherBenefits.size() : 0),
                    'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}"
           style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.otherBenefits}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="hccr.property.otherBenefits.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'otherBenefits', 'collectionIndex':index, 'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'otherBenefits', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="hccr.property.otherBenefitName.label" /></dt>
        <dd class="${rqt.stepStates['benefits'].invalidFields.contains('otherBenefits[' + index + '].otherBenefitName') ? 'validation-failed' : ''}">${it.otherBenefitName?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

  
    <div class="collection  summary-box">
      <h4 class=""><g:message code="hccr.property.additionalFee.label" /> 
        <span><g:message code="hccr.property.additionalFee.help" /></span>
      </h4>

    <g:if test="${true && !isEdition}">
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit',            params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'additionalFee',
                    'collectionIndex':(rqt.additionalFee ? rqt.additionalFee.size() : 0),
                    'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}"
           style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    </g:if>
    <g:each var="it" in="${rqt.additionalFee}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="hccr.property.additionalFee.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'additionalFee', 'collectionIndex':index, 'collectionIndexAdded':collectionIndexAdded, 'collectionSpecific' : collectionSpecific])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'benefits', 'currentCollection':'additionalFee', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="hccr.property.additionalFeeKind.label" /></dt>
        <dd class="${rqt.stepStates['benefits'].invalidFields.contains('additionalFee[' + index + '].additionalFeeKind') ? 'validation-failed' : ''}">${it.additionalFeeKind?.toString()}</dd>
    
        <dt><g:message code="hccr.property.additionalFeeCost.label" /></dt>
        <dd class="${rqt.stepStates['benefits'].invalidFields.contains('additionalFee[' + index + '].additionalFeeCost') ? 'validation-failed' : ''}">${it.additionalFeeCost?.toString()}</dd>
    
        <dt><g:message code="hccr.property.additionalFeePeriodicity.label" /></dt>
        <dd class="${rqt.stepStates['benefits'].invalidFields.contains('additionalFee[' + index + '].additionalFeePeriodicity') ? 'validation-failed' : ''}">${it.additionalFeePeriodicity?.toString()}</dd>
    
        </dl>
      </div>
    </g:each>
    </div>
  

