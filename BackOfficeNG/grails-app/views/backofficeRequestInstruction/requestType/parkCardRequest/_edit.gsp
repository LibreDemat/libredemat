


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="pcr.step.subject.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="pcr.step.car.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="pcr.step.subject.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required"><g:message code="pcr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'pcr.property.subjectAddress.label')} &nbsp;*&nbsp;:</dt><dd id="subjectAddress" class="action-editField validate-address required-true i18n-pcr.property.subjectAddress" ><div><p class="additionalDeliveryInformation">${rqt?.subjectAddress?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.subjectAddress?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.subjectAddress?.streetNumber}</span> <span class="streetName">${rqt?.subjectAddress?.streetName}</span><g:if test="${!!rqt?.subjectAddress?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.subjectAddress?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.subjectAddress?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.subjectAddress?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.subjectAddress?.placeNameOrService}</p><span class="postalCode">${rqt?.subjectAddress?.postalCode}</span> <span class="city">${rqt?.subjectAddress?.city}</span><p class="countryName">${rqt?.subjectAddress?.countryName}</p><g:if test="${!!rqt?.subjectAddress?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.subjectAddress?.cityInseeCode}</span></g:if></div></dd>
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
          <span><g:message code="pcr.step.car.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="">${message(code:'pcr.property.parkResident.label')} &nbsp;:</dt><dd id="parkResident" class=" validate-string i18n-pcr.property.parkResident" ><span >${message(code:'pcr.property.' + rqt.parkResident)}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'pcr.property.informationCardLimitRest.label')} &nbsp;:</dt><dd id="informationCardLimitRest" class=" validate-string i18n-pcr.property.informationCardLimitRest" ><span >${rqt?.informationCardLimitRest}</span></dd>
              </dl>
              
            
              
              <div id="widget-parkImatriculation" class="required">
                <g:render template="/backofficeRequestInstruction/requestType/parkCardRequest/parkImatriculation" model="['rqt':rqt]" />
              </div>
              
            
              
              <dl>
                <dt class="">${message(code:'pcr.property.paymentTotal.label')} &nbsp;:</dt><dd id="paymentTotal" class="action-editField validate-string i18n-pcr.property.paymentTotal" ><span >${rqt?.paymentTotal}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'pcr.property.paymentReference.label')} &nbsp;:</dt><dd id="paymentReference" class=" validate-string i18n-pcr.property.paymentReference" ><span >${rqt?.paymentReference}</span></dd>
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
