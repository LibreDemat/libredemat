


  
    <h3><g:message code="pptrr.step.relocation.label" /></h3>
    
      
      <dl>
        
          <g:render template="/frontofficeRequestType/widget/requesterSummary" model="['requester':requester]" />
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.requesterAddress.label" /></dt>
          <dd>
          <g:if test="${rqt.requesterAddress}">
              <p>${rqt.requesterAddress?.additionalDeliveryInformation}</p>
              <p>${rqt.requesterAddress?.additionalGeographicalInformation}</p>
              <p>${rqt.requesterAddress?.streetNumber} ${rqt.requesterAddress?.streetName}</p>
              <p>${rqt.requesterAddress?.placeNameOrService}</p>
              <p>${rqt.requesterAddress?.postalCode} ${rqt.requesterAddress?.city}</p>
              <p>${rqt.requesterAddress?.countryName}</p>
          </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.periodeStart.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.periodeStart}"/></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.periodeEnd.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.periodeEnd}"/></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.heureStart.label" /></dt>
          <dd>
            <g:if test="${rqt.heureStart}">
              <g:libredematEnumToField var="${rqt.heureStart}" i18nKeyPrefix="pptrr.property.heureStart" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.heureEnd.label" /></dt>
          <dd>
            <g:if test="${rqt.heureEnd}">
              <g:libredematEnumToField var="${rqt.heureEnd}" i18nKeyPrefix="pptrr.property.heureEnd" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.performChoice.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'performChoice', 'lrEntries': lrTypes.performChoice.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.equipmentUsed.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'equipmentUsed', 'lrEntries': lrTypes.equipmentUsed.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.marque.label" /></dt><dd>${rqt.marque?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.immatriculation.label" /></dt><dd>${rqt.immatriculation?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.longeur.label" /></dt><dd>${rqt.longeur?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.largeur.label" /></dt><dd>${rqt.largeur?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.tonnage.label" /></dt><dd>${rqt.tonnage?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.volume.label" /></dt><dd>${rqt.volume?.toString()}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="pptrr.step.reglements.label" /></h3>
    
      
      <dl>
        <dt><g:message code="pptrr.property.acceptationReglementInterieur.label" /></dt>
          <dd><g:message code="message.${rqt.acceptationReglementInterieur ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
  


  


