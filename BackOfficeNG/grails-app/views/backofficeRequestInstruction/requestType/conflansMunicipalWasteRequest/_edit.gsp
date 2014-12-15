


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="cmwr.step.informations.label" /></em></a>
    </li>
  
    <li class="administration ">
      <a href="#page3"><em><g:message code="request.step.administration.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="cmwr.step.informations.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required condition-estParticulier-trigger">${message(code:'cmwr.property.profilDemandeur.label')}&nbsp;*&nbsp;:</dt><dd id="profilDemandeur" class="action-editField validate-libredematEnum required-true i18n-cmwr.property.profilDemandeur javatype-org.libredemat.business.request.environment.CmwrProfilDemandeurType" ><g:libredematEnumToField var="${rqt?.profilDemandeur}" i18nKeyPrefix="cmwr.property.profilDemandeur" /></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-estParticulier-unfilled">${message(code:'cmwr.property.nomOrganisation.label')}&nbsp;*&nbsp;:</dt><dd id="nomOrganisation" class="action-editField validate-regex required-true i18n-cmwr.property.nomOrganisation" regex="^[\w\W]{0,255}$"><span>${rqt?.nomOrganisation}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-estParticulier-unfilled">${message(code:'cmwr.property.adresseOrganisation.label')}&nbsp;*&nbsp;:</dt><dd id="adresseOrganisation" class="action-editField validate-address required-true i18n-cmwr.property.adresseOrganisation" ><div><p class="additionalDeliveryInformation">${rqt?.adresseOrganisation?.additionalDeliveryInformation}</p><p class="additionalGeographicalInformation">${rqt?.adresseOrganisation?.additionalGeographicalInformation}</p><span class="streetNumber">${rqt?.adresseOrganisation?.streetNumber}</span> <span class="streetName">${rqt?.adresseOrganisation?.streetName}</span><g:if test="${!!rqt?.adresseOrganisation?.streetMatriculation}"><br /><em><g:message code="address.property.streetMatriculation" /></em><span class="streetMatriculation">${rqt?.adresseOrganisation?.streetMatriculation}</span></g:if><g:if test="${!!rqt?.adresseOrganisation?.streetRivoliCode}"><br /><em><g:message code="address.property.streetRivoliCode" /></em><span class="streetRivoliCode">${rqt?.adresseOrganisation?.streetRivoliCode}</span></g:if><p class="placeNameOrService">${rqt?.adresseOrganisation?.placeNameOrService}</p><span class="postalCode">${rqt?.adresseOrganisation?.postalCode}</span> <span class="city">${rqt?.adresseOrganisation?.city}</span><p class="countryName">${rqt?.adresseOrganisation?.countryName}</p><g:if test="${!!rqt?.adresseOrganisation?.cityInseeCode}"><em><g:message code="address.property.cityInseeCode" /></em><span class="cityInseeCode">${rqt?.adresseOrganisation?.cityInseeCode}</span></g:if></div></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'cmwr.property.nombreResidants.label')}&nbsp;*&nbsp;:</dt><dd id="nombreResidants" class="action-editField validate-regex required-true i18n-cmwr.property.nombreResidants" regex="^[\w\W]{0,255}$"><span>${rqt?.nombreResidants}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'cmwr.property.typeHabitation.label')}&nbsp;*&nbsp;:</dt><dd id="typeHabitation" class="action-editField validate-libredematEnum required-true i18n-cmwr.property.typeHabitation javatype-org.libredemat.business.request.environment.CmwrTypeHabitationType" ><g:libredematEnumToField var="${rqt?.typeHabitation}" i18nKeyPrefix="cmwr.property.typeHabitation" /></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <dl>
                <dt class="required">${message(code:'cmwr.property.conteneur.label')}&nbsp;*&nbsp;:</dt><dd id="conteneur" class="action-editField validate-localReferentialData required-true i18n-cmwr.property.conteneur data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'conteneur', 'lrEntries': lrTypes.conteneur?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.conteneur?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'cmwr.property.precisionsReparation.label')}&nbsp;:</dt><dd id="precisionsReparation" class="action-editField validate-regex i18n-cmwr.property.precisionsReparation" regex="^[\w\W]{0,1024}$"><span>${rqt?.precisionsReparation}</span></dd>
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
          <span><g:message code="request.step.administration.label" /></span>
        </h2>
        <div class="yui-g">
          
            <div class="administration information-message">
              <g:message code="request.step.administration.desc" />
            </div>
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'cmwr.property.quartier.label')}&nbsp;*&nbsp;:</dt><dd id="quartier" class="action-editField validate-localReferentialData required-true i18n-cmwr.property.quartier data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'quartier', 'lrEntries': lrTypes.quartier?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.quartier?.isMultiple(), 'depth':0]" />
 
          </dd>
              </dl>
              
            
              
              <h3><g:message code="cmwr.property.volumesOm.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'cmwr.property.omCentVingtLitres.label')}&nbsp;:</dt><dd id="omCentVingtLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.omCentVingtLitres" ><span>${rqt?.omCentVingtLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.omDeuxCentQuaranteLitres.label')}&nbsp;:</dt><dd id="omDeuxCentQuaranteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.omDeuxCentQuaranteLitres" ><span>${rqt?.omDeuxCentQuaranteLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.omTroisCentQuaranteLitres.label')}&nbsp;:</dt><dd id="omTroisCentQuaranteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.omTroisCentQuaranteLitres" ><span>${rqt?.omTroisCentQuaranteLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.omSixCentSoixanteLitres.label')}&nbsp;:</dt><dd id="omSixCentSoixanteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.omSixCentSoixanteLitres" ><span>${rqt?.omSixCentSoixanteLitres}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cmwr.property.volumesVerre.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'cmwr.property.verreTrenteCinqLitres.label')}&nbsp;:</dt><dd id="verreTrenteCinqLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.verreTrenteCinqLitres" ><span>${rqt?.verreTrenteCinqLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.verreCentVingtLitres.label')}&nbsp;:</dt><dd id="verreCentVingtLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.verreCentVingtLitres" ><span>${rqt?.verreCentVingtLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.verreDeuxCentQuaranteLitres.label')}&nbsp;:</dt><dd id="verreDeuxCentQuaranteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.verreDeuxCentQuaranteLitres" ><span>${rqt?.verreDeuxCentQuaranteLitres}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <h3><g:message code="cmwr.property.volumesTri.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'cmwr.property.triCentVingtLitres.label')}&nbsp;:</dt><dd id="triCentVingtLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.triCentVingtLitres" ><span>${rqt?.triCentVingtLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.triDeuxCentQuaranteLitres.label')}&nbsp;:</dt><dd id="triDeuxCentQuaranteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.triDeuxCentQuaranteLitres" ><span>${rqt?.triDeuxCentQuaranteLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.triTroisCentQuaranteLitres.label')}&nbsp;:</dt><dd id="triTroisCentQuaranteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.triTroisCentQuaranteLitres" ><span>${rqt?.triTroisCentQuaranteLitres}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.triSixCentSoixanteLitres.label')}&nbsp;:</dt><dd id="triSixCentSoixanteLitres" class="action-editField validate-positiveInteger i18n-cmwr.property.triSixCentSoixanteLitres" ><span>${rqt?.triSixCentSoixanteLitres}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cmwr.property.tailleComposteur.label" /></h3>
              <dl class="required">
                
                  <dt class="">${message(code:'cmwr.property.composteurPetit.label')}&nbsp;:</dt><dd id="composteurPetit" class="action-editField validate-positiveInteger i18n-cmwr.property.composteurPetit" ><span>${rqt?.composteurPetit}</span></dd>
                
                  <dt class="">${message(code:'cmwr.property.composteurGrand.label')}&nbsp;:</dt><dd id="composteurGrand" class="action-editField validate-positiveInteger i18n-cmwr.property.composteurGrand" ><span>${rqt?.composteurGrand}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
    
    
  </div>
  
</div>
