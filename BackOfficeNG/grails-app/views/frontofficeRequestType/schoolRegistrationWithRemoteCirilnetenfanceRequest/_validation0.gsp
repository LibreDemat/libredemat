


  
    <h3><g:message code="srwrcr.step.registration.label" /></h3>
    
      
      <dl>
        <dt><g:message code="srwrcr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="srwrcr.property.section.label" /></dt>
          <dd>
            <g:if test="${rqt.section}">
              <g:libredematEnumToField var="${rqt.section}" i18nKeyPrefix="srwrcr.property.section" />
            </g:if>
          </dd>
          

      </dl>
      
    
      
      <h4><g:message code="srwrcr.property.theSchool.label" /></h4>
      <dl>
        
          <dt><g:message code="srwrcr.property.idSchoolName.label" /></dt><dd>${rqt.idSchoolName?.toString()}</dd>

        
          <dt><g:message code="srwrcr.property.labelSchoolName.label" /></dt><dd>${rqt.labelSchoolName?.toString()}</dd>

        
      </dl>
      
    
  


  
    <h3><g:message code="srwrcr.step.rules.label" /></h3>
    
      
      <dl>
        <dt><g:message code="srwrcr.property.rulesAndRegulationsAcceptance.label" /></dt>
          <dd><g:message code="message.${rqt.rulesAndRegulationsAcceptance ? 'yes' : 'no'}" /></dd>
          

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
  


  


