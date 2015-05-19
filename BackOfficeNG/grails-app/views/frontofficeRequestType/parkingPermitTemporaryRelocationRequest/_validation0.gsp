


  
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
      
    
  


  
  <g:if test="${!documentsByTypes.isEmpty()}">
    <h3>${message(code:'request.step.document.label')}</h3>
    <g:each in="${documentsByTypes}" var="documentType">
      <h4>${message(code:documentType.value.name)}</h4>
      <g:if test="${documentType.value.associated}">
      <dl class="document-linked">
        <g:each in="${documentType.value.associated}" var="document">
        <dt>
          <g:if test="${document.ecitizenNote}">${message(code:'document.header.description')} : ${document.ecitizenNote}<br/></g:if>
          <g:if test="${document.endValidityDate}">${message(code:'document.header.expireOn')} ${formatDate(date:document.endValidityDate,formatName:'format.date')}</g:if>
        </dt>
        <dd>
          <g:libredematEnumToFlag var="${document.state}" i18nKeyPrefix="document.state" />
          <a href="${createLink(controller:'frontofficeDocument',action:'details', id:document.id)}" target="blank" title="${message(code:'document.message.preview.longdesc')}">${message(code:'document.message.preview')}</a>
        </dd>
        </g:each>
      </dl>
      </g:if>
      <g:else>
        ${message(code:'document.header.noAttachments')}
      </g:else>
    </g:each>
  </g:if>
  


  


