


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="serrr.step.inscription.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="serrr.step.situation.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page2"><em><g:message code="serrr.step.radiation.label" /></em></a>
    </li>
  
    <li class="administration ">
      <a href="#page5"><em><g:message code="request.step.administration.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="serrr.step.inscription.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required"><g:message code="serrr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'serrr.property.nomNaissance.label')} &nbsp;*&nbsp;:</dt><dd id="nomNaissance" class="action-editField validate-lastName required-true i18n-serrr.property.nomNaissance maxLength-38" ><span >${rqt?.nomNaissance}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'serrr.property.prenom.label')} &nbsp;*&nbsp;:</dt><dd id="prenom" class="action-editField validate-firstName required-true i18n-serrr.property.prenom maxLength-38" ><span >${rqt?.prenom}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'serrr.property.deuxiemePrenom.label')} &nbsp;:</dt><dd id="deuxiemePrenom" class="action-editField validate-firstName i18n-serrr.property.deuxiemePrenom maxLength-38" ><span >${rqt?.deuxiemePrenom}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'serrr.property.troisiemePrenom.label')} &nbsp;:</dt><dd id="troisiemePrenom" class="action-editField validate-firstName i18n-serrr.property.troisiemePrenom maxLength-38" ><span >${rqt?.troisiemePrenom}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-estFemme-trigger">${message(code:'serrr.property.sexe.label')} &nbsp;*&nbsp;:</dt><dd id="sexe" class="action-editField validate-libredematEnum required-true i18n-serrr.property.sexe javatype-org.libredemat.business.request.election.SerrrSexeType" ><g:libredematEnumToField var="${rqt?.sexe}" i18nKeyPrefix="serrr.property.sexe" /></dd>
              </dl>
              
            
              
              <dl>
                <dt class="condition-estFemme-filled">${message(code:'serrr.property.nomMarital.label')} &nbsp;:</dt><dd id="nomMarital" class="action-editField validate-lastName i18n-serrr.property.nomMarital maxLength-38" ><span >${rqt?.nomMarital}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'serrr.property.dateNaissance.label')} &nbsp;*&nbsp;:</dt><dd id="dateNaissance" class="action-editField validate-date required-true i18n-serrr.property.dateNaissance" ><span><g:formatDate formatName="format.date" date="${rqt?.dateNaissance}"/></span></dd>
              </dl>
              
            
              
              <h3><g:message code="serrr.property.lieuNaissance.label" /></h3>
              <dl class="required">
                
                  <dt class="required">${message(code:'serrr.property.villeNaissanceCodePostal.label')} &nbsp;*&nbsp;:</dt><dd id="villeNaissanceCodePostal" class="action-editField validate-city required-true i18n-serrr.property.villeNaissanceCodePostal maxLength-32" ><span >${rqt?.villeNaissanceCodePostal}</span></dd>
                
                  <dt class="">${message(code:'serrr.property.lieuNaissanceDepartement.label')} &nbsp;:</dt><dd id="lieuNaissanceDepartement" class="action-editField validate-libredematEnum i18n-serrr.property.lieuNaissanceDepartement javatype-org.libredemat.business.users.InseeDepartementCodeType" ><g:libredematEnumToField var="${rqt?.lieuNaissanceDepartement}" i18nKeyPrefix="serrr.property.lieuNaissanceDepartement" /></dd>
                
                  <dt class="">${message(code:'serrr.property.lieuNaissancePays.label')} &nbsp;:</dt><dd id="lieuNaissancePays" class="action-editField validate-libredematEnum i18n-serrr.property.lieuNaissancePays javatype-org.libredemat.business.users.CountryType" ><g:libredematEnumToField var="${rqt?.lieuNaissancePays}" i18nKeyPrefix="serrr.property.lieuNaissancePays" /></dd>
                
              </dl>
              
            
              
              <dl>
                <dt class="required condition-estUnionEuropenne-trigger">${message(code:'serrr.property.nationalite.label')} &nbsp;*&nbsp;:</dt><dd id="nationalite" class="action-editField validate-libredematEnum required-true i18n-serrr.property.nationalite javatype-org.libredemat.business.request.election.SerrrNationaliteType" ><g:libredematEnumToField var="${rqt?.nationalite}" i18nKeyPrefix="serrr.property.nationalite" /></dd>
              </dl>
              
            
              
              <h3><g:message code="serrr.property.fieldsetEstUnionEuropeenne.label" /></h3>
              <dl class="required condition-estUnionEuropenne-filled">
                
                  <dt class="required condition-estUnionEuropenne-filled">${message(code:'serrr.property.precisionNationalite.label')} &nbsp;*&nbsp;:</dt><dd id="precisionNationalite" class="action-editField validate-libredematEnum required-true i18n-serrr.property.precisionNationalite javatype-org.libredemat.business.request.election.SerrrPrecisionNationaliteType" ><g:libredematEnumToField var="${rqt?.precisionNationalite}" i18nKeyPrefix="serrr.property.precisionNationalite" /></dd>
                
                  <dt class="required condition-estUnionEuropenne-filled condition-estElectionEuropenne-trigger">${message(code:'serrr.property.typeElection.label')} &nbsp;*&nbsp;:</dt><dd id="typeElection" class="action-editField validate-libredematEnum required-true i18n-serrr.property.typeElection javatype-org.libredemat.business.request.election.SerrrTypeElectionType" ><g:libredematEnumToField var="${rqt?.typeElection}" i18nKeyPrefix="serrr.property.typeElection" /></dd>
                
                  <dt class="condition-estElectionEuropenne-filled">${message(code:'serrr.property.paysPrecedent.label')} &nbsp;:</dt><dd id="paysPrecedent" class="action-editField validate-libredematEnum i18n-serrr.property.paysPrecedent javatype-org.libredemat.business.users.CountryType" ><g:libredematEnumToField var="${rqt?.paysPrecedent}" i18nKeyPrefix="serrr.property.paysPrecedent" /></dd>
                
                  <dt class="condition-estElectionEuropenne-filled">${message(code:'serrr.property.subdivisionAdministrativePrecedente.label')} &nbsp;:</dt><dd id="subdivisionAdministrativePrecedente" class="action-editField validate-string i18n-serrr.property.subdivisionAdministrativePrecedente" ><span >${rqt?.subdivisionAdministrativePrecedente}</span></dd>
                
                  <dt class="condition-estElectionEuropenne-filled">${message(code:'serrr.property.communeOuLocalitePrecedente.label')} &nbsp;:</dt><dd id="communeOuLocalitePrecedente" class="action-editField validate-city i18n-serrr.property.communeOuLocalitePrecedente maxLength-32" ><span >${rqt?.communeOuLocalitePrecedente}</span></dd>
                
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
          <span><g:message code="serrr.step.situation.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required condition-estChangementCommune-trigger">${message(code:'serrr.property.situation.label')} &nbsp;*&nbsp;:</dt><dd id="situation" class="action-editField validate-libredematEnum required-true i18n-serrr.property.situation javatype-org.libredemat.business.request.election.SerrrSituationType" ><g:libredematEnumToField var="${rqt?.situation}" i18nKeyPrefix="serrr.property.situation" /></dd>
              </dl>
              
            
              
              <h3><g:message code="serrr.property.precedentLieuInscription.label" /></h3>
              <dl class="required condition-estChangementCommune-filled">
                
                  <dt class="required">${message(code:'serrr.property.ancienneCommune.label')} &nbsp;*&nbsp;:</dt><dd id="ancienneCommune" class="action-editField validate-city required-true i18n-serrr.property.ancienneCommune maxLength-32" ><span >${rqt?.ancienneCommune}</span></dd>
                
                  <dt class="required">${message(code:'serrr.property.departementAncienneCommune.label')} &nbsp;*&nbsp;:</dt><dd id="departementAncienneCommune" class="action-editField validate-libredematEnum required-true i18n-serrr.property.departementAncienneCommune javatype-org.libredemat.business.users.InseeDepartementCodeType" ><g:libredematEnumToField var="${rqt?.departementAncienneCommune}" i18nKeyPrefix="serrr.property.departementAncienneCommune" /></dd>
                
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
          <span><g:message code="serrr.step.radiation.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="">${message(code:'serrr.property.ambassadeOuPosteConsulaire.label')} &nbsp;:</dt><dd id="ambassadeOuPosteConsulaire" class="action-editField validate-string i18n-serrr.property.ambassadeOuPosteConsulaire" ><span >${rqt?.ambassadeOuPosteConsulaire}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'serrr.property.paysRadiation.label')} &nbsp;:</dt><dd id="paysRadiation" class="action-editField validate-libredematEnum i18n-serrr.property.paysRadiation javatype-org.libredemat.business.users.CountryType" ><g:libredematEnumToField var="${rqt?.paysRadiation}" i18nKeyPrefix="serrr.property.paysRadiation" /></dd>
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
      <div id="page5">
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
                <dt class="">${message(code:'serrr.property.typeInscription.label')} &nbsp;:</dt><dd id="typeInscription" class="action-editField validate-libredematEnum i18n-serrr.property.typeInscription javatype-org.libredemat.business.request.election.SerrrTypeInscriptionType" ><g:libredematEnumToField var="${rqt?.typeInscription}" i18nKeyPrefix="serrr.property.typeInscription" /></dd>
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
