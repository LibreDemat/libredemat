


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="pptwr.step.work.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="pptwr.step.reglements.label" /></em></a>
    </li>
  
    <li class="paiement ">
      <a href="#page4"><em><g:message code="request.step.paiement.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="pptwr.step.work.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <g:render template="/backofficeRequestInstruction/requestType/requester" model="['requester':requester]" />
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isCompany-trigger">${message(code:'pptwr.property.isCompany.label')} &nbsp;*&nbsp;:</dt><dd id="isCompany" class="action-editField validate-boolean required-true i18n-pptwr.property.isCompany" ><span class="value-${rqt?.isCompany}"><g:message code="${rqt?.isCompany ? 'message.yes' : rqt?.isCompany==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <h3><g:message code="pptwr.property.companyInformation.label" /></h3>
              <dl class="required condition-isCompany-filled">
                
                  <dt class="required condition-isCompany-filled">${message(code:'pptwr.property.siretNumber.label')} &nbsp;*&nbsp;:</dt><dd id="siretNumber" class="action-editField validate-regex required-true i18n-pptwr.property.siretNumber maxLength-14" regex="^[0-9]{14}$"><span >${rqt?.siretNumber}</span></dd>
                
                  <dt class="required condition-isCompany-filled">${message(code:'pptwr.property.apeCode.label')} &nbsp;*&nbsp;:</dt><dd id="apeCode" class="action-editField validate-regex required-true i18n-pptwr.property.apeCode maxLength-5" regex="^[0-9]{4}[a-zA-Z]{1}$"><span >${rqt?.apeCode}</span></dd>
                
              </dl>
              
            
              
              <dl>
                <dt class="required condition-scaffolding-trigger">${message(code:'pptwr.property.scaffolding.label')} &nbsp;*&nbsp;:</dt><dd id="scaffolding" class="action-editField validate-boolean required-true i18n-pptwr.property.scaffolding" ><span class="value-${rqt?.scaffolding}"><g:message code="${rqt?.scaffolding ? 'message.yes' : rqt?.scaffolding==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <h3><g:message code="pptwr.property.scaffoldingInformation.label" /></h3>
              <dl class="required condition-scaffolding-filled">
                
                  <dt class="required">${message(code:'pptwr.property.scaffoldingLength.label')}&nbsp;*&nbsp;:</dt><dd id="scaffoldingLength" class="action-editField validate-double required-true i18n-pptwr.property.scaffoldingLength" ><span>${formatNumber(number: rqt?.scaffoldingLength, type: 'number')}</span></dd>
                
                  <dt class="required">${message(code:'pptwr.property.scaffoldingStartDate.label')} &nbsp;*&nbsp;:</dt><dd id="scaffoldingStartDate" class="action-editField validate-calendar required-true i18n-pptwr.property.scaffoldingStartDate" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.scaffoldingStartDate}"/></span></dd>
                
                  <dt class="required">${message(code:'pptwr.property.scaffoldingEndDate.label')} &nbsp;*&nbsp;:</dt><dd id="scaffoldingEndDate" class="action-editField validate-calendar required-true i18n-pptwr.property.scaffoldingEndDate" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.scaffoldingEndDate}"/></span></dd>
                
              </dl>
              
            
              
              <dl>
                <dt class="required condition-vehicleParkingOrFloorOccupation-trigger">${message(code:'pptwr.property.vehicleParkingOrFloorOccupation.label')} &nbsp;*&nbsp;:</dt><dd id="vehicleParkingOrFloorOccupation" class="action-editField validate-boolean required-true i18n-pptwr.property.vehicleParkingOrFloorOccupation" ><span class="value-${rqt?.vehicleParkingOrFloorOccupation}"><g:message code="${rqt?.vehicleParkingOrFloorOccupation ? 'message.yes' : rqt?.vehicleParkingOrFloorOccupation==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <h3><g:message code="pptwr.property.vehicleParkingOrFloorOccupationInformation.label" /></h3>
              <dl class="required condition-vehicleParkingOrFloorOccupation-filled">
                
                  <dt class="required">${message(code:'pptwr.property.occupation.label')}&nbsp;*&nbsp;:</dt><dd id="occupation" class="action-editField validate-double required-true i18n-pptwr.property.occupation" ><span>${formatNumber(number: rqt?.occupation, type: 'number')}</span></dd>
                
                  <dt class="required">${message(code:'pptwr.property.occupationStartDate.label')} &nbsp;*&nbsp;:</dt><dd id="occupationStartDate" class="action-editField validate-calendar required-true i18n-pptwr.property.occupationStartDate" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.occupationStartDate}"/></span></dd>
                
                  <dt class="required">${message(code:'pptwr.property.occupationEndDate.label')} &nbsp;*&nbsp;:</dt><dd id="occupationEndDate" class="action-editField validate-calendar required-true i18n-pptwr.property.occupationEndDate" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.occupationEndDate}"/></span></dd>
                
                  <dt class="">${message(code:'pptwr.property.occupationOtherAddress.label')} &nbsp;:</dt><dd id="occupationOtherAddress" class="action-editField validate-textarea i18n-pptwr.property.occupationOtherAddress rows-3" ><span >${rqt?.occupationOtherAddress}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="required condition-desiredService-trigger">${message(code:'pptwr.property.desiredService.label')} &nbsp;*&nbsp;:</dt><dd id="desiredService" class="action-editField validate-localReferentialData required-true i18n-pptwr.property.desiredService data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'desiredService', 'lrEntries': lrTypes.desiredService?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.desiredService?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <h3><g:message code="pptwr.property.parkingPermitForWorkInformation.label" /></h3>
              <dl class="required condition-desiredService-filled">
                
                  <dt class="required">${message(code:'pptwr.property.siteAddress.label')} &nbsp;*&nbsp;:</dt><dd id="siteAddress" class="action-editField validate-textarea required-true i18n-pptwr.property.siteAddress rows-3" ><span >${rqt?.siteAddress}</span></dd>
                
                  <dt class="required">${message(code:'pptwr.property.workNature.label')} &nbsp;*&nbsp;:</dt><dd id="workNature" class="action-editField validate-textarea required-true i18n-pptwr.property.workNature rows-3" ><span >${rqt?.workNature}</span></dd>
                
                  <dt class="required condition-workOnBuilding-trigger">${message(code:'pptwr.property.workOnBuilding.label')} &nbsp;*&nbsp;:</dt><dd id="workOnBuilding" class="action-editField validate-boolean required-true i18n-pptwr.property.workOnBuilding" ><span class="value-${rqt?.workOnBuilding}"><g:message code="${rqt?.workOnBuilding ? 'message.yes' : rqt?.workOnBuilding==null ? '' : 'message.no'}" /></span></dd>
                
                  <dt class="required condition-workOnBuilding-filled">${message(code:'pptwr.property.constructLicenseNumber.label')} &nbsp;*&nbsp;:</dt><dd id="constructLicenseNumber" class="action-editField validate-string required-true i18n-pptwr.property.constructLicenseNumber" ><span >${rqt?.constructLicenseNumber}</span></dd>
                
                  <dt class="">${message(code:'pptwr.property.usedVehicles.label')} &nbsp;:</dt><dd id="usedVehicles" class="action-editField validate-string i18n-pptwr.property.usedVehicles" ><span >${rqt?.usedVehicles}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="pptwr.property.existingLicenseExtensionInformation.label" /></h3>
              <dl class="required condition-desiredService-unfilled">
                
                  <dt class="required">${message(code:'pptwr.property.referenceNumber.label')} &nbsp;*&nbsp;:</dt><dd id="referenceNumber" class="action-editField validate-string required-true i18n-pptwr.property.referenceNumber" ><span >${rqt?.referenceNumber}</span></dd>
                
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'pptwr.property.observations.label')} &nbsp;:</dt><dd id="observations" class="action-editField validate-textarea i18n-pptwr.property.observations rows-3" ><span >${rqt?.observations}</span></dd>
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
          <span><g:message code="pptwr.step.reglements.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'pptwr.property.acceptationReglementInterieur.label')} &nbsp;*&nbsp;:</dt><dd id="acceptationReglementInterieur" class="action-editField validate-acceptance required-true i18n-pptwr.property.acceptationReglementInterieur" ><span class="value-${rqt?.acceptationReglementInterieur}"><g:message code="message.${rqt?.acceptationReglementInterieur ? 'yes' : 'no'}" /></span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="">${message(code:'pptwr.property.observationsReglement.label')} &nbsp;:</dt><dd id="observationsReglement" class="action-editField validate-textarea i18n-pptwr.property.observationsReglement rows-3" ><span >${rqt?.observationsReglement}</span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page4">
        <h2><g:message code="property.form" />
          <span><g:message code="request.step.paiement.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="">${message(code:'pptwr.property.payment.label')}(<g:message code="system.paiement" />) &nbsp;:</dt><dd id="payment" class="${rqt.payment != null && rqt.payment.state.name == 'Validated' ? '' : 'action-editField'} validate-payment i18n-pptwr.property.payment" ><span><g:if test="${rqt.payment != null}"><g:formatNumber number="${(rqt.payment.amount.toDouble())/100}" type="number" minFractionDigits="2" maxFractionDigits="2" /></g:if><g:else><g:formatNumber number="${Double.valueOf(rqt.paymentIndicativeAmount)}" type="number" minFractionDigits="2" maxFractionDigits="2" /></g:else></span></dd>
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
