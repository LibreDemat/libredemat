


  
    <h3><g:message code="ycrr.step.registration.label" /></h3>
    
      
      <dl>
        <dt><g:message code="ycrr.property.subject.label" /></dt>
          <dd>${subjects.get(rqt.subjectId)}</dd>

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.subjectChoiceBirthDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.subjectChoiceBirthDate}"/></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.subjectChoiceMobilePhone.label" /></dt>
          <dd>${rqt.subjectChoiceMobilePhone?.toString()}</dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.subjectChoiceEmail.label" /></dt>
          <dd>${rqt.subjectChoiceEmail?.toString()}</dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.isFirstRegistration.label" /></dt>
          <dd><g:message code="message.${rqt.isFirstRegistration ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.firstRegistrationDate.label" /></dt>
          <dd><g:formatDate formatName="format.date" date="${rqt.firstRegistrationDate}"/></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.firstRegistrationNumeroAdherent.label" /></dt>
          <dd>${rqt.firstRegistrationNumeroAdherent?.toString()}</dd>
          

      </dl>
      
    
  


  
    <h3><g:message code="ycrr.step.rules.label" /></h3>
    
      
      <dl>
        <dt><g:message code="ycrr.property.childAlone.label" /></dt>
          <dd><g:message code="message.${rqt.childAlone ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.multiActivities.label" /></dt>
          <dd><g:message code="message.${rqt.multiActivities ? 'yes' : 'no'}" /></dd>
          

      </dl>
      
    
      
      <dl>
        <dt><g:message code="ycrr.property.rulesAcceptance.label" /></dt>
          <dd><g:message code="message.${rqt.rulesAcceptance ? 'yes' : 'no'}" /></dd>
          

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
  


  


