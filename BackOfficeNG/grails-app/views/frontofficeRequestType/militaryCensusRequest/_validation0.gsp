


  

    <h3>${message(code:'mcr.step.homeFolder.label')}</h3>

    
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
          


  


  
    <h3><g:message code="mcr.step.census.label" /></h3>
    
      
      <dl>
        <dt><g:message code="mcr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childTitle.label" /></dt>
          <dd>
            <g:if test="${rqt.childTitle}">
              <g:libredematEnumToField var="${rqt.childTitle}" i18nKeyPrefix="mcr.property.childTitle" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.maidenName.label" /></dt>
          <dd>${rqt.maidenName?.toString()}</dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childBirthCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.childBirthCountry}">
              <g:libredematEnumToField var="${rqt.childBirthCountry}" i18nKeyPrefix="mcr.property.childBirthCountry" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childResidenceCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.childResidenceCountry}">
              <g:libredematEnumToField var="${rqt.childResidenceCountry}" i18nKeyPrefix="mcr.property.childResidenceCountry" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childPhone.label" /></dt>
          <dd>${rqt.childPhone?.toString()}</dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childMail.label" /></dt>
          <dd>${rqt.childMail?.toString()}</dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.childCountry}">
              <g:libredematEnumToField var="${rqt.childCountry}" i18nKeyPrefix="mcr.property.childCountry" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childOtherCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.childOtherCountry}">
              <g:libredematEnumToField var="${rqt.childOtherCountry}" i18nKeyPrefix="mcr.property.childOtherCountry" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.childConvention.label" /></dt>
          <dd>${rqt.childConvention?.toString()}</dd>
          

      </dl>
      
    
  


  
    <h3><g:message code="mcr.step.parentage.label" /></h3>
    
      
      <h4><g:message code="mcr.property.fatherInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mcr.property.fatherLastName.label" /></dt>
          <dd>${rqt.fatherLastName?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.fatherFirstName.label" /></dt>
          <dd>${rqt.fatherFirstName?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.fatherBirthDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.fatherBirthDate}"/></dd>
          

        
          <dt><g:message code="mcr.property.fatherBirthCity.label" /></dt>
          <dd>${rqt.fatherBirthCity?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.fatherBirthDepartment.label" /></dt>
          <dd>
            <g:if test="${rqt.fatherBirthDepartment}">
              <g:libredematEnumToField var="${rqt.fatherBirthDepartment}" i18nKeyPrefix="mcr.property.fatherBirthDepartment" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.fatherBirthCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.fatherBirthCountry}">
              <g:libredematEnumToField var="${rqt.fatherBirthCountry}" i18nKeyPrefix="mcr.property.fatherBirthCountry" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.fatherNationality.label" /></dt>
          <dd>
            <g:if test="${rqt.fatherNationality}">
              <g:libredematEnumToField var="${rqt.fatherNationality}" i18nKeyPrefix="mcr.property.fatherNationality" />
            </g:if>
          </dd>
          

        
      </dl>
      
    
      
      <h4><g:message code="mcr.property.motherInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mcr.property.motherLastName.label" /></dt>
          <dd>${rqt.motherLastName?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.motherFirstName.label" /></dt>
          <dd>${rqt.motherFirstName?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.motherBirthDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.motherBirthDate}"/></dd>
          

        
          <dt><g:message code="mcr.property.motherBirthCity.label" /></dt>
          <dd>${rqt.motherBirthCity?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.motherBirthDepartment.label" /></dt>
          <dd>
            <g:if test="${rqt.motherBirthDepartment}">
              <g:libredematEnumToField var="${rqt.motherBirthDepartment}" i18nKeyPrefix="mcr.property.motherBirthDepartment" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.motherBirthCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.motherBirthCountry}">
              <g:libredematEnumToField var="${rqt.motherBirthCountry}" i18nKeyPrefix="mcr.property.motherBirthCountry" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.motherNationality.label" /></dt>
          <dd>
            <g:if test="${rqt.motherNationality}">
              <g:libredematEnumToField var="${rqt.motherNationality}" i18nKeyPrefix="mcr.property.motherNationality" />
            </g:if>
          </dd>
          

        
      </dl>
      
    
  


  
    <h3><g:message code="mcr.step.situation.label" /></h3>
    
      
      <h4><g:message code="mcr.property.familySituationInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mcr.property.aliveChildren.label" /></dt>
          <dd>${rqt.aliveChildren?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.childStatus.label" /></dt>
          <dd>
            <g:if test="${rqt.childStatus}">
              <g:libredematEnumToField var="${rqt.childStatus}" i18nKeyPrefix="mcr.property.childStatus" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.childrenInCharge.label" /></dt>
          <dd>${rqt.childrenInCharge?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.otherSituation.label" /></dt>
          <dd>${rqt.otherSituation?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.statePupil.label" /></dt>
          <dd><g:message code="message.${rqt.statePupil ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="mcr.property.prefectPupil.label" /></dt>
          <dd><g:message code="message.${rqt.prefectPupil ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="mcr.property.prefectPupilDepartment.label" /></dt>
          <dd>
            <g:if test="${rqt.prefectPupilDepartment}">
              <g:libredematEnumToField var="${rqt.prefectPupilDepartment}" i18nKeyPrefix="mcr.property.prefectPupilDepartment" />
            </g:if>
          </dd>
          

        
      </dl>
      
    
      
      <h4><g:message code="mcr.property.professionalSituationInformation.label" /></h4>
      <dl>
        
          <dt><g:message code="mcr.property.childSituation.label" /></dt>
          <dd>
            <g:if test="${rqt.childSituation}">
              <g:libredematEnumToField var="${rqt.childSituation}" i18nKeyPrefix="mcr.property.childSituation" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.childDiploma.label" /></dt>
          <dd>
            <g:if test="${rqt.childDiploma}">
              <g:libredematEnumToField var="${rqt.childDiploma}" i18nKeyPrefix="mcr.property.childDiploma" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="mcr.property.childSpeciality.label" /></dt>
          <dd>${rqt.childSpeciality?.toString()}</dd>
          

        
          <dt><g:message code="mcr.property.childProfession.label" /></dt>
          <dd>${rqt.childProfession?.toString()}</dd>
          

        
      </dl>
      
    
  


  
    <h3><g:message code="mcr.step.exemption.label" /></h3>
    
      
      <dl>
        <dt><g:message code="mcr.property.japdExemption.label" /></dt>
          <dd><g:message code="message.${rqt.japdExemption ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.highlyInfirm.label" /></dt>
          <dd><g:message code="message.${rqt.highlyInfirm ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="mcr.property.affectionOrDisease.label" /></dt>
          <dd><g:message code="message.${rqt.affectionOrDisease ? 'yes' : 'no'}" /></dd>
          

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
  


  


