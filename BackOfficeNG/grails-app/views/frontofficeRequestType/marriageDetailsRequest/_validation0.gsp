


  

    <h3>${message(code:'mdr.step.homeFolder.label')}</h3>

    
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
          


  


  
    <h3><g:message code="mdr.step.nature.label" /></h3>
    
      
      <dl>
        <dt><g:message code="mdr.property.requesterQuality.label" /></dt>
          <dd>
            <g:if test="${rqt.requesterQuality}">
              <g:libredematEnumToField var="${rqt.requesterQuality}" i18nKeyPrefix="mdr.property.requesterQuality" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mdr.property.requesterQualityPrecision.label" /></dt><dd>${rqt.requesterQualityPrecision?.toString()}</dd>

      </dl>
      
    
      
      <h4><g:message code="mdr.property.marriageHusband.label" /></h4>
      <dl>
        
          <dt><g:message code="mdr.property.marriageHusbandLastName.label" /></dt><dd>${rqt.marriageHusbandLastName?.toString()}</dd>

        
          <dt><g:message code="mdr.property.marriageHusbandFirstNames.label" /></dt><dd>${rqt.marriageHusbandFirstNames?.toString()}</dd>

        
      </dl>
      
    
      
      <h4><g:message code="mdr.property.marriageWife.label" /></h4>
      <dl>
        
          <dt><g:message code="mdr.property.marriageWifeLastName.label" /></dt><dd>${rqt.marriageWifeLastName?.toString()}</dd>

        
          <dt><g:message code="mdr.property.marriageWifeFirstNames.label" /></dt><dd>${rqt.marriageWifeFirstNames?.toString()}</dd>

        
      </dl>
      
    
      
      <h4><g:message code="mdr.property.marriage.label" /></h4>
      <dl>
        
          <dt><g:message code="mdr.property.marriageDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.marriageDate}"/></dd>
          

        
          <dt><g:message code="mdr.property.marriageCity.label" /></dt><dd>${rqt.marriageCity?.toString()}</dd>

        
          <dt><g:message code="mdr.property.marriagePostalCode.label" /></dt><dd>${rqt.marriagePostalCode?.toString()}</dd>

        
      </dl>
      
    
  


  
    <h3><g:message code="mdr.step.type.label" /></h3>
    
      
      <dl>
        <dt><g:message code="mdr.property.format.label" /></dt>
          <dd>
            <g:if test="${rqt.format}">
              <g:libredematEnumToField var="${rqt.format}" i18nKeyPrefix="mdr.property.format" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mdr.property.copies.label" /></dt><dd>${rqt.copies?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mdr.property.motive.label" /></dt>
          <dd>
            <g:if test="${rqt.motive}">
              <g:libredematEnumToField var="${rqt.motive}" i18nKeyPrefix="mdr.property.motive" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mdr.property.comment.label" /></dt><dd>${rqt.comment?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mdr.property.relationship.label" /></dt>
          <dd>
            <g:if test="${rqt.relationship}">
              <g:libredematEnumToField var="${rqt.relationship}" i18nKeyPrefix="mdr.property.relationship" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <h4><g:message code="mdr.property.fatherInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mdr.property.fatherLastName.label" /></dt><dd>${rqt.fatherLastName?.toString()}</dd>

        
          <dt><g:message code="mdr.property.fatherFirstNames.label" /></dt><dd>${rqt.fatherFirstNames?.toString()}</dd>

        
      </dl>
      
    
      
      <h4><g:message code="mdr.property.motherInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mdr.property.motherMaidenName.label" /></dt><dd>${rqt.motherMaidenName?.toString()}</dd>

        
          <dt><g:message code="mdr.property.motherFirstNames.label" /></dt><dd>${rqt.motherFirstNames?.toString()}</dd>

        
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
  


  


