


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="ancr.step.address.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="ancr.step.cadastre.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="ancr.step.address.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="required condition-isSameAddress-trigger">${message(code:'ancr.property.isAccountAddress.label')}&nbsp;*&nbsp;:</dt><dd id="isAccountAddress" class="action-editField validate-boolean required-true i18n-ancr.property.isAccountAddress" ><span class="value-${rqt?.isAccountAddress}"><g:message code="${rqt?.isAccountAddress ? 'message.yes' : rqt?.isAccountAddress==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isSameAddress-unfilled">${message(code:'ancr.property.otherAddress.label')}&nbsp;*&nbsp;:</dt><dd id="otherAddress" class="action-editField validate-address required-true i18n-ancr.property.otherAddress" ><div><p class="additionalDeliveryInformation">${rqt?.otherAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.otherAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.otherAddress?.streetNumber}</span> <span class="streetName">${rqt?.otherAddress?.streetName}</span><g:if test="${!!rqt?.otherAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.otherAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.otherAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.otherAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.otherAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.otherAddress?.postalCode}</span> <span class="city">${rqt?.otherAddress?.city}</span><p class="countryName">${rqt?.otherAddress?.countryName}</p><g:if test="${!!rqt?.otherAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.otherAddress?.cityInseeCode}</span></g:if></div></dd>
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
          <span><g:message code="ancr.step.cadastre.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required condition-isOwner-trigger">${message(code:'ancr.property.requesterQuality.label')}&nbsp;*&nbsp;:</dt><dd id="requesterQuality" class="action-editField validate-capdematEnum required-true i18n-ancr.property.requesterQuality javatype-fr.cg95.cvq.business.request.urbanism.AncrRequesterQualityType" ><g:capdematEnumToField var="${rqt?.requesterQuality}" i18nKeyPrefix="ancr.property.requesterQuality" /></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isOwner-unfilled">${message(code:'ancr.property.ownerLastName.label')}&nbsp;*&nbsp;:</dt><dd id="ownerLastName" class="action-editField validate-lastName required-true i18n-ancr.property.ownerLastName maxLength-38" ><span>${rqt?.ownerLastName}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isOwner-unfilled">${message(code:'ancr.property.ownerFirstNames.label')}&nbsp;*&nbsp;:</dt><dd id="ownerFirstNames" class="action-editField validate-string required-true i18n-ancr.property.ownerFirstNames" ><span>${rqt?.ownerFirstNames}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isOwner-unfilled">${message(code:'ancr.property.ownerAddress.label')}&nbsp;*&nbsp;:</dt><dd id="ownerAddress" class="action-editField validate-address required-true i18n-ancr.property.ownerAddress" ><div><p class="additionalDeliveryInformation">${rqt?.ownerAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.ownerAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.ownerAddress?.streetNumber}</span> <span class="streetName">${rqt?.ownerAddress?.streetName}</span><g:if test="${!!rqt?.ownerAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.ownerAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.ownerAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.ownerAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.ownerAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.ownerAddress?.postalCode}</span> <span class="city">${rqt?.ownerAddress?.city}</span><p class="countryName">${rqt?.ownerAddress?.countryName}</p><g:if test="${!!rqt?.ownerAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.ownerAddress?.cityInseeCode}</span></g:if></div></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.isAlignment.label')}&nbsp;*&nbsp;:</dt><dd id="isAlignment" class="action-editField validate-boolean required-true i18n-ancr.property.isAlignment" ><span class="value-${rqt?.isAlignment}"><g:message code="${rqt?.isAlignment ? 'message.yes' : rqt?.isAlignment==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.isNumbering.label')}&nbsp;*&nbsp;:</dt><dd id="isNumbering" class="action-editField validate-boolean required-true i18n-ancr.property.isNumbering" ><span class="value-${rqt?.isNumbering}"><g:message code="${rqt?.isNumbering ? 'message.yes' : rqt?.isNumbering==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.isConnection.label')}&nbsp;*&nbsp;:</dt><dd id="isConnection" class="action-editField validate-boolean required-true i18n-ancr.property.isConnection" ><span class="value-${rqt?.isConnection}"><g:message code="${rqt?.isConnection ? 'message.yes' : rqt?.isConnection==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.section.label')}&nbsp;*&nbsp;:</dt><dd id="section" class="action-editField validate-string required-true i18n-ancr.property.section" ><span>${rqt?.section}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.number.label')}&nbsp;*&nbsp;:</dt><dd id="number" class="action-editField validate-positiveInteger required-true i18n-ancr.property.number" ><span>${rqt?.number}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ancr.property.locality.label')}&nbsp;:</dt><dd id="locality" class="action-editField validate-string i18n-ancr.property.locality" ><span>${rqt?.locality}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ancr.property.transportationRoute.label')}&nbsp;:</dt><dd id="transportationRoute" class="action-editField validate-string i18n-ancr.property.transportationRoute" ><span>${rqt?.transportationRoute}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ancr.property.moreThanTwoYears.label')}&nbsp;*&nbsp;:</dt><dd id="moreThanTwoYears" class="action-editField validate-boolean required-true i18n-ancr.property.moreThanTwoYears" ><span class="value-${rqt?.moreThanTwoYears}"><g:message code="${rqt?.moreThanTwoYears ? 'message.yes' : rqt?.moreThanTwoYears==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ancr.property.area.label')}&nbsp;:</dt><dd id="area" class="action-editField validate-positiveInteger i18n-ancr.property.area" ><span>${rqt?.area}</span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
    
    
  </div>
  
</div>
