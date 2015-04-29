


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="pptrr.step.relocation.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="pptrr.step.reglements.label" /></em></a>
    </li>
  
    <li class="paiement ">
      <a href="#page3"><em><g:message code="request.step.paiement.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="pptrr.step.relocation.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <g:render template="/backofficeRequestInstruction/requestType/requester" model="['requester':requester]" />
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isCompany-trigger">${message(code:'pptrr.property.isCompany.label')} &nbsp;*&nbsp;:</dt><dd id="isCompany" class="action-editField validate-boolean required-true i18n-pptrr.property.isCompany" ><span class="value-${rqt?.isCompany}"><g:message code="${rqt?.isCompany ? 'message.yes' : rqt?.isCompany==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <h3><g:message code="pptrr.property.companyInformation.label" /></h3>
              <dl class="required condition-isCompany-filled">
                
                  <dt class="required condition-isCompany-filled">${message(code:'pptrr.property.siretNumber.label')} &nbsp;*&nbsp;:</dt><dd id="siretNumber" class="action-editField validate-regex required-true i18n-pptrr.property.siretNumber maxLength-14" regex="^[0-9]{14}$"><span>${rqt?.siretNumber}</span></dd>
                
                  <dt class="required condition-isCompany-filled">${message(code:'pptrr.property.apeCode.label')} &nbsp;*&nbsp;:</dt><dd id="apeCode" class="action-editField validate-regex required-true i18n-pptrr.property.apeCode maxLength-5" regex="^[0-9]{4}[a-zA-Z]{1}$"><span>${rqt?.apeCode}</span></dd>
                
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.desiredService.label')} &nbsp;*&nbsp;:</dt><dd id="desiredService" class="action-editField validate-localReferentialData required-true i18n-pptrr.property.desiredService data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'desiredService', 'lrEntries': lrTypes.desiredService?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.desiredService?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <h3><g:message code="pptrr.property.equipmentUsed.label" /></h3>
              <dl class="required">
                
                  <dt class="required">${message(code:'pptrr.property.vehicleType.label')} &nbsp;*&nbsp;:</dt><dd id="vehicleType" class="action-editField validate-string required-true i18n-pptrr.property.vehicleType" ><span>${rqt?.vehicleType}</span></dd>
                
                  <dt class="required">${message(code:'pptrr.property.longeur.label')} &nbsp;*&nbsp;:</dt><dd id="longeur" class="action-editField validate-positiveInteger required-true i18n-pptrr.property.longeur" ><span>${rqt?.longeur}</span></dd>
                
                  <dt class="">${message(code:'pptrr.property.immatriculation.label')} &nbsp;:</dt><dd id="immatriculation" class="action-editField validate-regex i18n-pptrr.property.immatriculation maxLength-255" regex="^[\w\W]{0,255}$"><span>${rqt?.immatriculation}</span></dd>
                
                  <dt class="required">${message(code:'pptrr.property.furnitureLifting.label')} &nbsp;*&nbsp;:</dt><dd id="furnitureLifting" class="action-editField validate-boolean required-true i18n-pptrr.property.furnitureLifting" ><span class="value-${rqt?.furnitureLifting}"><g:message code="${rqt?.furnitureLifting ? 'message.yes' : rqt?.furnitureLifting==null ? '' : 'message.no'}" /></span></dd>
                
                  <dt class="">${message(code:'pptrr.property.other.label')} &nbsp;:</dt><dd id="other" class="action-editField validate-string i18n-pptrr.property.other" ><span>${rqt?.other}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.requesterAddress.label')} &nbsp;*&nbsp;:</dt><dd id="requesterAddress" class="action-editField validate-textarea required-true i18n-pptrr.property.requesterAddress rows-3" ><span>${rqt?.requesterAddress}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.periodeStart.label')} &nbsp;*&nbsp;:</dt><dd id="periodeStart" class="action-editField validate-calendar required-true i18n-pptrr.property.periodeStart" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.periodeStart}"/></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.periodeEnd.label')} &nbsp;*&nbsp;:</dt><dd id="periodeEnd" class="action-editField validate-calendar required-true i18n-pptrr.property.periodeEnd" ><span><g:formatDate format="dd/MM/yyyy" date="${rqt?.periodeEnd}"/></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.heureStart.label')} &nbsp;*&nbsp;:</dt><dd id="heureStart" class="action-editField validate-libredematEnum required-true i18n-pptrr.property.heureStart javatype-org.libredemat.business.request.permit.HeuresType" ><g:libredematEnumToField var="${rqt?.heureStart}" i18nKeyPrefix="pptrr.property.heureStart" /></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.heureEnd.label')} &nbsp;*&nbsp;:</dt><dd id="heureEnd" class="action-editField validate-libredematEnum required-true i18n-pptrr.property.heureEnd javatype-org.libredemat.business.request.permit.HeuresType" ><g:libredematEnumToField var="${rqt?.heureEnd}" i18nKeyPrefix="pptrr.property.heureEnd" /></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'pptrr.property.observations.label')} &nbsp;:</dt><dd id="observations" class="action-editField validate-textarea i18n-pptrr.property.observations rows-3" ><span>${rqt?.observations}</span></dd>
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
          <span><g:message code="pptrr.step.reglements.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.acceptationReglementInterieur.label')} &nbsp;*&nbsp;:</dt><dd id="acceptationReglementInterieur" class="action-editField validate-acceptance required-true i18n-pptrr.property.acceptationReglementInterieur" ><span class="value-${rqt?.acceptationReglementInterieur}"><g:message code="message.${rqt?.acceptationReglementInterieur ? 'yes' : 'no'}" /></span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="">${message(code:'pptrr.property.observationsReglement.label')} &nbsp;:</dt><dd id="observationsReglement" class="action-editField validate-textarea i18n-pptrr.property.observationsReglement rows-3" ><span>${rqt?.observationsReglement}</span></dd>
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
          <span><g:message code="request.step.paiement.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="">${message(code:'pptrr.property.payment.label')}(<g:message code="system.paiement" />) &nbsp;:</dt><dd id="payment" class="${rqt.payment != null && rqt.payment.state.name == 'Validated' ? '' : 'action-editField'} validate-payment i18n-pptrr.property.payment" ><span><g:if test="${rqt.payment != null}"><g:formatNumber number="${(rqt.payment.amount.toDouble())/100}" type="number" minFractionDigits="2" maxFractionDigits="2" /></g:if><g:else><g:formatNumber number="${rqt.paymentIndicativeAmount}" type="number" minFractionDigits="2" maxFractionDigits="2" />${rqt.paymentIndicativeAmount}</g:else></span></dd>
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
