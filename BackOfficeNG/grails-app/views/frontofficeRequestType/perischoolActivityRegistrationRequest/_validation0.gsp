


  
    <h3><g:message code="parr.step.registration.label" /></h3>
    
      
      <dl>
        <dt><g:message code="parr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="parr.property.perischoolActivity.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'perischoolActivity', 'lrEntries': lrTypes.perischoolActivity.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="parr.property.urgencyPhone.label" /></dt><dd>${rqt.urgencyPhone?.toString()}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="parr.step.contact.label" /></h3>
    
      
      <h4><g:message code="parr.property.contactIndividuals.label" /></h4>
      <g:each var="it" in="${rqt.contactIndividuals}" status="index">
      <dl>
        
          <dt><g:message code="parr.property.lastName.label" /></dt><dd>${it.lastName?.toString()}</dd>

        
          <dt><g:message code="parr.property.firstName.label" /></dt><dd>${it.firstName?.toString()}</dd>

        
          <dt><g:message code="parr.property.address.label" /></dt>
          <dd>
          <g:if test="${it.address}">
              <p>${it.address?.additionalDeliveryInformation}</p>
              <p>${it.address?.additionalGeographicalInformation}</p>
              <p>${it.address?.streetNumber} ${it.address?.streetName}</p>
              <p>${it.address?.placeNameOrService}</p>
              <p>${it.address?.postalCode} ${it.address?.city}</p>
              <p>${it.address?.countryName}</p>
          </g:if>
          </dd>
          

        
          <dt><g:message code="parr.property.homePhone.label" /></dt><dd>${it.homePhone?.toString()}</dd>

        
          <dt><g:message code="parr.property.officePhone.label" /></dt><dd>${it.officePhone?.toString()}</dd>

        
      </dl>
      </g:each>
      
    
  


  
    <h3><g:message code="parr.step.authorization.label" /></h3>
    
      
      <h4><g:message code="parr.property.authorizedIndividuals.label" /></h4>
      <g:each var="it" in="${rqt.authorizedIndividuals}" status="index">
      <dl>
        
          <dt><g:message code="parr.property.lastName.label" /></dt><dd>${it.lastName?.toString()}</dd>

        
          <dt><g:message code="parr.property.firstName.label" /></dt><dd>${it.firstName?.toString()}</dd>

        
          <dt><g:message code="parr.property.address.label" /></dt>
          <dd>
          <g:if test="${it.address}">
              <p>${it.address?.additionalDeliveryInformation}</p>
              <p>${it.address?.additionalGeographicalInformation}</p>
              <p>${it.address?.streetNumber} ${it.address?.streetName}</p>
              <p>${it.address?.placeNameOrService}</p>
              <p>${it.address?.postalCode} ${it.address?.city}</p>
              <p>${it.address?.countryName}</p>
          </g:if>
          </dd>
          

        
          <dt><g:message code="parr.property.homePhone.label" /></dt><dd>${it.homePhone?.toString()}</dd>

        
          <dt><g:message code="parr.property.officePhone.label" /></dt><dd>${it.officePhone?.toString()}</dd>

        
      </dl>
      </g:each>
      
    
  


  
    <h3><g:message code="parr.step.rules.label" /></h3>
    
      
      <dl>
        <dt><g:message code="parr.property.rulesAndRegulationsAcceptance.label" /></dt>
          <dd><g:message code="message.${rqt.rulesAndRegulationsAcceptance ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="parr.property.classTripPermission.label" /></dt>
          <dd><g:message code="message.${rqt.classTripPermission ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="parr.property.childPhotoExploitationPermission.label" /></dt>
          <dd><g:message code="message.${rqt.childPhotoExploitationPermission ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="parr.property.hospitalizationPermission.label" /></dt>
          <dd><g:message code="message.${rqt.hospitalizationPermission ? 'yes' : 'no'}" /></dd>
          

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
          <g:capdematEnumToFlag var="${document.state}" i18nKeyPrefix="document.state" />
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
  


  


