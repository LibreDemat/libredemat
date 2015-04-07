


  
    <h3><g:message code="pptrr.step.relocation.label" /></h3>
    
      
      <dl>
        
          <g:render template="/frontofficeRequestType/widget/requesterSummary" model="['requester':requester]" />
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.isCompany.label" /></dt>
          <dd><g:message code="message.${rqt.isCompany ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <h4><g:message code="pptrr.property.companyInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptrr.property.siretNumber.label" /></dt><dd>${rqt.siretNumber?.toString()}</dd>

        
          <dt><g:message code="pptrr.property.apeCode.label" /></dt><dd>${rqt.apeCode?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.desiredService.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'desiredService', 'lrEntries': lrTypes.desiredService.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.requesterAddress.label" /></dt><dd>${rqt.requesterAddress?.toString()}</dd>

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
      
    
      
      <h4><g:message code="pptrr.property.equipmentUsed.label" /></h4>
      <dl>
        
          <dt><g:message code="pptrr.property.vehicleType.label" /></dt><dd>${rqt.vehicleType?.toString()}</dd>

        
          <dt><g:message code="pptrr.property.longeur.label" /></dt><dd>${rqt.longeur?.toString()}</dd>

        
          <dt><g:message code="pptrr.property.immatriculation.label" /></dt><dd>${rqt.immatriculation?.toString()}</dd>

        
          <dt><g:message code="pptrr.property.furnitureLifting.label" /></dt>
          <dd><g:message code="message.${rqt.furnitureLifting ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="pptrr.property.other.label" /></dt><dd>${rqt.other?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.observations.label" /></dt><dd>${rqt.observations?.toString()}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="pptrr.step.reglements.label" /></h3>
    
      
      <dl>
        <dt><g:message code="pptrr.property.acceptationReglementInterieur.label" /></dt>
          <dd><g:message code="message.${rqt.acceptationReglementInterieur ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptrr.property.observationsReglement.label" /></dt><dd>${rqt.observationsReglement?.toString()}</dd>

      </dl>
      
    
  


  


