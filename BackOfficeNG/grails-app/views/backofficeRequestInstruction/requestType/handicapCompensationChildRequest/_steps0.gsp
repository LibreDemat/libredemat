



<!-- step start -->
<div id="page0">
  <h2><g:message code="property.form" />
    <span><g:message code="hccr.step.subject.label" /></span>
  </h2>
  <div class="yui-g">
    
    
    <!-- column start -->
    <div class="yui-u first">
      
        
        <h3><g:message code="hccr.property.hccrSubject.label" /></h3>
        <dl class="required">
          
              <dt class="required"><g:message code="hccr.property.subject.label" /> : </dt>
             <dd><span>${rqt?.subjectFirstName} ${rqt?.subjectLastName}</span></dd>
          
          
              <dt class="required condition-isLessThan18-trigger">${message(code:'hccr.property.subjectBirthDate.label')}&nbsp;*&nbsp;:</dt><dd id="subjectBirthDate" class="action-editField validate-date required-true i18n-hccr.property.subjectBirthDate" ><span><g:formatDate formatName="format.date" date="${rqt?.subjectBirthDate}"/></span></dd>
          
              <dt class="required">${message(code:'hccr.property.subjectBirthCity.label')}&nbsp;*&nbsp;:</dt><dd id="subjectBirthCity" class="action-editField validate-city required-true i18n-hccr.property.subjectBirthCity maxLength-32" ><span>${rqt?.subjectBirthCity}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.subjectBirthCountry.label')}&nbsp;*&nbsp;:</dt><dd id="subjectBirthCountry" class="action-editField validate- required-true i18n-hccr.property.subjectBirthCountry maxLength-50" ><span>${rqt?.subjectBirthCountry}</span></dd>
          
              <dt class="required condition-isLessThan18-filled">${message(code:'hccr.property.subjectParentalAuthorityHolder.label')}&nbsp;*&nbsp;:</dt><dd id="subjectParentalAuthorityHolder" class="action-editField validate-capdematEnum required-true i18n-hccr.property.subjectParentalAuthorityHolder javatype-fr.cg95.cvq.business.request.social.HccrSubjectParentalAuthorityHolderType" ><g:capdematEnumToField var="${rqt?.subjectParentalAuthorityHolder}" i18nKeyPrefix="hccr.property.subjectParentalAuthorityHolder" /></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
    <!-- column start -->
    <div class="yui-u">
      
        
        <h3><g:message code="hccr.property.father.label" /></h3>
        <dl class="required condition-isLessThan18-filled">
          
              <dt class="">${message(code:'hccr.property.fatherLastName.label')}&nbsp;:</dt><dd id="fatherLastName" class="action-editField validate-lastName i18n-hccr.property.fatherLastName maxLength-38" ><span>${rqt?.fatherLastName}</span></dd>
          
              <dt class="">${message(code:'hccr.property.fatherFirstName.label')}&nbsp;:</dt><dd id="fatherFirstName" class="action-editField validate-firstName i18n-hccr.property.fatherFirstName maxLength-38" ><span>${rqt?.fatherFirstName}</span></dd>
          
              <dt class="">${message(code:'hccr.property.fatherJob.label')}&nbsp;:</dt><dd id="fatherJob" class="action-editField validate- i18n-hccr.property.fatherJob maxLength-60" ><span>${rqt?.fatherJob}</span></dd>
          
              <dt class="condition-isFatherActivityReduction-trigger">${message(code:'hccr.property.fatherActivityReduction.label')}&nbsp;:</dt><dd id="fatherActivityReduction" class="action-editField validate-boolean i18n-hccr.property.fatherActivityReduction" ><span class="value-${rqt?.fatherActivityReduction}"><g:message code="message.${rqt?.fatherActivityReduction ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="condition-isFatherActivityReduction-filled">${message(code:'hccr.property.fatherActivityReductionRatio.label')}&nbsp;:</dt><dd id="fatherActivityReductionRatio" class="action-editField validate-positiveInteger i18n-hccr.property.fatherActivityReductionRatio" ><span>${rqt?.fatherActivityReductionRatio}</span></dd>
          
        </dl>
        
      
        
        <h3><g:message code="hccr.property.mother.label" /></h3>
        <dl class="required condition-isLessThan18-filled">
          
              <dt class="">${message(code:'hccr.property.motherLastName.label')}&nbsp;:</dt><dd id="motherLastName" class="action-editField validate-lastName i18n-hccr.property.motherLastName maxLength-38" ><span>${rqt?.motherLastName}</span></dd>
          
              <dt class="">${message(code:'hccr.property.motherFirstName.label')}&nbsp;:</dt><dd id="motherFirstName" class="action-editField validate-firstName i18n-hccr.property.motherFirstName maxLength-38" ><span>${rqt?.motherFirstName}</span></dd>
          
              <dt class="">${message(code:'hccr.property.motherJob.label')}&nbsp;:</dt><dd id="motherJob" class="action-editField validate- i18n-hccr.property.motherJob maxLength-60" ><span>${rqt?.motherJob}</span></dd>
          
              <dt class="condition-isMotherActivityReduction-trigger">${message(code:'hccr.property.motherActivityReduction.label')}&nbsp;:</dt><dd id="motherActivityReduction" class="action-editField validate-boolean i18n-hccr.property.motherActivityReduction" ><span class="value-${rqt?.motherActivityReduction}"><g:message code="message.${rqt?.motherActivityReduction ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="condition-isMotherActivityReduction-filled">${message(code:'hccr.property.motherActivityReductionRatio.label')}&nbsp;:</dt><dd id="motherActivityReductionRatio" class="action-editField validate-positiveInteger i18n-hccr.property.motherActivityReductionRatio" ><span>${rqt?.motherActivityReductionRatio}</span></dd>
          
        </dl>
        
      
        
        <h3><g:message code="hccr.property.aseReferent.label" /></h3>
        <dl class="required condition-isLessThan18-filled">
          
              <dt class="">${message(code:'hccr.property.aseReferentLastName.label')}&nbsp;:</dt><dd id="aseReferentLastName" class="action-editField validate-lastName i18n-hccr.property.aseReferentLastName maxLength-38" ><span>${rqt?.aseReferentLastName}</span></dd>
          
              <dt class="">${message(code:'hccr.property.aseReferentDepartment.label')}&nbsp;:</dt><dd id="aseReferentDepartment" class="action-editField validate-departmentCode i18n-hccr.property.aseReferentDepartment maxLength-2" ><span>${rqt?.aseReferentDepartment}</span></dd>
          
        </dl>
        
      
        
        <h3><g:message code="hccr.property.referent.label" /></h3>
        <dl class="required">
          
              <dt class="required">${message(code:'hccr.property.referentLastName.label')}&nbsp;*&nbsp;:</dt><dd id="referentLastName" class="action-editField validate-lastName required-true i18n-hccr.property.referentLastName maxLength-38" ><span>${rqt?.referentLastName}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.referentFirstName.label')}&nbsp;*&nbsp;:</dt><dd id="referentFirstName" class="action-editField validate-firstName required-true i18n-hccr.property.referentFirstName maxLength-38" ><span>${rqt?.referentFirstName}</span></dd>
          
              <dt class="required condition-isReferentMadam-trigger">${message(code:'hccr.property.referentTitle.label')}&nbsp;*&nbsp;:</dt><dd id="referentTitle" class="action-editField validate-capdematEnum required-true i18n-hccr.property.referentTitle javatype-fr.cg95.cvq.business.users.TitleType" ><g:capdematEnumToField var="${rqt?.referentTitle}" i18nKeyPrefix="hccr.property.referentTitle" /></dd>
          
              <dt class="required condition-isReferentMadam-filled">${message(code:'hccr.property.referentMaidenName.label')}&nbsp;*&nbsp;:</dt><dd id="referentMaidenName" class="action-editField validate-lastName required-true i18n-hccr.property.referentMaidenName maxLength-38" ><span>${rqt?.referentMaidenName}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.referentBirthDate.label')}&nbsp;*&nbsp;:</dt><dd id="referentBirthDate" class="action-editField validate-date required-true i18n-hccr.property.referentBirthDate" ><span><g:formatDate formatName="format.date" date="${rqt?.referentBirthDate}"/></span></dd>
          
              <dt class="required">${message(code:'hccr.property.referentBirthCity.label')}&nbsp;*&nbsp;:</dt><dd id="referentBirthCity" class="action-editField validate-city required-true i18n-hccr.property.referentBirthCity maxLength-32" ><span>${rqt?.referentBirthCity}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.referentBirthCountry.label')}&nbsp;*&nbsp;:</dt><dd id="referentBirthCountry" class="action-editField validate- required-true i18n-hccr.property.referentBirthCountry maxLength-50" ><span>${rqt?.referentBirthCountry}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.referentFamilyStatus.label')}&nbsp;*&nbsp;:</dt><dd id="referentFamilyStatus" class="action-editField validate-capdematEnum required-true i18n-hccr.property.referentFamilyStatus javatype-fr.cg95.cvq.business.users.FamilyStatusType" ><g:capdematEnumToField var="${rqt?.referentFamilyStatus}" i18nKeyPrefix="hccr.property.referentFamilyStatus" /></dd>
          
              <dt class="required condition-isReferentFamilyDependents-trigger">${message(code:'hccr.property.referentFamilyDependents.label')}&nbsp;*&nbsp;:</dt><dd id="referentFamilyDependents" class="action-editField validate-boolean required-true i18n-hccr.property.referentFamilyDependents" ><span class="value-${rqt?.referentFamilyDependents}"><g:message code="message.${rqt?.referentFamilyDependents ? 'yes' : 'no'}" /></span></dd>
          
        </dl>
        
      
        
        <div id="widget-familyDependents" class="required condition-isReferentFamilyDependents-filled">
          <g:render template="/backofficeRequestInstruction/requestType/handicapCompensationChildRequest/familyDependents" model="['rqt':rqt]" />
        </div>
        
      
    </div>
    <!-- column end -->
    
  </div>
  <!-- data step  end -->
