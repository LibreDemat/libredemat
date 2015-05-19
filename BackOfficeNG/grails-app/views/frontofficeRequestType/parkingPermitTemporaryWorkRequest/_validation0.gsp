


  
    <h3><g:message code="pptwr.step.work.label" /></h3>
    
      
      <dl>
        
          <g:render template="/frontofficeRequestType/widget/requesterSummary" model="['requester':requester]" />
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.isCompany.label" /></dt>
          <dd><g:message code="message.${rqt.isCompany ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <h4><g:message code="pptwr.property.companyInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptwr.property.siretNumber.label" /></dt><dd>${rqt.siretNumber?.toString()}</dd>

        
          <dt><g:message code="pptwr.property.apeCode.label" /></dt><dd>${rqt.apeCode?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.desiredService.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'desiredService', 'lrEntries': lrTypes.desiredService.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <h4><g:message code="pptwr.property.parkingPermitForWorkInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptwr.property.siteAddress.label" /></dt><dd>${rqt.siteAddress?.toString()}</dd>

        
          <dt><g:message code="pptwr.property.workNature.label" /></dt><dd>${rqt.workNature?.toString()}</dd>

        
          <dt><g:message code="pptwr.property.workOnBuilding.label" /></dt>
          <dd><g:message code="message.${rqt.workOnBuilding ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="pptwr.property.constructLicenseNumber.label" /></dt><dd>${rqt.constructLicenseNumber?.toString()}</dd>

        
          <dt><g:message code="pptwr.property.usedVehicles.label" /></dt><dd>${rqt.usedVehicles?.toString()}</dd>

        
      </dl>
      
    
      
      <h4><g:message code="pptwr.property.existingLicenseExtensionInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptwr.property.referenceNumber.label" /></dt><dd>${rqt.referenceNumber?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.scaffolding.label" /></dt>
          <dd><g:message code="message.${rqt.scaffolding ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <h4><g:message code="pptwr.property.scaffoldingInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptwr.property.scaffoldingLength.label" /></dt><dd>${formatNumber(number: rqt.scaffoldingLength, type: 'number')}</dd>

        
          <dt><g:message code="pptwr.property.scaffoldingStartDate.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.scaffoldingStartDate}"/></dd>
          

        
          <dt><g:message code="pptwr.property.scaffoldingEndDate.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.scaffoldingEndDate}"/></dd>
          

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.vehicleParkingOrFloorOccupation.label" /></dt>
          <dd><g:message code="message.${rqt.vehicleParkingOrFloorOccupation ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <h4><g:message code="pptwr.property.vehicleParkingOrFloorOccupationInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="pptwr.property.occupation.label" /></dt><dd>${formatNumber(number: rqt.occupation, type: 'number')}</dd>

        
          <dt><g:message code="pptwr.property.occupationStartDate.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.occupationStartDate}"/></dd>
          

        
          <dt><g:message code="pptwr.property.occupationEndDate.label" /></dt>
          <dd><g:formatDate format="dd/MM/yyyy" date="${rqt.occupationEndDate}"/></dd>
          

        
          <dt><g:message code="pptwr.property.occupationOtherAddress.label" /></dt><dd>${rqt.occupationOtherAddress?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.observations.label" /></dt><dd>${rqt.observations?.toString()}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="pptwr.step.reglements.label" /></h3>
    
      
      <dl>
        <dt><g:message code="pptwr.property.acceptationReglementInterieur.label" /></dt>
          <dd><g:message code="message.${rqt.acceptationReglementInterieur ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pptwr.property.observationsReglement.label" /></dt><dd>${rqt.observationsReglement?.toString()}</dd>

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
  


  


