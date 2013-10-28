


  

    <h3>${message(code:'sgr.step.homeFolder.label')}</h3>

    
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
          


  


  
    <h3><g:message code="sgr.step.subject.label" /></h3>
    
      
      <h4><g:message code="sgr.property.subjectInformations.label" /></h4>
      <dl>
        
          <dt><g:message code="sgr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

        
          <dt><g:message code="sgr.property.subjectBirthDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.subjectBirthDate}"/></dd>
          

        
          <dt><g:message code="sgr.property.subjectFirstRequest.label" /></dt>
          <dd><g:message code="message.${rqt.subjectFirstRequest ? 'yes' : 'no'}" /></dd>
          

        
      </dl>
      
    
  


  
    <h3><g:message code="sgr.step.taxHousehold.label" /></h3>
    
      
      <dl>
        <dt><g:message code="sgr.property.taxHouseholdLastName.label" /></dt><dd>${rqt.taxHouseholdLastName?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.taxHouseholdFirstName.label" /></dt><dd>${rqt.taxHouseholdFirstName?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.taxHouseholdCity.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'taxHouseholdCity', 'lrEntries': lrTypes.taxHouseholdCity.entries, 'depth':0]" />
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.taxHouseholdCityPrecision.label" /></dt><dd>${rqt.taxHouseholdCityPrecision?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.taxHouseholdIncome.label" /></dt><dd>${formatNumber(number: rqt.taxHouseholdIncome, type: 'number')}</dd>

      </dl>
      
    
  


  
    <h3><g:message code="sgr.step.otherHelps.label" /></h3>
    
      
      <dl>
        <dt><g:message code="sgr.property.hasCROUSHelp.label" /></dt>
          <dd><g:message code="message.${rqt.hasCROUSHelp ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.hasRegionalCouncilHelp.label" /></dt>
          <dd><g:message code="message.${rqt.hasRegionalCouncilHelp ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.hasEuropeHelp.label" /></dt>
          <dd><g:message code="message.${rqt.hasEuropeHelp ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.hasOtherHelp.label" /></dt>
          <dd><g:message code="message.${rqt.hasOtherHelp ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
  


  
    <h3><g:message code="sgr.step.currentStudies.label" /></h3>
    
      
      <h4><g:message code="sgr.property.currentSchool.label" /></h4>
      <dl>
        
          <dt><g:message code="sgr.property.currentSchoolName.label" /></dt>
          <dd>
          <g:render template="/frontofficeRequestType/widget/localReferentialDataSummary" 
                    model="['javaName':'currentSchoolName', 'lrEntries': lrTypes.currentSchoolName.entries, 'depth':0]" />
          </dd>
          

        
          <dt><g:message code="sgr.property.currentSchoolNamePrecision.label" /></dt><dd>${rqt.currentSchoolNamePrecision?.toString()}</dd>

        
          <dt><g:message code="sgr.property.currentSchoolAddress.label" /></dt>
          <dd>
          <g:if test="${rqt.currentSchoolAddress}">
              <p>${rqt.currentSchoolAddress?.additionalDeliveryInformation}</p>
              <p>${rqt.currentSchoolAddress?.additionalGeographicalInformation}</p>
              <p>${rqt.currentSchoolAddress?.streetNumber} ${rqt.currentSchoolAddress?.streetName}</p>
              <p>${rqt.currentSchoolAddress?.placeNameOrService}</p>
              <p>${rqt.currentSchoolAddress?.postalCode} ${rqt.currentSchoolAddress?.city}</p>
              <p>${rqt.currentSchoolAddress?.countryName}</p>
          </g:if>
          </dd>
          

        
      </dl>
      
    
      
      <h4><g:message code="sgr.property.aLevelsInformations.label" /></h4>
      <dl>
        
          <dt><g:message code="sgr.property.alevelsDate.label" /></dt><dd>${rqt.alevelsDate?.toString()}</dd>

        
          <dt><g:message code="sgr.property.alevels.label" /></dt>
          <dd>
            <g:if test="${rqt.alevels}">
              <g:libredematEnumToField var="${rqt.alevels}" i18nKeyPrefix="sgr.property.alevels" />
            </g:if>
          </dd>
          

        
      </dl>
      
    
      
      <h4><g:message code="sgr.property.currentStudiesInformations.label" /></h4>
      <dl>
        
          <dt><g:message code="sgr.property.currentStudiesDiploma.label" /></dt>
          <dd>
            <g:if test="${rqt.currentStudiesDiploma}">
              <g:libredematEnumToField var="${rqt.currentStudiesDiploma}" i18nKeyPrefix="sgr.property.currentStudiesDiploma" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="sgr.property.otherStudiesLabel.label" /></dt><dd>${rqt.otherStudiesLabel?.toString()}</dd>

        
          <dt><g:message code="sgr.property.currentStudiesLevel.label" /></dt>
          <dd>
            <g:if test="${rqt.currentStudiesLevel}">
              <g:libredematEnumToField var="${rqt.currentStudiesLevel}" i18nKeyPrefix="sgr.property.currentStudiesLevel" />
            </g:if>
          </dd>
          

        
          <dt><g:message code="sgr.property.sandwichCourses.label" /></dt>
          <dd><g:message code="message.${rqt.sandwichCourses ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="sgr.property.abroadInternship.label" /></dt>
          <dd><g:message code="message.${rqt.abroadInternship ? 'yes' : 'no'}" /></dd>
          

        
          <dt><g:message code="sgr.property.abroadInternshipStartDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.abroadInternshipStartDate}"/></dd>
          

        
          <dt><g:message code="sgr.property.abroadInternshipEndDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.abroadInternshipEndDate}"/></dd>
          

        
          <dt><g:message code="sgr.property.abroadInternshipSchoolName.label" /></dt><dd>${rqt.abroadInternshipSchoolName?.toString()}</dd>

        
          <dt><g:message code="sgr.property.abroadInternshipSchoolCountry.label" /></dt>
          <dd>
            <g:if test="${rqt.abroadInternshipSchoolCountry}">
              <g:libredematEnumToField var="${rqt.abroadInternshipSchoolCountry}" i18nKeyPrefix="sgr.property.abroadInternshipSchoolCountry" />
            </g:if>
          </dd>
          

        
      </dl>
      
    
  


  
    <h3><g:message code="sgr.step.bankReference.label" /></h3>
    
      
      <dl>
        <dt><g:message code="sgr.property.isSubjectAccountHolder.label" /></dt>
          <dd><g:message code="message.${rqt.isSubjectAccountHolder ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.accountHolderTitle.label" /></dt>
          <dd>
            <g:if test="${rqt.accountHolderTitle}">
              <g:libredematEnumToField var="${rqt.accountHolderTitle}" i18nKeyPrefix="sgr.property.accountHolderTitle" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.accountHolderLastName.label" /></dt><dd>${rqt.accountHolderLastName?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.accountHolderFirstName.label" /></dt><dd>${rqt.accountHolderFirstName?.toString()}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.accountHolderBirthDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.accountHolderBirthDate}"/></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="sgr.property.bankAccount.label" /></dt>
          <dd>
          <g:if test="${rqt.bankAccount}">
            <p>
              ${rqt.bankAccount?.BIC}
              ${rqt.bankAccount?.IBAN}
            </p>
          </g:if>
          </dd>
          

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
  


  