</div>
<!-- step end -->

<!-- step start -->
<div id="page1">
  <h2><g:message code="property.form" />
    <span><g:message code="hccr.step.dwelling.label" /></span>
  </h2>
  <div class="yui-g">
    
    
    <!-- column start -->
    <div class="yui-u first">
      
        
        <h3><g:message code="hccr.property.dwelling.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isNotPlaceOfResidence-trigger">${message(code:'hccr.property.dwellingKind.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingKind" class="action-editField validate-capdematEnum required-true i18n-hccr.property.dwellingKind javatype-fr.cg95.cvq.business.request.social.HccrDwellingKindType" ><g:capdematEnumToField var="${rqt?.dwellingKind}" i18nKeyPrefix="hccr.property.dwellingKind" /></dd>
          
              <dt class="required condition-isNotPlaceOfResidence-filled">${message(code:'hccr.property.dwellingPrecision.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingPrecision" class="action-editField validate-textarea required-true i18n-hccr.property.dwellingPrecision rows-2 maxLength-120" ><span>${rqt?.dwellingPrecision}</span></dd>
          
              <dt class="required condition-isInEstablishmentReception-trigger">${message(code:'hccr.property.dwellingEstablishmentReception.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingEstablishmentReception" class="action-editField validate-boolean required-true i18n-hccr.property.dwellingEstablishmentReception" ><span class="value-${rqt?.dwellingEstablishmentReception}"><g:message code="message.${rqt?.dwellingEstablishmentReception ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isInEstablishmentReception-filled">${message(code:'hccr.property.dwellingReceptionType.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingReceptionType" class="action-editField validate-capdematEnum required-true i18n-hccr.property.dwellingReceptionType javatype-fr.cg95.cvq.business.request.social.HccrDwellingReceptionKindType" ><g:capdematEnumToField var="${rqt?.dwellingReceptionType}" i18nKeyPrefix="hccr.property.dwellingReceptionType" /></dd>
          
              <dt class="required condition-isInEstablishmentReception-filled">${message(code:'hccr.property.dwellingReceptionNaming.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingReceptionNaming" class="action-editField validate- required-true i18n-hccr.property.dwellingReceptionNaming maxLength-80" ><span>${rqt?.dwellingReceptionNaming}</span></dd>
          
              <dt class="required condition-isInEstablishmentReception-filled">${message(code:'hccr.property.dwellingReceptionAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingReceptionAddress" class="action-editField validate-address required-true i18n-hccr.property.dwellingReceptionAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dwellingReceptionAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dwellingReceptionAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dwellingReceptionAddress?.streetNumber}</span> <span class="streetName">${rqt?.dwellingReceptionAddress?.streetName}</span><g:if test="${!!rqt?.dwellingReceptionAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dwellingReceptionAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dwellingReceptionAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dwellingReceptionAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dwellingReceptionAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dwellingReceptionAddress?.postalCode}</span> <span class="city">${rqt?.dwellingReceptionAddress?.city}</span><p class="countryName">${rqt?.dwellingReceptionAddress?.countryName}</p><g:if test="${!!rqt?.dwellingReceptionAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dwellingReceptionAddress?.cityInseeCode}</span></g:if></div></dd>
          
              <dt class="required condition-isInSocialReception-trigger">${message(code:'hccr.property.dwellingSocialReception.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingSocialReception" class="action-editField validate-boolean required-true i18n-hccr.property.dwellingSocialReception" ><span class="value-${rqt?.dwellingSocialReception}"><g:message code="message.${rqt?.dwellingSocialReception ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isInSocialReception-filled">${message(code:'hccr.property.dwellingSocialReceptionNaming.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingSocialReceptionNaming" class="action-editField validate- required-true i18n-hccr.property.dwellingSocialReceptionNaming maxLength-80" ><span>${rqt?.dwellingSocialReceptionNaming}</span></dd>
          
              <dt class="required condition-isInSocialReception-filled">${message(code:'hccr.property.dwellingSocialReceptionAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dwellingSocialReceptionAddress" class="action-editField validate-address required-true i18n-hccr.property.dwellingSocialReceptionAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dwellingSocialReceptionAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dwellingSocialReceptionAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dwellingSocialReceptionAddress?.streetNumber}</span> <span class="streetName">${rqt?.dwellingSocialReceptionAddress?.streetName}</span><g:if test="${!!rqt?.dwellingSocialReceptionAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dwellingSocialReceptionAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dwellingSocialReceptionAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dwellingSocialReceptionAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dwellingSocialReceptionAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dwellingSocialReceptionAddress?.postalCode}</span> <span class="city">${rqt?.dwellingSocialReceptionAddress?.city}</span><p class="countryName">${rqt?.dwellingSocialReceptionAddress?.countryName}</p><g:if test="${!!rqt?.dwellingSocialReceptionAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dwellingSocialReceptionAddress?.cityInseeCode}</span></g:if></div></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
    <!-- column start -->
    <div class="yui-u">
      
    </div>
    <!-- column end -->
    
  </div>
  <!-- data step  end -->
</div>
<!-- step end -->

<!-- step start -->
<div id="page2">
  <h2><g:message code="property.form" />
    <span><g:message code="hccr.step.socialSecurityAndPaymentAgency.label" /></span>
  </h2>
  <div class="yui-g">
    
    
    <!-- column start -->
    <div class="yui-u first">
      
        
        <h3><g:message code="hccr.property.socialSecurity.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isSocialSecurityMemberShip-trigger">${message(code:'hccr.property.socialSecurityMemberShipKind.label')}&nbsp;*&nbsp;:</dt><dd id="socialSecurityMemberShipKind" class="action-editField validate-capdematEnum required-true i18n-hccr.property.socialSecurityMemberShipKind javatype-fr.cg95.cvq.business.request.social.HccrSocialSecurityMemberShipKindType" ><g:capdematEnumToField var="${rqt?.socialSecurityMemberShipKind}" i18nKeyPrefix="hccr.property.socialSecurityMemberShipKind" /></dd>
          
              <dt class="required condition-isSocialSecurityMemberShip-filled">${message(code:'hccr.property.socialSecurityNumber.label')}&nbsp;*&nbsp;:</dt><dd id="socialSecurityNumber" class="action-editField validate- required-true i18n-hccr.property.socialSecurityNumber" ><span>${rqt?.socialSecurityNumber}</span></dd>
          
              <dt class="required condition-isSocialSecurityMemberShip-filled">${message(code:'hccr.property.socialSecurityAgencyName.label')}&nbsp;*&nbsp;:</dt><dd id="socialSecurityAgencyName" class="action-editField validate- required-true i18n-hccr.property.socialSecurityAgencyName maxLength-50" ><span>${rqt?.socialSecurityAgencyName}</span></dd>
          
              <dt class="required condition-isSocialSecurityMemberShip-filled">${message(code:'hccr.property.socialSecurityAgencyAddress.label')}&nbsp;*&nbsp;:</dt><dd id="socialSecurityAgencyAddress" class="action-editField validate-address required-true i18n-hccr.property.socialSecurityAgencyAddress" ><div><p class="additionalDeliveryInformation">${rqt?.socialSecurityAgencyAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.socialSecurityAgencyAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.socialSecurityAgencyAddress?.streetNumber}</span> <span class="streetName">${rqt?.socialSecurityAgencyAddress?.streetName}</span><g:if test="${!!rqt?.socialSecurityAgencyAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.socialSecurityAgencyAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.socialSecurityAgencyAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.socialSecurityAgencyAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.socialSecurityAgencyAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.socialSecurityAgencyAddress?.postalCode}</span> <span class="city">${rqt?.socialSecurityAgencyAddress?.city}</span><p class="countryName">${rqt?.socialSecurityAgencyAddress?.countryName}</p><g:if test="${!!rqt?.socialSecurityAgencyAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.socialSecurityAgencyAddress?.cityInseeCode}</span></g:if></div></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
    <!-- column start -->
    <div class="yui-u">
      
        
        <h3><g:message code="hccr.property.paymentAgency.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isPaymentAgencyBeneficiary-trigger">${message(code:'hccr.property.paymentAgencyBeneficiary.label')}&nbsp;*&nbsp;:</dt><dd id="paymentAgencyBeneficiary" class="action-editField validate-capdematEnum required-true i18n-hccr.property.paymentAgencyBeneficiary javatype-fr.cg95.cvq.business.request.social.HccrPaymentAgencyBeneficiaryType" ><g:capdematEnumToField var="${rqt?.paymentAgencyBeneficiary}" i18nKeyPrefix="hccr.property.paymentAgencyBeneficiary" /></dd>
          
              <dt class="required condition-isPaymentAgencyBeneficiary-filled">${message(code:'hccr.property.paymentAgencyBeneficiaryNumber.label')}&nbsp;*&nbsp;:</dt><dd id="paymentAgencyBeneficiaryNumber" class="action-editField validate- required-true i18n-hccr.property.paymentAgencyBeneficiaryNumber maxLength-20" ><span>${rqt?.paymentAgencyBeneficiaryNumber}</span></dd>
          
              <dt class="required condition-isPaymentAgencyBeneficiary-filled">${message(code:'hccr.property.paymentAgencyName.label')}&nbsp;*&nbsp;:</dt><dd id="paymentAgencyName" class="action-editField validate- required-true i18n-hccr.property.paymentAgencyName maxLength-50" ><span>${rqt?.paymentAgencyName}</span></dd>
          
              <dt class="required condition-isPaymentAgencyBeneficiary-filled">${message(code:'hccr.property.paymentAgencyAddress.label')}&nbsp;*&nbsp;:</dt><dd id="paymentAgencyAddress" class="action-editField validate-address required-true i18n-hccr.property.paymentAgencyAddress" ><div><p class="additionalDeliveryInformation">${rqt?.paymentAgencyAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.paymentAgencyAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.paymentAgencyAddress?.streetNumber}</span> <span class="streetName">${rqt?.paymentAgencyAddress?.streetName}</span><g:if test="${!!rqt?.paymentAgencyAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.paymentAgencyAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.paymentAgencyAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.paymentAgencyAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.paymentAgencyAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.paymentAgencyAddress?.postalCode}</span> <span class="city">${rqt?.paymentAgencyAddress?.city}</span><p class="countryName">${rqt?.paymentAgencyAddress?.countryName}</p><g:if test="${!!rqt?.paymentAgencyAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.paymentAgencyAddress?.cityInseeCode}</span></g:if></div></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
  </div>
  <!-- data step  end -->
</div>
<!-- step end -->

<!-- step start -->
<div id="page3">
  <h2><g:message code="property.form" />
    <span><g:message code="hccr.step.occupationnalAndSchoolStatus.label" /></span>
  </h2>
  <div class="yui-g">
    
    
    <!-- column start -->
    <div class="yui-u first">
      
        
        <h3><g:message code="hccr.property.schooling.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isSchoolingEnrolment-trigger">${message(code:'hccr.property.schoolingEnrolment.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingEnrolment" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingEnrolment" ><span class="value-${rqt?.schoolingEnrolment}"><g:message code="message.${rqt?.schoolingEnrolment ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isSchoolingEnrolment-filled">${message(code:'hccr.property.schoolingSchoolName.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSchoolName" class="action-editField validate- required-true i18n-hccr.property.schoolingSchoolName maxLength-80" ><span>${rqt?.schoolingSchoolName}</span></dd>
          
              <dt class="required condition-isSchoolingEnrolment-filled">${message(code:'hccr.property.schoolingSchoolAddress.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSchoolAddress" class="action-editField validate-address required-true i18n-hccr.property.schoolingSchoolAddress" ><div><p class="additionalDeliveryInformation">${rqt?.schoolingSchoolAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.schoolingSchoolAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.schoolingSchoolAddress?.streetNumber}</span> <span class="streetName">${rqt?.schoolingSchoolAddress?.streetName}</span><g:if test="${!!rqt?.schoolingSchoolAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.schoolingSchoolAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.schoolingSchoolAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.schoolingSchoolAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.schoolingSchoolAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.schoolingSchoolAddress?.postalCode}</span> <span class="city">${rqt?.schoolingSchoolAddress?.city}</span><p class="countryName">${rqt?.schoolingSchoolAddress?.countryName}</p><g:if test="${!!rqt?.schoolingSchoolAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.schoolingSchoolAddress?.cityInseeCode}</span></g:if></div></dd>
          
              <dt class="required condition-isSentToSchool-trigger">${message(code:'hccr.property.schoolingSendToSchool.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSendToSchool" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingSendToSchool" ><span class="value-${rqt?.schoolingSendToSchool}"><g:message code="message.${rqt?.schoolingSendToSchool ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isSentToSchool-filled">${message(code:'hccr.property.schoolingAttendedGrade.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingAttendedGrade" class="action-editField validate-capdematEnum required-true i18n-hccr.property.schoolingAttendedGrade javatype-fr.cg95.cvq.business.users.SectionType maxLength-32" ><g:capdematEnumToField var="${rqt?.schoolingAttendedGrade}" i18nKeyPrefix="hccr.property.schoolingAttendedGrade" /></dd>
          
              <dt class="required condition-isSpecializedGrade-trigger">${message(code:'hccr.property.schoolingSpecializedGrade.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSpecializedGrade" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingSpecializedGrade" ><span class="value-${rqt?.schoolingSpecializedGrade}"><g:message code="message.${rqt?.schoolingSpecializedGrade ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isSpecializedGrade-filled">${message(code:'hccr.property.schoolingSpecializedGradeDetails.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSpecializedGradeDetails" class="action-editField validate- required-true i18n-hccr.property.schoolingSpecializedGradeDetails maxLength-30" ><span>${rqt?.schoolingSpecializedGradeDetails}</span></dd>
          
              <dt class="required condition-isPartTimeSchooling-trigger">${message(code:'hccr.property.schoolingSchoolingKind.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingSchoolingKind" class="action-editField validate-capdematEnum required-true i18n-hccr.property.schoolingSchoolingKind javatype-fr.cg95.cvq.business.request.social.HccrSchoolingKindType" ><g:capdematEnumToField var="${rqt?.schoolingSchoolingKind}" i18nKeyPrefix="hccr.property.schoolingSchoolingKind" /></dd>
          
              <dt class="required condition-isPartTimeSchooling-filled">${message(code:'hccr.property.schoolingTime.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingTime" class="action-editField validate- required-true i18n-hccr.property.schoolingTime" ><span>${rqt?.schoolingTime}</span></dd>
          
              <dt class="required">${message(code:'hccr.property.schoolingHomeSchooling.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingHomeSchooling" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingHomeSchooling" ><span class="value-${rqt?.schoolingHomeSchooling}"><g:message code="message.${rqt?.schoolingHomeSchooling ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required">${message(code:'hccr.property.schoolingPersonalizedSchoolingPlan.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingPersonalizedSchoolingPlan" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingPersonalizedSchoolingPlan" ><span class="value-${rqt?.schoolingPersonalizedSchoolingPlan}"><g:message code="message.${rqt?.schoolingPersonalizedSchoolingPlan ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isAccompaniedHomeSchooling-trigger">${message(code:'hccr.property.schoolingHomeSchoolingKind.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingHomeSchoolingKind" class="action-editField validate-capdematEnum required-true i18n-hccr.property.schoolingHomeSchoolingKind javatype-fr.cg95.cvq.business.request.social.HccrHomeSchoolingKindType" ><g:capdematEnumToField var="${rqt?.schoolingHomeSchoolingKind}" i18nKeyPrefix="hccr.property.schoolingHomeSchoolingKind" /></dd>
          
              <dt class="required condition-isAccompaniedHomeSchooling-filled">${message(code:'hccr.property.schoolingHomeSchoolingAccompanistLastName.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingHomeSchoolingAccompanistLastName" class="action-editField validate-lastName required-true i18n-hccr.property.schoolingHomeSchoolingAccompanistLastName maxLength-38" ><span>${rqt?.schoolingHomeSchoolingAccompanistLastName}</span></dd>
          
              <dt class="required condition-isAccompaniedHomeSchooling-filled">${message(code:'hccr.property.schoolingHomeSchoolingAccompanistFirstName.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingHomeSchoolingAccompanistFirstName" class="action-editField validate-firstName required-true i18n-hccr.property.schoolingHomeSchoolingAccompanistFirstName maxLength-38" ><span>${rqt?.schoolingHomeSchoolingAccompanistFirstName}</span></dd>
          
              <dt class="condition-isAccompaniedHomeSchooling-filled">${message(code:'hccr.property.schoolingHomeSchoolingAccompanistAddress.label')}&nbsp;:</dt><dd id="schoolingHomeSchoolingAccompanistAddress" class="action-editField validate-address i18n-hccr.property.schoolingHomeSchoolingAccompanistAddress" ><div><p class="additionalDeliveryInformation">${rqt?.schoolingHomeSchoolingAccompanistAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.schoolingHomeSchoolingAccompanistAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.schoolingHomeSchoolingAccompanistAddress?.streetNumber}</span> <span class="streetName">${rqt?.schoolingHomeSchoolingAccompanistAddress?.streetName}</span><g:if test="${!!rqt?.schoolingHomeSchoolingAccompanistAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.schoolingHomeSchoolingAccompanistAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.schoolingHomeSchoolingAccompanistAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.schoolingHomeSchoolingAccompanistAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.schoolingHomeSchoolingAccompanistAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.schoolingHomeSchoolingAccompanistAddress?.postalCode}</span> <span class="city">${rqt?.schoolingHomeSchoolingAccompanistAddress?.city}</span><p class="countryName">${rqt?.schoolingHomeSchoolingAccompanistAddress?.countryName}</p><g:if test="${!!rqt?.schoolingHomeSchoolingAccompanistAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.schoolingHomeSchoolingAccompanistAddress?.cityInseeCode}</span></g:if></div></dd>
          
              <dt class="required condition-isExtraCurricular-trigger">${message(code:'hccr.property.schoolingExtraCurricular.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingExtraCurricular" class="action-editField validate-boolean required-true i18n-hccr.property.schoolingExtraCurricular" ><span class="value-${rqt?.schoolingExtraCurricular}"><g:message code="message.${rqt?.schoolingExtraCurricular ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isExtraCurricular-filled">${message(code:'hccr.property.schoolingExtraCurricularDetails.label')}&nbsp;*&nbsp;:</dt><dd id="schoolingExtraCurricularDetails" class="action-editField validate- required-true i18n-hccr.property.schoolingExtraCurricularDetails maxLength-50" ><span>${rqt?.schoolingExtraCurricularDetails}</span></dd>
          
        </dl>
        
      
        
        <h3><g:message code="hccr.property.studies.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isHighSchool-trigger">${message(code:'hccr.property.studiesHighSchool.label')}&nbsp;*&nbsp;:</dt><dd id="studiesHighSchool" class="action-editField validate-boolean required-true i18n-hccr.property.studiesHighSchool" ><span class="value-${rqt?.studiesHighSchool}"><g:message code="message.${rqt?.studiesHighSchool ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isHighSchool-filled">${message(code:'hccr.property.studiesHighSchoolGrade.label')}&nbsp;*&nbsp;:</dt><dd id="studiesHighSchoolGrade" class="action-editField validate- required-true i18n-hccr.property.studiesHighSchoolGrade maxLength-60" ><span>${rqt?.studiesHighSchoolGrade}</span></dd>
          
              <dt class="required condition-isHighSchool-filled">${message(code:'hccr.property.studiesHighSchoolName.label')}&nbsp;*&nbsp;:</dt><dd id="studiesHighSchoolName" class="action-editField validate- required-true i18n-hccr.property.studiesHighSchoolName maxLength-60" ><span>${rqt?.studiesHighSchoolName}</span></dd>
          
              <dt class="required condition-isHighSchool-filled">${message(code:'hccr.property.studiesHighSchoolAddress.label')}&nbsp;*&nbsp;:</dt><dd id="studiesHighSchoolAddress" class="action-editField validate-address required-true i18n-hccr.property.studiesHighSchoolAddress" ><div><p class="additionalDeliveryInformation">${rqt?.studiesHighSchoolAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.studiesHighSchoolAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.studiesHighSchoolAddress?.streetNumber}</span> <span class="streetName">${rqt?.studiesHighSchoolAddress?.streetName}</span><g:if test="${!!rqt?.studiesHighSchoolAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.studiesHighSchoolAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.studiesHighSchoolAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.studiesHighSchoolAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.studiesHighSchoolAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.studiesHighSchoolAddress?.postalCode}</span> <span class="city">${rqt?.studiesHighSchoolAddress?.city}</span><p class="countryName">${rqt?.studiesHighSchoolAddress?.countryName}</p><g:if test="${!!rqt?.studiesHighSchoolAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.studiesHighSchoolAddress?.cityInseeCode}</span></g:if></div></dd>
          
              <dt class="required condition-isHighSchool-filled condition-isAssistanceUnderDisability-trigger">${message(code:'hccr.property.studiesAssistanceUnderDisability.label')}&nbsp;*&nbsp;:</dt><dd id="studiesAssistanceUnderDisability" class="action-editField validate-boolean required-true i18n-hccr.property.studiesAssistanceUnderDisability" ><span class="value-${rqt?.studiesAssistanceUnderDisability}"><g:message code="message.${rqt?.studiesAssistanceUnderDisability ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isAssistanceUnderDisability-filled">${message(code:'hccr.property.studiesAssistanceUnderDisabilityDetails.label')}&nbsp;*&nbsp;:</dt><dd id="studiesAssistanceUnderDisabilityDetails" class="action-editField validate- required-true i18n-hccr.property.studiesAssistanceUnderDisabilityDetails maxLength-60" ><span>${rqt?.studiesAssistanceUnderDisabilityDetails}</span></dd>
          
        </dl>
        
      
        
        <h3><g:message code="hccr.property.formation.label" /></h3>
        <dl class="required">
          
              <dt class="">${message(code:'hccr.property.formationStudiesLevel.label')}&nbsp;:</dt><dd id="formationStudiesLevel" class="action-editField validate- i18n-hccr.property.formationStudiesLevel maxLength-30" ><span>${rqt?.formationStudiesLevel}</span></dd>
          
              <dt class="">${message(code:'hccr.property.formationDiploma.label')}&nbsp;:</dt><dd id="formationDiploma" class="action-editField validate-textarea i18n-hccr.property.formationDiploma rows-2 maxLength-120" ><span>${rqt?.formationDiploma}</span></dd>
          
              <dt class="">${message(code:'hccr.property.formationPreviousFormation.label')}&nbsp;:</dt><dd id="formationPreviousFormation" class="action-editField validate-textarea i18n-hccr.property.formationPreviousFormation rows-3 maxLength-180" ><span>${rqt?.formationPreviousFormation}</span></dd>
          
              <dt class="">${message(code:'hccr.property.formationCurrentFormation.label')}&nbsp;:</dt><dd id="formationCurrentFormation" class="action-editField validate-textarea i18n-hccr.property.formationCurrentFormation rows-2 maxLength-120" ><span>${rqt?.formationCurrentFormation}</span></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
    <!-- column start -->
    <div class="yui-u">
      
        
        <h3><g:message code="hccr.property.professionalStatus.label" /></h3>
        <dl class="required">
          
              <dt class="required condition-isEmployed-trigger condition-isUnemployed-trigger">${message(code:'hccr.property.professionalStatusKind.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusKind" class="action-editField validate-capdematEnum required-true i18n-hccr.property.professionalStatusKind javatype-fr.cg95.cvq.business.request.social.HccrProfessionalStatusKindType" ><g:capdematEnumToField var="${rqt?.professionalStatusKind}" i18nKeyPrefix="hccr.property.professionalStatusKind" /></dd>
          
              <dt class="required">${message(code:'hccr.property.professionalStatusDate.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusDate" class="action-editField validate-date required-true i18n-hccr.property.professionalStatusDate" ><span><g:formatDate formatName="format.date" date="${rqt?.professionalStatusDate}"/></span></dd>
          
              <dt class="required condition-isEmployed-filled">${message(code:'hccr.property.professionalStatusEnvironment.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusEnvironment" class="action-editField validate-capdematEnum required-true i18n-hccr.property.professionalStatusEnvironment javatype-fr.cg95.cvq.business.request.social.HccrProfessionalStatusEnvironmentType" ><g:capdematEnumToField var="${rqt?.professionalStatusEnvironment}" i18nKeyPrefix="hccr.property.professionalStatusEnvironment" /></dd>
          
              <dt class="required condition-isEmployed-filled">${message(code:'hccr.property.professionalStatusProfession.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusProfession" class="action-editField validate- required-true i18n-hccr.property.professionalStatusProfession maxLength-60" ><span>${rqt?.professionalStatusProfession}</span></dd>
          
              <dt class="required condition-isEmployed-filled">${message(code:'hccr.property.professionalStatusEmployerName.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusEmployerName" class="action-editField validate-lastName required-true i18n-hccr.property.professionalStatusEmployerName maxLength-38" ><span>${rqt?.professionalStatusEmployerName}</span></dd>
          
              <dt class="condition-isEmployed-filled">${message(code:'hccr.property.professionalStatusAddress.label')}&nbsp;:</dt><dd id="professionalStatusAddress" class="action-editField validate-address i18n-hccr.property.professionalStatusAddress" ><div><p class="additionalDeliveryInformation">${rqt?.professionalStatusAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.professionalStatusAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.professionalStatusAddress?.streetNumber}</span> <span class="streetName">${rqt?.professionalStatusAddress?.streetName}</span><g:if test="${!!rqt?.professionalStatusAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.professionalStatusAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.professionalStatusAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.professionalStatusAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.professionalStatusAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.professionalStatusAddress?.postalCode}</span> <span class="city">${rqt?.professionalStatusAddress?.city}</span><p class="countryName">${rqt?.professionalStatusAddress?.countryName}</p><g:if test="${!!rqt?.professionalStatusAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.professionalStatusAddress?.cityInseeCode}</span></g:if></div></dd>
          
              <dt class="required condition-isUnemployed-filled condition-isRegisteredAsUnemployed-trigger">${message(code:'hccr.property.professionalStatusRegisterAsUnemployed.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusRegisterAsUnemployed" class="action-editField validate-boolean required-true i18n-hccr.property.professionalStatusRegisterAsUnemployed" ><span class="value-${rqt?.professionalStatusRegisterAsUnemployed}"><g:message code="message.${rqt?.professionalStatusRegisterAsUnemployed ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isRegisteredAsUnemployed-filled">${message(code:'hccr.property.professionalStatusRegisterAsUnemployedDate.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusRegisterAsUnemployedDate" class="action-editField validate-date required-true i18n-hccr.property.professionalStatusRegisterAsUnemployedDate" ><span><g:formatDate formatName="format.date" date="${rqt?.professionalStatusRegisterAsUnemployedDate}"/></span></dd>
          
              <dt class="required condition-isUnemployed-filled condition-isIndemnified-trigger">${message(code:'hccr.property.professionalStatusIndemnified.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusIndemnified" class="action-editField validate-boolean required-true i18n-hccr.property.professionalStatusIndemnified" ><span class="value-${rqt?.professionalStatusIndemnified}"><g:message code="message.${rqt?.professionalStatusIndemnified ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isIndemnified-filled">${message(code:'hccr.property.professionalStatusIndemnifiedDate.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusIndemnifiedDate" class="action-editField validate-date required-true i18n-hccr.property.professionalStatusIndemnifiedDate" ><span><g:formatDate formatName="format.date" date="${rqt?.professionalStatusIndemnifiedDate}"/></span></dd>
          
              <dt class="required condition-isElectiveFunction-trigger">${message(code:'hccr.property.professionalStatusElectiveFunction.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusElectiveFunction" class="action-editField validate-boolean required-true i18n-hccr.property.professionalStatusElectiveFunction" ><span class="value-${rqt?.professionalStatusElectiveFunction}"><g:message code="message.${rqt?.professionalStatusElectiveFunction ? 'yes' : 'no'}" /></span></dd>
          
              <dt class="required condition-isElectiveFunction-filled">${message(code:'hccr.property.professionalStatusElectiveFunctionDetails.label')}&nbsp;*&nbsp;:</dt><dd id="professionalStatusElectiveFunctionDetails" class="action-editField validate- required-true i18n-hccr.property.professionalStatusElectiveFunctionDetails maxLength-60" ><span>${rqt?.professionalStatusElectiveFunctionDetails}</span></dd>
          
        </dl>
        
      
    </div>
    <!-- column end -->
    
  </div>
  <!-- data step  end -->
</div>
<!-- step end -->

