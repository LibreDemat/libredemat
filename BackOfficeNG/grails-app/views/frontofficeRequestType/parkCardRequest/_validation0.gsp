


  
    <h3><g:message code="pcr.step.subject.label" /></h3>
    
      
      <dl>
        <dt><g:message code="pcr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pcr.property.subjectAddress.label" /></dt>
          <dd>
          <g:if test="${rqt.subjectAddress}">
              <p>${rqt.subjectAddress?.additionalDeliveryInformation}</p>
              <p>${rqt.subjectAddress?.additionalGeographicalInformation}</p>
              <p>${rqt.subjectAddress?.streetNumber} ${rqt.subjectAddress?.streetName}</p>
              <p>${rqt.subjectAddress?.placeNameOrService}</p>
              <p>${rqt.subjectAddress?.postalCode} ${rqt.subjectAddress?.city}</p>
              <p>${rqt.subjectAddress?.countryName}</p>
          </g:if>
          </dd>
          

      </dl>
      
    
  


  
    <h3><g:message code="pcr.step.car.label" /></h3>
    
      
      <dl>
        <dt><g:message code="pcr.property.parkResident.label" /></dt><dd>${rqt.parkResident?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="pcr.property.informationCardLimitRest.label" /></dt><dd>${rqt.informationCardLimitRest?.toString()}</dd>

      </dl>
      
    
      
      <h4><g:message code="pcr.property.parkImatriculation.label" /></h4>
      <g:each var="it" in="${rqt.parkImatriculation}" status="index">
      <dl>
        
          <dt><g:message code="pcr.property.immatriculation.label" /></dt><dd>${it.immatriculation?.toString()}</dd>

        
          <dt><g:message code="pcr.property.tarif.label" /></dt><dd>${it.tarif?.toString()}</dd>

        
      </dl>
      </g:each>
      
    
      
      <dl>
        <dt><g:message code="pcr.property.paymentTotal.label" /></dt><dd>${rqt.paymentTotal?.toString()}</dd>

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
  


  


