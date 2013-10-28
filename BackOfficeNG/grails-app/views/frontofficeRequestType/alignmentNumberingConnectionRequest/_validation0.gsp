


  

    <h3>${message(code:'ancr.step.homeFolder.label')}</h3>

    
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
          


  


  
    <h3><g:message code="ancr.step.address.label" /></h3>
    
      
      <dl>
        <dt><g:message code="ancr.property.isAccountAddress.label" /></dt>
          <dd><g:message code="message.${rqt.isAccountAddress ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.otherAddress.label" /></dt>
          <dd>
          <g:if test="${rqt.otherAddress}">
              <p>${rqt.otherAddress?.additionalDeliveryInformation}</p>
              <p>${rqt.otherAddress?.additionalGeographicalInformation}</p>
              <p>${rqt.otherAddress?.streetNumber} ${rqt.otherAddress?.streetName}</p>
              <p>${rqt.otherAddress?.placeNameOrService}</p>
              <p>${rqt.otherAddress?.postalCode} ${rqt.otherAddress?.city}</p>
              <p>${rqt.otherAddress?.countryName}</p>
          </g:if>
          </dd>
          

      </dl>
      
    
  


  
    <h3><g:message code="ancr.step.cadastre.label" /></h3>
    
      
      <dl>
        <dt><g:message code="ancr.property.requesterQuality.label" /></dt>
          <dd>
            <g:if test="${rqt.requesterQuality}">
              <g:libredematEnumToField var="${rqt.requesterQuality}" i18nKeyPrefix="ancr.property.requesterQuality" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.ownerLastName.label" /></dt><dd>${rqt.ownerLastName?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.ownerFirstNames.label" /></dt><dd>${rqt.ownerFirstNames?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.ownerAddress.label" /></dt>
          <dd>
          <g:if test="${rqt.ownerAddress}">
              <p>${rqt.ownerAddress?.additionalDeliveryInformation}</p>
              <p>${rqt.ownerAddress?.additionalGeographicalInformation}</p>
              <p>${rqt.ownerAddress?.streetNumber} ${rqt.ownerAddress?.streetName}</p>
              <p>${rqt.ownerAddress?.placeNameOrService}</p>
              <p>${rqt.ownerAddress?.postalCode} ${rqt.ownerAddress?.city}</p>
              <p>${rqt.ownerAddress?.countryName}</p>
          </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.section.label" /></dt><dd>${rqt.section?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.number.label" /></dt><dd>${rqt.number?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.locality.label" /></dt><dd>${rqt.locality?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.transportationRoute.label" /></dt><dd>${rqt.transportationRoute?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.moreThanTwoYears.label" /></dt>
          <dd><g:message code="message.${rqt.moreThanTwoYears ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.area.label" /></dt><dd>${rqt.area?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.isAlignment.label" /></dt>
          <dd><g:message code="message.${rqt.isAlignment ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.isNumbering.label" /></dt>
          <dd><g:message code="message.${rqt.isNumbering ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ancr.property.isConnection.label" /></dt>
          <dd><g:message code="message.${rqt.isConnection ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
  


  


