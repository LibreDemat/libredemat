


  
    
    <label class="required"><g:message code="cmwr.property.profilDemandeur.label" /> *  <span><g:message code="cmwr.property.profilDemandeur.help" /></span></label>
            <ul class="required ${rqt.stepStates['informations'].invalidFields.contains('profilDemandeur') ? 'validation-failed' : ''}">
              <g:each in="${['PARTICULIER','BAILLEUR','SYNDIC']}">
              <li>
                <input type="radio" id="profilDemandeur_${it}" class="required condition-estParticulier-trigger  validate-one-required" value="${it}" name="profilDemandeur" ${it == rqt.profilDemandeur.toString() ? 'checked="checked"': ''} title="<g:message code="cmwr.property.profilDemandeur.validationError" />" />
                <label for="profilDemandeur_${it}"><g:libredematEnumToText var="${it}" i18nKeyPrefix="cmwr.property.profilDemandeur" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label for="nomOrganisation" class="required condition-estParticulier-unfilled"><g:message code="cmwr.property.nomOrganisation.label" /> *  <span><g:message code="cmwr.property.nomOrganisation.help" /></span></label>
            <input  type="text" id="nomOrganisation"
                   name="nomOrganisation"
                   value="${rqt.nomOrganisation?.toString()}"
                   class="required condition-estParticulier-unfilled  validate-regex ${rqt.stepStates['informations'].invalidFields.contains('nomOrganisation') ? 'validation-failed' : ''}"
                   title="<g:message code="cmwr.property.nomOrganisation.validationError" />" regex="^[\w\W]{0,255}$"  />
            

  

  
    
    <label class="required condition-estParticulier-unfilled"><g:message code="cmwr.property.adresseOrganisation.label" /> *  <span><g:message code="cmwr.property.adresseOrganisation.help" /></span></label>
            <div id="adresseOrganisation" class="address required condition-estParticulier-unfilled  ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation') ? 'validation-failed' : ''}">
            <label for="adresseOrganisation.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.additionalDeliveryInformation}" maxlength="38" id="adresseOrganisation.additionalDeliveryInformation" name="adresseOrganisation.additionalDeliveryInformation" />  
            <label for="adresseOrganisation.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.additionalGeographicalInformation}" maxlength="38" id="adresseOrganisation.additionalGeographicalInformation" name="adresseOrganisation.additionalGeographicalInformation" />
            <label for="adresseOrganisation_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="adresseOrganisation_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input  type="text" class="line1 validate-streetNumber ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.streetNumber') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.streetNumber}" size="5" maxlength="5" id="adresseOrganisation_streetNumber" name="adresseOrganisation.streetNumber" />
            <input  type="text" class="line2 required validate-streetName ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.streetName') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.streetName}" maxlength="32" id="adresseOrganisation_streetName" name="adresseOrganisation.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <input  type="hidden" value="${rqt.adresseOrganisation?.streetMatriculation}" id="adresseOrganisation_streetMatriculation" name="adresseOrganisation.streetMatriculation" />
            <input  type="hidden" value="${rqt.adresseOrganisation?.streetRivoliCode}" id="adresseOrganisation_streetRivoliCode" name="adresseOrganisation.streetRivoliCode" />
            <label for="adresseOrganisation.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.placeNameOrService}" maxlength="38" id="adresseOrganisation.placeNameOrService" name="adresseOrganisation.placeNameOrService" />
            <label for="adresseOrganisation_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="adresseOrganisation_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input  type="text" class="line1 required validate-postalCode ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.postalCode') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.postalCode}" size="5" maxlength="5" id="adresseOrganisation_postalCode" name="adresseOrganisation.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input  type="text" class="line2 required validate-city ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.city') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.city}" maxlength="32" id="adresseOrganisation_city" name="adresseOrganisation.city" title="<g:message code="address.property.city.validationError" />" />
            <input  type="hidden" value="${rqt.adresseOrganisation?.cityInseeCode}" id="adresseOrganisation_cityInseeCode" name="adresseOrganisation.cityInseeCode" />
            <label for="adresseOrganisation.countryName"><g:message code="address.property.countryName" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['informations'].invalidFields.contains('adresseOrganisation.countryName') ? 'validation-failed' : ''}" value="${rqt.adresseOrganisation?.countryName}" maxlength="38" id="adresseOrganisation.countryName" name="adresseOrganisation.countryName" />
            </div>
            

  

  
    
    <label for="nombreResidants" class="required"><g:message code="cmwr.property.nombreResidants.label" /> *  <span><g:message code="cmwr.property.nombreResidants.help" /></span></label>
            <input  type="text" id="nombreResidants"
                   name="nombreResidants"
                   value="${rqt.nombreResidants?.toString()}"
                   class="required  validate-regex ${rqt.stepStates['informations'].invalidFields.contains('nombreResidants') ? 'validation-failed' : ''}"
                   title="<g:message code="cmwr.property.nombreResidants.validationError" />" regex="^[\w\W]{0,255}$"  />
            

  

  
    
    <label class="required"><g:message code="cmwr.property.typeHabitation.label" /> *  <span><g:message code="cmwr.property.typeHabitation.help" /></span></label>
            <ul class="required ${rqt.stepStates['informations'].invalidFields.contains('typeHabitation') ? 'validation-failed' : ''}">
              <g:each in="${['PAVILLON','COMMERCE','IMMEUBLE','INDUSTRIEL']}">
              <li>
                <input type="radio" id="typeHabitation_${it}" class="required  validate-one-required" value="${it}" name="typeHabitation" ${it == rqt.typeHabitation.toString() ? 'checked="checked"': ''} title="<g:message code="cmwr.property.typeHabitation.validationError" />" />
                <label for="typeHabitation_${it}"><g:libredematEnumToText var="${it}" i18nKeyPrefix="cmwr.property.typeHabitation" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label class="required"><g:message code="cmwr.property.conteneur.label" /> *  <span><g:message code="cmwr.property.conteneur.help" /></span></label>
            <g:set var="conteneurIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'conteneur', 'i18nPrefixCode':'cmwr.property.conteneur', 'htmlClass':'required  ', 
                              'lrEntries': lrTypes.conteneur.entries, 'depth':0]" />
            

  

  
    
    <label for="precisionsReparation" class=""><g:message code="cmwr.property.precisionsReparation.label" />   <span><g:message code="cmwr.property.precisionsReparation.help" /></span></label>
            <textarea id="precisionsReparation" name="precisionsReparation" class="  validate-regex ${rqt.stepStates['informations'].invalidFields.contains('precisionsReparation') ? 'validation-failed' : ''}" title="<g:message code="cmwr.property.precisionsReparation.validationError" />" rows="4" cols="" regex="^[\w\W]{0,1024}$" >${rqt.precisionsReparation}</textarea>
            

  

