


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="dhr.step.subject.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="dhr.step.familyReferent.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page2"><em><g:message code="dhr.step.spouse.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page3"><em><g:message code="dhr.step.dwelling.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page4"><em><g:message code="dhr.step.resources.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page5"><em><g:message code="dhr.step.taxes.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="dhr.step.subject.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrRequester.label" /></h3>
              <dl class="required">
                
                  <dt class="required"><g:message code="dhr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
                
                  <dt class="required">${message(code:'dhr.property.dhrRequesterBirthDate.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterBirthDate" class="action-editField validate-date required-true i18n-dhr.property.dhrRequesterBirthDate" ><span><g:formatDate formatName="format.date" date="${rqt?.dhrRequesterBirthDate}"/></span></dd>
                
                  <dt class="required">${message(code:'dhr.property.dhrRequesterBirthPlace.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterBirthPlace" class="action-editField validate-string required-true i18n-dhr.property.dhrRequesterBirthPlace" ><span>${rqt?.dhrRequesterBirthPlace}</span></dd>
                
                  <dt class="required condition-isNonEuropean-trigger">${message(code:'dhr.property.dhrRequesterNationality.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterNationality" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrRequesterNationality javatype-fr.cg95.cvq.business.users.NationalityType maxLength-32" ><g:capdematEnumToField var="${rqt?.dhrRequesterNationality}" i18nKeyPrefix="dhr.property.dhrRequesterNationality" /></dd>
                
                  <dt class="required condition-isNonEuropean-filled">${message(code:'dhr.property.dhrRequesterFranceArrivalDate.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterFranceArrivalDate" class="action-editField validate-date required-true i18n-dhr.property.dhrRequesterFranceArrivalDate" ><span><g:formatDate formatName="format.date" date="${rqt?.dhrRequesterFranceArrivalDate}"/></span></dd>
                
                  <dt class="required condition-isNonEuropean-filled">${message(code:'dhr.property.dhrRequesterIsFrenchResident.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterIsFrenchResident" class="action-editField validate-boolean required-true i18n-dhr.property.dhrRequesterIsFrenchResident" ><span class="value-${rqt?.dhrRequesterIsFrenchResident}"><g:message code="${rqt?.dhrRequesterIsFrenchResident ? 'message.yes' : rqt?.dhrRequesterIsFrenchResident==null ? '' : 'message.no'}" /></span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="dhr.property.dhrRequesterPensionPlan.label" /></h3>
              <dl class="required">
                
                  <dt class="required condition-isOtherPensionPlan-trigger">${message(code:'dhr.property.dhrPrincipalPensionPlan.label')}&nbsp;*&nbsp;:</dt><dd id="dhrPrincipalPensionPlan" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrPrincipalPensionPlan javatype-fr.cg95.cvq.business.request.social.DhrPrincipalPensionPlanType" ><g:capdematEnumToField var="${rqt?.dhrPrincipalPensionPlan}" i18nKeyPrefix="dhr.property.dhrPrincipalPensionPlan" /></dd>
                
                  <dt class="required condition-isOtherPensionPlan-filled">${message(code:'dhr.property.dhrPensionPlanDetail.label')}&nbsp;*&nbsp;:</dt><dd id="dhrPensionPlanDetail" class="action-editField validate-string required-true i18n-dhr.property.dhrPensionPlanDetail" ><span>${rqt?.dhrPensionPlanDetail}</span></dd>
                
                  <dt class="required">${message(code:'dhr.property.dhrComplementaryPensionPlan.label')}&nbsp;*&nbsp;:</dt><dd id="dhrComplementaryPensionPlan" class="action-editField validate-string required-true i18n-dhr.property.dhrComplementaryPensionPlan" ><span>${rqt?.dhrComplementaryPensionPlan}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <h3><g:message code="dhr.property.dhrRequesterGuardian.label" /></h3>
              <dl class="required">
                
                  <dt class="required condition-haveGuardian-trigger">${message(code:'dhr.property.dhrRequesterHaveGuardian.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequesterHaveGuardian" class="action-editField validate-boolean required-true i18n-dhr.property.dhrRequesterHaveGuardian" ><span class="value-${rqt?.dhrRequesterHaveGuardian}"><g:message code="${rqt?.dhrRequesterHaveGuardian ? 'message.yes' : rqt?.dhrRequesterHaveGuardian==null ? '' : 'message.no'}" /></span></dd>
                
                  <dt class="required condition-haveGuardian-filled">${message(code:'dhr.property.dhrGuardianMeasure.label')}&nbsp;*&nbsp;:</dt><dd id="dhrGuardianMeasure" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrGuardianMeasure javatype-fr.cg95.cvq.business.request.social.DhrGuardianMeasureType" ><g:capdematEnumToField var="${rqt?.dhrGuardianMeasure}" i18nKeyPrefix="dhr.property.dhrGuardianMeasure" /></dd>
                
                  <dt class="required condition-haveGuardian-filled">${message(code:'dhr.property.dhrGuardianName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrGuardianName" class="action-editField validate-lastName required-true i18n-dhr.property.dhrGuardianName maxLength-38" ><span>${rqt?.dhrGuardianName}</span></dd>
                
                  <dt class="required condition-haveGuardian-filled">${message(code:'dhr.property.dhrGuardianAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dhrGuardianAddress" class="action-editField validate-address required-true i18n-dhr.property.dhrGuardianAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dhrGuardianAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dhrGuardianAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dhrGuardianAddress?.streetNumber}</span> <span class="streetName">${rqt?.dhrGuardianAddress?.streetName}</span><g:if test="${!!rqt?.dhrGuardianAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dhrGuardianAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dhrGuardianAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dhrGuardianAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dhrGuardianAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dhrGuardianAddress?.postalCode}</span> <span class="city">${rqt?.dhrGuardianAddress?.city}</span><p class="countryName">${rqt?.dhrGuardianAddress?.countryName}</p><g:if test="${!!rqt?.dhrGuardianAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dhrGuardianAddress?.cityInseeCode}</span></g:if></div></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page1">
        <h2><g:message code="property.form" />
          <span><g:message code="dhr.step.familyReferent.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrFamilyReferent.label" /></h3>
              <dl class="required">
                
                  <dt class="required condition-haveFamilyReferent-trigger">${message(code:'dhr.property.dhrHaveFamilyReferent.label')}&nbsp;*&nbsp;:</dt><dd id="dhrHaveFamilyReferent" class="action-editField validate-boolean required-true i18n-dhr.property.dhrHaveFamilyReferent" ><span class="value-${rqt?.dhrHaveFamilyReferent}"><g:message code="${rqt?.dhrHaveFamilyReferent ? 'message.yes' : rqt?.dhrHaveFamilyReferent==null ? '' : 'message.no'}" /></span></dd>
                
                  <dt class="required condition-haveFamilyReferent-filled">${message(code:'dhr.property.dhrReferentName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrReferentName" class="action-editField validate-lastName required-true i18n-dhr.property.dhrReferentName maxLength-38" ><span>${rqt?.dhrReferentName}</span></dd>
                
                  <dt class="required condition-haveFamilyReferent-filled">${message(code:'dhr.property.dhrReferentFirstName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrReferentFirstName" class="action-editField validate-firstName required-true i18n-dhr.property.dhrReferentFirstName maxLength-38" ><span>${rqt?.dhrReferentFirstName}</span></dd>
                
                  <dt class="required condition-haveFamilyReferent-filled">${message(code:'dhr.property.dhrReferentAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dhrReferentAddress" class="action-editField validate-address required-true i18n-dhr.property.dhrReferentAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dhrReferentAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dhrReferentAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dhrReferentAddress?.streetNumber}</span> <span class="streetName">${rqt?.dhrReferentAddress?.streetName}</span><g:if test="${!!rqt?.dhrReferentAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dhrReferentAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dhrReferentAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dhrReferentAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dhrReferentAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dhrReferentAddress?.postalCode}</span> <span class="city">${rqt?.dhrReferentAddress?.city}</span><p class="countryName">${rqt?.dhrReferentAddress?.countryName}</p><g:if test="${!!rqt?.dhrReferentAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dhrReferentAddress?.cityInseeCode}</span></g:if></div></dd>
                
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
          <span><g:message code="dhr.step.spouse.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrSpouse.label" /></h3>
              <dl class="required">
                
                  <dt class="required condition-isCoupleRequest-trigger">${message(code:'dhr.property.dhrRequestKind.label')}&nbsp;*&nbsp;:</dt><dd id="dhrRequestKind" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrRequestKind javatype-fr.cg95.cvq.business.request.social.DhrRequestKindType" ><g:capdematEnumToField var="${rqt?.dhrRequestKind}" i18nKeyPrefix="dhr.property.dhrRequestKind" /></dd>
                
                  <dt class="required condition-isCoupleRequest-filled condition-isSpouseMadam-trigger">${message(code:'dhr.property.dhrSpouseTitle.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseTitle" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrSpouseTitle javatype-fr.cg95.cvq.business.users.TitleType" ><g:capdematEnumToField var="${rqt?.dhrSpouseTitle}" i18nKeyPrefix="dhr.property.dhrSpouseTitle" /></dd>
                
                  <dt class="required condition-isCoupleRequest-filled">${message(code:'dhr.property.dhrSpouseFamilyStatus.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseFamilyStatus" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrSpouseFamilyStatus javatype-fr.cg95.cvq.business.users.FamilyStatusType" ><g:capdematEnumToField var="${rqt?.dhrSpouseFamilyStatus}" i18nKeyPrefix="dhr.property.dhrSpouseFamilyStatus" /></dd>
                
                  <dt class="required condition-isCoupleRequest-filled">${message(code:'dhr.property.dhrSpouseName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseName" class="action-editField validate-lastName required-true i18n-dhr.property.dhrSpouseName maxLength-38" ><span>${rqt?.dhrSpouseName}</span></dd>
                
                  <dt class="required condition-isCoupleRequest-filled">${message(code:'dhr.property.dhrSpouseFirstName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseFirstName" class="action-editField validate-firstName required-true i18n-dhr.property.dhrSpouseFirstName maxLength-38" ><span>${rqt?.dhrSpouseFirstName}</span></dd>
                
                  <dt class="required condition-isSpouseMadam-filled">${message(code:'dhr.property.dhrSpouseMaidenName.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseMaidenName" class="action-editField validate-lastName required-true i18n-dhr.property.dhrSpouseMaidenName maxLength-38" ><span>${rqt?.dhrSpouseMaidenName}</span></dd>
                
                  <dt class="required condition-isCoupleRequest-filled">${message(code:'dhr.property.dhrSpouseBirthDate.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseBirthDate" class="action-editField validate-date required-true i18n-dhr.property.dhrSpouseBirthDate" ><span><g:formatDate formatName="format.date" date="${rqt?.dhrSpouseBirthDate}"/></span></dd>
                
                  <dt class="required condition-isCoupleRequest-filled">${message(code:'dhr.property.dhrSpouseBirthPlace.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseBirthPlace" class="action-editField validate-string required-true i18n-dhr.property.dhrSpouseBirthPlace" ><span>${rqt?.dhrSpouseBirthPlace}</span></dd>
                
                  <dt class="required condition-isCoupleRequest-filled condition-isSpouseNonEuropean-trigger">${message(code:'dhr.property.dhrSpouseNationality.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseNationality" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrSpouseNationality javatype-fr.cg95.cvq.business.users.NationalityType maxLength-32" ><g:capdematEnumToField var="${rqt?.dhrSpouseNationality}" i18nKeyPrefix="dhr.property.dhrSpouseNationality" /></dd>
                
                  <dt class="required condition-isSpouseNonEuropean-filled">${message(code:'dhr.property.dhrSpouseFranceArrivalDate.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseFranceArrivalDate" class="action-editField validate-date required-true i18n-dhr.property.dhrSpouseFranceArrivalDate" ><span><g:formatDate formatName="format.date" date="${rqt?.dhrSpouseFranceArrivalDate}"/></span></dd>
                
                  <dt class="required condition-isSpouseNonEuropean-filled">${message(code:'dhr.property.dhrSpouseIsFrenchResident.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseIsFrenchResident" class="action-editField validate-boolean required-true i18n-dhr.property.dhrSpouseIsFrenchResident" ><span class="value-${rqt?.dhrSpouseIsFrenchResident}"><g:message code="${rqt?.dhrSpouseIsFrenchResident ? 'message.yes' : rqt?.dhrSpouseIsFrenchResident==null ? '' : 'message.no'}" /></span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="dhr.property.dhrSpouseStatus.label" /></h3>
              <dl class="required condition-isCoupleRequest-filled">
                
                  <dt class="required condition-isSpouseRetired-trigger">${message(code:'dhr.property.dhrIsSpouseRetired.label')}&nbsp;*&nbsp;:</dt><dd id="dhrIsSpouseRetired" class="action-editField validate-boolean required-true i18n-dhr.property.dhrIsSpouseRetired" ><span class="value-${rqt?.dhrIsSpouseRetired}"><g:message code="${rqt?.dhrIsSpouseRetired ? 'message.yes' : rqt?.dhrIsSpouseRetired==null ? '' : 'message.no'}" /></span></dd>
                
                  <dt class="required condition-isSpouseRetired-filled condition-isSpouseOtherPensionPlan-trigger">${message(code:'dhr.property.dhrSpousePrincipalPensionPlan.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpousePrincipalPensionPlan" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrSpousePrincipalPensionPlan javatype-fr.cg95.cvq.business.request.social.DhrPrincipalPensionPlanType" ><g:capdematEnumToField var="${rqt?.dhrSpousePrincipalPensionPlan}" i18nKeyPrefix="dhr.property.dhrSpousePrincipalPensionPlan" /></dd>
                
                  <dt class="required condition-isSpouseOtherPensionPlan-filled">${message(code:'dhr.property.dhrSpousePensionPlanDetail.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpousePensionPlanDetail" class="action-editField validate-string required-true i18n-dhr.property.dhrSpousePensionPlanDetail" ><span>${rqt?.dhrSpousePensionPlanDetail}</span></dd>
                
                  <dt class="required condition-isSpouseRetired-filled">${message(code:'dhr.property.dhrSpouseComplementaryPensionPlan.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseComplementaryPensionPlan" class="action-editField validate-string required-true i18n-dhr.property.dhrSpouseComplementaryPensionPlan" ><span>${rqt?.dhrSpouseComplementaryPensionPlan}</span></dd>
                
                  <dt class="required condition-isSpouseRetired-unfilled">${message(code:'dhr.property.dhrSpouseProfession.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseProfession" class="action-editField validate-string required-true i18n-dhr.property.dhrSpouseProfession" ><span>${rqt?.dhrSpouseProfession}</span></dd>
                
                  <dt class="required condition-isSpouseRetired-unfilled">${message(code:'dhr.property.dhrSpouseEmployer.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseEmployer" class="action-editField validate-string required-true i18n-dhr.property.dhrSpouseEmployer" ><span>${rqt?.dhrSpouseEmployer}</span></dd>
                
                  <dt class="required condition-isSpouseRetired-unfilled">${message(code:'dhr.property.dhrSpouseAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dhrSpouseAddress" class="action-editField validate-address required-true i18n-dhr.property.dhrSpouseAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dhrSpouseAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dhrSpouseAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dhrSpouseAddress?.streetNumber}</span> <span class="streetName">${rqt?.dhrSpouseAddress?.streetName}</span><g:if test="${!!rqt?.dhrSpouseAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dhrSpouseAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dhrSpouseAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dhrSpouseAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dhrSpouseAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dhrSpouseAddress?.postalCode}</span> <span class="city">${rqt?.dhrSpouseAddress?.city}</span><p class="countryName">${rqt?.dhrSpouseAddress?.countryName}</p><g:if test="${!!rqt?.dhrSpouseAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dhrSpouseAddress?.cityInseeCode}</span></g:if></div></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <h3><g:message code="dhr.property.dhrSpouseIncomes.label" /></h3>
              <dl class="condition-isCoupleRequest-filled">
                
                  <dt class="">${message(code:'dhr.property.pensions.label')}&nbsp;:</dt><dd id="pensions" class="action-editField validate-positiveInteger i18n-dhr.property.pensions" ><span>${rqt?.pensions}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrAllowances.label')}&nbsp;:</dt><dd id="dhrAllowances" class="action-editField validate-positiveInteger i18n-dhr.property.dhrAllowances" ><span>${rqt?.dhrAllowances}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrFurnitureInvestmentIncome.label')}&nbsp;:</dt><dd id="dhrFurnitureInvestmentIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrFurnitureInvestmentIncome" ><span>${rqt?.dhrFurnitureInvestmentIncome}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrRealEstateInvestmentIncome.label')}&nbsp;:</dt><dd id="dhrRealEstateInvestmentIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrRealEstateInvestmentIncome" ><span>${rqt?.dhrRealEstateInvestmentIncome}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrNetIncome.label')}&nbsp;:</dt><dd id="dhrNetIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrNetIncome" ><span>${rqt?.dhrNetIncome}</span></dd>
                
                  <dt class="required">${message(code:'dhr.property.dhrIncomesAnnualTotal.label')}&nbsp;*&nbsp;:</dt><dd id="dhrIncomesAnnualTotal" class="action-editField validate-positiveInteger required-true i18n-dhr.property.dhrIncomesAnnualTotal" ><span>${rqt?.dhrIncomesAnnualTotal}</span></dd>
                
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
          <span><g:message code="dhr.step.dwelling.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrCurrentDwelling.label" /></h3>
              <dl class="required">
                
                  <dt class="required">${message(code:'dhr.property.dhrCurrentDwellingAddress.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingAddress" class="action-editField validate-address required-true i18n-dhr.property.dhrCurrentDwellingAddress" ><div><p class="additionalDeliveryInformation">${rqt?.dhrCurrentDwellingAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.dhrCurrentDwellingAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.dhrCurrentDwellingAddress?.streetNumber}</span> <span class="streetName">${rqt?.dhrCurrentDwellingAddress?.streetName}</span><g:if test="${!!rqt?.dhrCurrentDwellingAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.dhrCurrentDwellingAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.dhrCurrentDwellingAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.dhrCurrentDwellingAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.dhrCurrentDwellingAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.dhrCurrentDwellingAddress?.postalCode}</span> <span class="city">${rqt?.dhrCurrentDwellingAddress?.city}</span><p class="countryName">${rqt?.dhrCurrentDwellingAddress?.countryName}</p><g:if test="${!!rqt?.dhrCurrentDwellingAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.dhrCurrentDwellingAddress?.cityInseeCode}</span></g:if></div></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrCurrentDwellingPhone.label')}&nbsp;:</dt><dd id="dhrCurrentDwellingPhone" class="action-editField validate-phone i18n-dhr.property.dhrCurrentDwellingPhone maxLength-10" ><span>${rqt?.dhrCurrentDwellingPhone}</span></dd>
                
                  <dt class="required condition-isCurrentDwellingPlaceOfResidence-trigger">${message(code:'dhr.property.dhrCurrentDwellingKind.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingKind" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrCurrentDwellingKind javatype-fr.cg95.cvq.business.request.social.DhrDwellingKindType" ><g:capdematEnumToField var="${rqt?.dhrCurrentDwellingKind}" i18nKeyPrefix="dhr.property.dhrCurrentDwellingKind" /></dd>
                
                  <dt class="required condition-isCurrentDwellingPlaceOfResidence-filled">${message(code:'dhr.property.dhrCurrentDwellingArrivalDate.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingArrivalDate" class="action-editField validate-date required-true i18n-dhr.property.dhrCurrentDwellingArrivalDate" ><span><g:formatDate formatName="format.date" date="${rqt?.dhrCurrentDwellingArrivalDate}"/></span></dd>
                
                  <dt class="required condition-isCurrentDwellingPlaceOfResidence-filled">${message(code:'dhr.property.dhrCurrentDwellingStatus.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingStatus" class="action-editField validate-capdematEnum required-true i18n-dhr.property.dhrCurrentDwellingStatus javatype-fr.cg95.cvq.business.request.social.DhrDwellingStatusType" ><g:capdematEnumToField var="${rqt?.dhrCurrentDwellingStatus}" i18nKeyPrefix="dhr.property.dhrCurrentDwellingStatus" /></dd>
                
                  <dt class="required condition-isCurrentDwellingPlaceOfResidence-filled">${message(code:'dhr.property.dhrCurrentDwellingNumberOfRoom.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingNumberOfRoom" class="action-editField validate-dhrDwellingNumberOfRoom required-true i18n-dhr.property.dhrCurrentDwellingNumberOfRoom" ><span>${rqt?.dhrCurrentDwellingNumberOfRoom}</span></dd>
                
                  <dt class="required condition-isCurrentDwellingPlaceOfResidence-filled">${message(code:'dhr.property.dhrCurrentDwellingNetArea.label')}&nbsp;*&nbsp;:</dt><dd id="dhrCurrentDwellingNetArea" class="action-editField validate-regex required-true i18n-dhr.property.dhrCurrentDwellingNetArea" regex="^[1-9]$|^[1-9][0-9]$|^[1-4][0-9][0-9]$|^500$"><span>${rqt?.dhrCurrentDwellingNetArea}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <div id="widget-dhrPreviousDwelling" class="required">
                <g:render template="/backofficeRequestInstruction/requestType/domesticHelpRequest/dhrPreviousDwelling" model="['rqt':rqt]" />
              </div>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page4">
        <h2><g:message code="property.form" />
          <span><g:message code="dhr.step.resources.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrRequesterIncomes.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'dhr.property.pensions.label')}&nbsp;:</dt><dd id="pensions" class="action-editField validate-positiveInteger i18n-dhr.property.pensions" ><span>${rqt?.pensions}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrAllowances.label')}&nbsp;:</dt><dd id="dhrAllowances" class="action-editField validate-positiveInteger i18n-dhr.property.dhrAllowances" ><span>${rqt?.dhrAllowances}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrFurnitureInvestmentIncome.label')}&nbsp;:</dt><dd id="dhrFurnitureInvestmentIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrFurnitureInvestmentIncome" ><span>${rqt?.dhrFurnitureInvestmentIncome}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrRealEstateInvestmentIncome.label')}&nbsp;:</dt><dd id="dhrRealEstateInvestmentIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrRealEstateInvestmentIncome" ><span>${rqt?.dhrRealEstateInvestmentIncome}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.dhrNetIncome.label')}&nbsp;:</dt><dd id="dhrNetIncome" class="action-editField validate-positiveInteger i18n-dhr.property.dhrNetIncome" ><span>${rqt?.dhrNetIncome}</span></dd>
                
                  <dt class="required">${message(code:'dhr.property.dhrIncomesAnnualTotal.label')}&nbsp;*&nbsp;:</dt><dd id="dhrIncomesAnnualTotal" class="action-editField validate-positiveInteger required-true i18n-dhr.property.dhrIncomesAnnualTotal" ><span>${rqt?.dhrIncomesAnnualTotal}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <div id="widget-dhrRealAsset" class="required">
                <g:render template="/backofficeRequestInstruction/requestType/domesticHelpRequest/dhrRealAsset" model="['rqt':rqt]" />
              </div>
              
            
              
              <div id="widget-dhrNotRealAsset" class="required">
                <g:render template="/backofficeRequestInstruction/requestType/domesticHelpRequest/dhrNotRealAsset" model="['rqt':rqt]" />
              </div>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page5">
        <h2><g:message code="property.form" />
          <span><g:message code="dhr.step.taxes.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <h3><g:message code="dhr.property.dhrTaxesAmount.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'dhr.property.dhrIncomeTax.label')}&nbsp;:</dt><dd id="dhrIncomeTax" class="action-editField validate-positiveInteger i18n-dhr.property.dhrIncomeTax" ><span>${rqt?.dhrIncomeTax}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.localRate.label')}&nbsp;:</dt><dd id="localRate" class="action-editField validate-positiveInteger i18n-dhr.property.localRate" ><span>${rqt?.localRate}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.propertyTaxes.label')}&nbsp;:</dt><dd id="propertyTaxes" class="action-editField validate-positiveInteger i18n-dhr.property.propertyTaxes" ><span>${rqt?.propertyTaxes}</span></dd>
                
                  <dt class="">${message(code:'dhr.property.professionalTaxes.label')}&nbsp;:</dt><dd id="professionalTaxes" class="action-editField validate-positiveInteger i18n-dhr.property.professionalTaxes" ><span>${rqt?.professionalTaxes}</span></dd>
                
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
      
    
    
  </div>
  
</div>
