


  

    <h3>${message(code:'lcrr.step.homeFolder.label')}</h3>

    
            <dl>
              <dt><g:capdematEnumToFlag var="${requester.title}" i18nKeyPrefix="homeFolder.adult.title" /> ${requester.fullName}</dt>
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
            <g:if test="${individual.getClass() == fr.cg95.cvq.business.users.Adult.class}">
              <dl>
                <dt><g:capdematEnumToFlag var="${individual.title}" i18nKeyPrefix="homeFolder.adult.title" /> ${individual.fullName}</dt>
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
            <g:if test="${individual.getClass() == fr.cg95.cvq.business.users.Child.class}">
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
          


  


  
    <h3><g:message code="lcrr.step.enfant.label" /></h3>
    
      
      <dl>
        <dt><g:message code="lcrr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="lcrr.property.estDerogation.label" /></dt>
          <dd><g:message code="message.${rqt.estDerogation ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="lcrr.property.motifsDerogationCentreLoisirs.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'motifsDerogationCentreLoisirs', 'lrEntries': lrTypes.motifsDerogationCentreLoisirs.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <h4><g:message code="lcrr.property.centresLoisirs.label" /></h4>
      <dl>
        
          <dt><g:message code="lcrr.property.idCentreLoisirs.label" /></dt><dd>${rqt.idCentreLoisirs?.toString()}</dd>

        
          <dt><g:message code="lcrr.property.labelCentreLoisirs.label" /></dt><dd>${rqt.labelCentreLoisirs?.toString()}</dd>

        
      </dl>
      
    
      
      <dl>
        <dt><g:message code="lcrr.property.estTransport.label" /></dt>
          <dd><g:message code="message.${rqt.estTransport ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <h4><g:message code="lcrr.property.transports.label" /></h4>
      <dl>
        
          <dt><g:message code="lcrr.property.idLigne.label" /></dt><dd>${rqt.idLigne?.toString()}</dd>

        
          <dt><g:message code="lcrr.property.labelLigne.label" /></dt><dd>${rqt.labelLigne?.toString()}</dd>

        
          <dt><g:message code="lcrr.property.idArret.label" /></dt><dd>${rqt.idArret?.toString()}</dd>

        
          <dt><g:message code="lcrr.property.labelArret.label" /></dt><dd>${rqt.labelArret?.toString()}</dd>

        
      </dl>
      
    
  


  
    <h3><g:message code="lcrr.step.reglements.label" /></h3>
    
      
      <dl>
        <dt><g:message code="lcrr.property.acceptationReglementInterieur.label" /></dt>
          <dd><g:message code="message.${rqt.acceptationReglementInterieur ? 'yes' : 'no'}" /></dd>
          

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
  


  


