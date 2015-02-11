


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
                <dt class="required">${message(code:'pptrr.property.requesterAddress.label')} &nbsp;*&nbsp;:</dt><dd id="requesterAddress" class="action-editField validate-address required-true i18n-pptrr.property.requesterAddress" ><div><p class="additionalDeliveryInformation">${rqt?.requesterAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.requesterAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.requesterAddress?.streetNumber}</span> <span class="streetName">${rqt?.requesterAddress?.streetName}</span><g:if test="${!!rqt?.requesterAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.requesterAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.requesterAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.requesterAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.requesterAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.requesterAddress?.postalCode}</span> <span class="city">${rqt?.requesterAddress?.city}</span><p class="countryName">${rqt?.requesterAddress?.countryName}</p><g:if test="${!!rqt?.requesterAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.requesterAddress?.cityInseeCode}</span></g:if></div></dd>
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
                <dt class="required">${message(code:'pptrr.property.performChoice.label')} &nbsp;*&nbsp;:</dt><dd id="performChoice" class="action-editField validate-localReferentialData required-true i18n-pptrr.property.performChoice data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'performChoice', 'lrEntries': lrTypes.performChoice?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.performChoice?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.equipmentUsed.label')} &nbsp;*&nbsp;:</dt><dd id="equipmentUsed" class="action-editField validate-localReferentialData required-true i18n-pptrr.property.equipmentUsed data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'equipmentUsed', 'lrEntries': lrTypes.equipmentUsed?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.equipmentUsed?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.marque.label')} &nbsp;*&nbsp;:</dt><dd id="marque" class="action-editField validate-regex required-true i18n-pptrr.property.marque maxLength-255" regex="^[\w\W]{0,255}$"><span>${rqt?.marque}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.immatriculation.label')} &nbsp;*&nbsp;:</dt><dd id="immatriculation" class="action-editField validate-regex required-true i18n-pptrr.property.immatriculation maxLength-255" regex="^[\w\W]{0,255}$"><span>${rqt?.immatriculation}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.longeur.label')} &nbsp;*&nbsp;:</dt><dd id="longeur" class="action-editField validate-positiveInteger required-true i18n-pptrr.property.longeur" ><span>${rqt?.longeur}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.largeur.label')} &nbsp;*&nbsp;:</dt><dd id="largeur" class="action-editField validate-positiveInteger required-true i18n-pptrr.property.largeur" ><span>${rqt?.largeur}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.tonnage.label')} &nbsp;*&nbsp;:</dt><dd id="tonnage" class="action-editField validate-positiveInteger required-true i18n-pptrr.property.tonnage" ><span>${rqt?.tonnage}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pptrr.property.volume.label')} &nbsp;*&nbsp;:</dt><dd id="volume" class="action-editField validate-positiveInteger required-true i18n-pptrr.property.volume" ><span>${rqt?.volume}</span></dd>
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
                <dt class="">${message(code:'pptrr.property.payment.label')}(<g:message code="system.paiement" />) &nbsp;:</dt><dd id="payment" class="${rqt.payment != null && rqt.payment.state.name == 'Validated' ? '' : 'action-editField'} validate-payment i18n-pptrr.property.payment" ><span><g:if test="${rqt.payment != null}"><g:formatNumber number="${(rqt.payment.amount.toDouble())/100}" type="number" maxFractionDigits="2" /></g:if><g:else><g:message code="payment.submit.nopaiement" /></g:else></span></dd>
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
