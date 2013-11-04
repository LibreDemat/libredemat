


  

    <h3>${message(code:'rarr.step.homeFolder.label')}</h3>

    
            <dl>
              <dt><g:libredematEnumToFlag var="${requester.title}" i18nKeyPrefix="homeFolder.adult.title" /> ${requester.fullName}</dt>
              <dd>
                <ul>
                  <li>
                    <span class="tag-homefolderresponsible tag-state">${message(code:'homeFolder.role.homeFolderResponsible')}</span>
                  </li>
                  <g:if test="${requester.homePhone}">
                    <li>${requester.homePhone}</li>
                  </g:if>
                  <g:if test="${requester.mobilePhone}">
                    <li>${requester.mobilePhone}</li>
                  </g:if>
                  <g:if test="${requester.email}">
                    <li>${requester.email}</li>
                  </g:if>
                </ul>
              </dd>
            </dl>
          


    
          <g:each in="${requester.getHomeFolder().getIndividuals().findAll{ !(it.getState().name.equals('Archived') || it.getState().name.equals('Invalid')) && (requester.getId() != it.getId()) }}" var="individual">
            <g:if test="${individual.getClass() == org.libredemat.business.users.Adult.class}">
              <dl>
                <dt><g:libredematEnumToFlag var="${individual.title}" i18nKeyPrefix="homeFolder.adult.title" /> ${individual.fullName}</dt>
                <dd>
                  <ul>
                    <g:if test="${individual.homePhone}">
                      <li>${individual.homePhone}</li>
                    </g:if>
                    <g:if test="${individual.mobilePhone}">
                      <li>${individual.mobilePhone}</li>
                    </g:if>
                    <g:if test="${individual.email}">
                      <li>${individual.email}</li>
                    </g:if>
                  </ul>
                </dd>
              </dl>
            </g:if>
          </g:each>
          


    
          <g:each in="${requester.getHomeFolder().getIndividuals().findAll{ !(it.getState().name.equals('Archived') || it.getState().name.equals('Invalid'))}}" var="individual">
            <g:if test="${individual.getClass() == org.libredemat.business.users.Child.class}">
              <dl class="${individual.state}">
                <dt>
                  <g:if test="${individual.born}">${individual.fullName}</g:if>
                  <g:else>${message(code:'request.subject.childNoBorn', args:[individual.fullName])}</g:else>
                <dd>
                  <g:if test="${individual.born}">${message(code:'homeFolder.header.born')}</g:if>
                  <g:else>${message(code:'homeFolder.header.noBorn')}</g:else>
                  <g:if test="${individual.birthDate}">
                    ${message(code:'homeFolder.header.on')}
                    ${formatDate(date:individual.birthDate,formatName:'format.date')}
                  </g:if>
                </dd>
              </dl>
            </g:if>
          </g:each>
          


  


  
    <h3><g:message code="rarr.step.registration.label" /></h3>
    
      
      <dl>
        <dt><g:message code="rarr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="rarr.property.recreationActivity.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'recreationActivity', 'lrEntries': lrTypes.recreationActivity.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="rarr.property.urgencyPhone.label" /></dt><dd>${rqt.urgencyPhone?.toString()}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="rarr.step.contact.label" /></h3>
    
      
      <h4><g:message code="rarr.property.contactIndividuals.label" /></h4>
      <g:each var="it" in="${rqt.contactIndividuals}" status="index">
      <dl>
        
          <dt><g:message code="rarr.property.lastName.label" /></dt><dd>${it.lastName?.toString()}</dd>

        
          <dt><g:message code="rarr.property.firstName.label" /></dt><dd>${it.firstName?.toString()}</dd>

        
          <dt><g:message code="rarr.property.address.label" /></dt>
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
          

        
          <dt><g:message code="rarr.property.homePhone.label" /></dt><dd>${it.homePhone?.toString()}</dd>

        
          <dt><g:message code="rarr.property.officePhone.label" /></dt><dd>${it.officePhone?.toString()}</dd>

        
      </dl>
      </g:each>
      
    
  


  
    <h3><g:message code="rarr.step.authorization.label" /></h3>
    
      
      <h4><g:message code="rarr.property.authorizedIndividuals.label" /></h4>
      <g:each var="it" in="${rqt.authorizedIndividuals}" status="index">
      <dl>
        
          <dt><g:message code="rarr.property.lastName.label" /></dt><dd>${it.lastName?.toString()}</dd>

        
          <dt><g:message code="rarr.property.firstName.label" /></dt><dd>${it.firstName?.toString()}</dd>

        
          <dt><g:message code="rarr.property.address.label" /></dt>
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
          

        
          <dt><g:message code="rarr.property.homePhone.label" /></dt><dd>${it.homePhone?.toString()}</dd>

        
          <dt><g:message code="rarr.property.officePhone.label" /></dt><dd>${it.officePhone?.toString()}</dd>

        
      </dl>
      </g:each>
      
    
  


  
    <h3><g:message code="rarr.step.rules.label" /></h3>
    
      
      <dl>
        <dt><g:message code="rarr.property.rulesAndRegulationsAcceptance.label" /></dt>
          <dd><g:message code="message.${rqt.rulesAndRegulationsAcceptance ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="rarr.property.classTripPermission.label" /></dt>
          <dd><g:message code="message.${rqt.classTripPermission ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="rarr.property.childPhotoExploitationPermission.label" /></dt>
          <dd><g:message code="message.${rqt.childPhotoExploitationPermission ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="rarr.property.hospitalizationPermission.label" /></dt>
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
  


  


