


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="ycrr.step.registration.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="ycrr.step.rules.label" /></em></a>
    </li>
  
    <li class="administration ">
      <a href="#page4"><em><g:message code="request.step.administration.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="ycrr.step.registration.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required"><g:message code="ycrr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ycrr.property.subjectChoiceBirthDate.label')}&nbsp;:</dt><dd id="subjectChoiceBirthDate" class="action-editField validate-date i18n-ycrr.property.subjectChoiceBirthDate" ><span><g:formatDate formatName="format.date" date="${rqt?.subjectChoiceBirthDate}"/></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ycrr.property.subjectChoiceMobilePhone.label')}&nbsp;:</dt><dd id="subjectChoiceMobilePhone" class="action-editField validate-phone i18n-ycrr.property.subjectChoiceMobilePhone maxLength-10" ><span>${rqt?.subjectChoiceMobilePhone}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="">${message(code:'ycrr.property.subjectChoiceEmail.label')}&nbsp;:</dt><dd id="subjectChoiceEmail" class="action-editField validate-email i18n-ycrr.property.subjectChoiceEmail" ><span>${rqt?.subjectChoiceEmail}</span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isFirstRegistration-trigger">${message(code:'ycrr.property.isFirstRegistration.label')}&nbsp;*&nbsp;:</dt><dd id="isFirstRegistration" class="action-editField validate-boolean required-true i18n-ycrr.property.isFirstRegistration" ><span class="value-${rqt?.isFirstRegistration}"><g:message code="${rqt?.isFirstRegistration ? 'message.yes' : rqt?.isFirstRegistration==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isFirstRegistration-filled">${message(code:'ycrr.property.firstRegistrationDate.label')}&nbsp;*&nbsp;:</dt><dd id="firstRegistrationDate" class="action-editField validate-date required-true i18n-ycrr.property.firstRegistrationDate" ><span><g:formatDate formatName="format.date" date="${rqt?.firstRegistrationDate}"/></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required condition-isFirstRegistration-filled">${message(code:'ycrr.property.firstRegistrationNumeroAdherent.label')}&nbsp;*&nbsp;:</dt><dd id="firstRegistrationNumeroAdherent" class="action-editField validate-string required-true i18n-ycrr.property.firstRegistrationNumeroAdherent" ><span>${rqt?.firstRegistrationNumeroAdherent}</span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page1">
        <h2><g:message code="property.form" />
          <span><g:message code="ycrr.step.rules.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'ycrr.property.childAlone.label')}&nbsp;*&nbsp;:</dt><dd id="childAlone" class="action-editField validate-boolean required-true i18n-ycrr.property.childAlone" ><span class="value-${rqt?.childAlone}"><g:message code="${rqt?.childAlone ? 'message.yes' : rqt?.childAlone==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ycrr.property.multiActivities.label')}&nbsp;*&nbsp;:</dt><dd id="multiActivities" class="action-editField validate-boolean required-true i18n-ycrr.property.multiActivities" ><span class="value-${rqt?.multiActivities}"><g:message code="${rqt?.multiActivities ? 'message.yes' : rqt?.multiActivities==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'ycrr.property.rulesAcceptance.label')}&nbsp;*&nbsp;:</dt><dd id="rulesAcceptance" class="action-editField validate-boolean required-true i18n-ycrr.property.rulesAcceptance" ><span class="value-${rqt?.rulesAcceptance}"><g:message code="${rqt?.rulesAcceptance ? 'message.yes' : rqt?.rulesAcceptance==null ? '' : 'message.no'}" /></span></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page4">
        <h2><g:message code="property.form" />
          <span><g:message code="request.step.administration.label" /></span>
        </h2>
        <div class="yui-g">
          
            <div class="administration information-message">
              <g:message code="request.step.administration.desc" />
            </div>
          
          
          <!-- column start -->
          <div class="yui-u first">
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
    
    
  </div>
  
</div>
