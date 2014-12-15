


  
    <h3><g:message code="cmwr.step.informations.label" /></h3>
    
      
      <dl>
        <dt><g:message code="cmwr.property.profilDemandeur.label" /></dt>
          <dd>
            <g:if test="${rqt.profilDemandeur}">
              <g:libredematEnumToField var="${rqt.profilDemandeur}" i18nKeyPrefix="cmwr.property.profilDemandeur" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.nomOrganisation.label" /></dt><dd>${rqt.nomOrganisation?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.adresseOrganisation.label" /></dt>
          <dd>
          <g:if test="${rqt.adresseOrganisation}">
              <p>${rqt.adresseOrganisation?.additionalDeliveryInformation}</p>
              <p>${rqt.adresseOrganisation?.additionalGeographicalInformation}</p>
              <p>${rqt.adresseOrganisation?.streetNumber} ${rqt.adresseOrganisation?.streetName}</p>
              <p>${rqt.adresseOrganisation?.placeNameOrService}</p>
              <p>${rqt.adresseOrganisation?.postalCode} ${rqt.adresseOrganisation?.city}</p>
              <p>${rqt.adresseOrganisation?.countryName}</p>
          </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.nombreResidants.label" /></dt><dd>${rqt.nombreResidants?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.typeHabitation.label" /></dt>
          <dd>
            <g:if test="${rqt.typeHabitation}">
              <g:libredematEnumToField var="${rqt.typeHabitation}" i18nKeyPrefix="cmwr.property.typeHabitation" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.conteneur.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'conteneur', 'lrEntries': lrTypes.conteneur.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="cmwr.property.precisionsReparation.label" /></dt><dd>${rqt.precisionsReparation?.toString()}</dd>

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
  


  


