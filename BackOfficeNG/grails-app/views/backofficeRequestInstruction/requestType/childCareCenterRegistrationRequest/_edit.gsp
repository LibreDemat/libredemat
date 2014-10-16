


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="cccrr.step.registrationSubject.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="cccrr.step.registrationParams.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page2"><em><g:message code="cccrr.step.welcoming.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="cccrr.step.registrationSubject.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required"><g:message code="cccrr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
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
          <span><g:message code="cccrr.step.registrationParams.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'cccrr.property.registrationDate.label')}&nbsp;*&nbsp;:</dt><dd id="registrationDate" class="action-editField validate-date required-true i18n-cccrr.property.registrationDate" ><span><g:formatDate formatName="format.date" date="${rqt?.registrationDate}"/></span></dd>
              </dl>
              
            
              
              <h3><g:message code="cccrr.property.mondayRegistrationParam.label" /></h3>
              <dl class="">
                
                  <dt class="condition-isMondayPeriodeChoice-trigger">${message(code:'cccrr.property.mondayPeriod.label')}&nbsp;:</dt><dd id="mondayPeriod" class="action-editField validate-libredematEnum i18n-cccrr.property.mondayPeriod javatype-org.libredemat.business.request.school.DayPeriodType" ><g:libredematEnumToField var="${rqt?.mondayPeriod}" i18nKeyPrefix="cccrr.property.mondayPeriod" /></dd>
                
                  <dt class="">${message(code:'cccrr.property.mondayFirstPeriodBegining.label')}&nbsp;:</dt><dd id="mondayFirstPeriodBegining" class="action-editField validate-string i18n-cccrr.property.mondayFirstPeriodBegining" ><span>${rqt?.mondayFirstPeriodBegining}</span></dd>
                
                  <dt class="">${message(code:'cccrr.property.mondayFirstPeriodEnding.label')}&nbsp;:</dt><dd id="mondayFirstPeriodEnding" class="action-editField validate-string i18n-cccrr.property.mondayFirstPeriodEnding" ><span>${rqt?.mondayFirstPeriodEnding}</span></dd>
                
                  <dt class="condition-isMondayPeriodeChoice-filled">${message(code:'cccrr.property.mondaySecondPeriodBegining.label')}&nbsp;:</dt><dd id="mondaySecondPeriodBegining" class="action-editField validate-string i18n-cccrr.property.mondaySecondPeriodBegining" ><span>${rqt?.mondaySecondPeriodBegining}</span></dd>
                
                  <dt class="condition-isMondayPeriodeChoice-filled">${message(code:'cccrr.property.mondaySecondPeriodEnding.label')}&nbsp;:</dt><dd id="mondaySecondPeriodEnding" class="action-editField validate-string i18n-cccrr.property.mondaySecondPeriodEnding" ><span>${rqt?.mondaySecondPeriodEnding}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cccrr.property.tuesdayRegistrationParam.label" /></h3>
              <dl class="required">
                
                  <dt class="condition-isTuesdayPeriodeChoice-trigger">${message(code:'cccrr.property.tuesdayPeriod.label')}&nbsp;:</dt><dd id="tuesdayPeriod" class="action-editField validate-libredematEnum i18n-cccrr.property.tuesdayPeriod javatype-org.libredemat.business.request.school.DayPeriodType" ><g:libredematEnumToField var="${rqt?.tuesdayPeriod}" i18nKeyPrefix="cccrr.property.tuesdayPeriod" /></dd>
                
                  <dt class="">${message(code:'cccrr.property.tuesdayFirstPeriodBegining.label')}&nbsp;:</dt><dd id="tuesdayFirstPeriodBegining" class="action-editField validate-string i18n-cccrr.property.tuesdayFirstPeriodBegining" ><span>${rqt?.tuesdayFirstPeriodBegining}</span></dd>
                
                  <dt class="">${message(code:'cccrr.property.tuesdayFirstPeriodEnding.label')}&nbsp;:</dt><dd id="tuesdayFirstPeriodEnding" class="action-editField validate-string i18n-cccrr.property.tuesdayFirstPeriodEnding" ><span>${rqt?.tuesdayFirstPeriodEnding}</span></dd>
                
                  <dt class="condition-isTuesdayPeriodeChoice-filled">${message(code:'cccrr.property.tuesdaySecondPeriodBegining.label')}&nbsp;:</dt><dd id="tuesdaySecondPeriodBegining" class="action-editField validate-string i18n-cccrr.property.tuesdaySecondPeriodBegining" ><span>${rqt?.tuesdaySecondPeriodBegining}</span></dd>
                
                  <dt class="condition-isTuesdayPeriodeChoice-filled">${message(code:'cccrr.property.tuesdaySecondPeriodEnding.label')}&nbsp;:</dt><dd id="tuesdaySecondPeriodEnding" class="action-editField validate-string i18n-cccrr.property.tuesdaySecondPeriodEnding" ><span>${rqt?.tuesdaySecondPeriodEnding}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cccrr.property.wednesdayRegistrationParam.label" /></h3>
              <dl class="required">
                
                  <dt class="condition-isWednesdayPeriodeChoice-trigger">${message(code:'cccrr.property.wednesdayPeriod.label')}&nbsp;:</dt><dd id="wednesdayPeriod" class="action-editField validate-libredematEnum i18n-cccrr.property.wednesdayPeriod javatype-org.libredemat.business.request.school.DayPeriodType" ><g:libredematEnumToField var="${rqt?.wednesdayPeriod}" i18nKeyPrefix="cccrr.property.wednesdayPeriod" /></dd>
                
                  <dt class="">${message(code:'cccrr.property.wednesdayFirstPeriodBegining.label')}&nbsp;:</dt><dd id="wednesdayFirstPeriodBegining" class="action-editField validate-string i18n-cccrr.property.wednesdayFirstPeriodBegining" ><span>${rqt?.wednesdayFirstPeriodBegining}</span></dd>
                
                  <dt class="">${message(code:'cccrr.property.wednesdayFirstPeriodEnding.label')}&nbsp;:</dt><dd id="wednesdayFirstPeriodEnding" class="action-editField validate-string i18n-cccrr.property.wednesdayFirstPeriodEnding" ><span>${rqt?.wednesdayFirstPeriodEnding}</span></dd>
                
                  <dt class="condition-isWednesdayPeriodeChoice-filled">${message(code:'cccrr.property.wednesdaySecondPeriodBegining.label')}&nbsp;:</dt><dd id="wednesdaySecondPeriodBegining" class="action-editField validate-string i18n-cccrr.property.wednesdaySecondPeriodBegining" ><span>${rqt?.wednesdaySecondPeriodBegining}</span></dd>
                
                  <dt class="condition-isWednesdayPeriodeChoice-filled">${message(code:'cccrr.property.wednesdaySecondPeriodEnding.label')}&nbsp;:</dt><dd id="wednesdaySecondPeriodEnding" class="action-editField validate-string i18n-cccrr.property.wednesdaySecondPeriodEnding" ><span>${rqt?.wednesdaySecondPeriodEnding}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cccrr.property.thursdayRegistrationParam.label" /></h3>
              <dl class="required">
                
                  <dt class="condition-isThursdayPeriodeChoice-trigger">${message(code:'cccrr.property.thursdayPeriod.label')}&nbsp;:</dt><dd id="thursdayPeriod" class="action-editField validate-libredematEnum i18n-cccrr.property.thursdayPeriod javatype-org.libredemat.business.request.school.DayPeriodType" ><g:libredematEnumToField var="${rqt?.thursdayPeriod}" i18nKeyPrefix="cccrr.property.thursdayPeriod" /></dd>
                
                  <dt class="">${message(code:'cccrr.property.thursdayFirstPeriodBegining.label')}&nbsp;:</dt><dd id="thursdayFirstPeriodBegining" class="action-editField validate-string i18n-cccrr.property.thursdayFirstPeriodBegining" ><span>${rqt?.thursdayFirstPeriodBegining}</span></dd>
                
                  <dt class="">${message(code:'cccrr.property.thursdayFirstPeriodEnding.label')}&nbsp;:</dt><dd id="thursdayFirstPeriodEnding" class="action-editField validate-string i18n-cccrr.property.thursdayFirstPeriodEnding" ><span>${rqt?.thursdayFirstPeriodEnding}</span></dd>
                
                  <dt class="condition-isThursdayPeriodeChoice-filled">${message(code:'cccrr.property.thursdaySecondPeriodBegining.label')}&nbsp;:</dt><dd id="thursdaySecondPeriodBegining" class="action-editField validate-string i18n-cccrr.property.thursdaySecondPeriodBegining" ><span>${rqt?.thursdaySecondPeriodBegining}</span></dd>
                
                  <dt class="condition-isThursdayPeriodeChoice-filled">${message(code:'cccrr.property.thursdaySecondPeriodEnding.label')}&nbsp;:</dt><dd id="thursdaySecondPeriodEnding" class="action-editField validate-string i18n-cccrr.property.thursdaySecondPeriodEnding" ><span>${rqt?.thursdaySecondPeriodEnding}</span></dd>
                
              </dl>
              
            
              
              <h3><g:message code="cccrr.property.fridayRegistrationParam.label" /></h3>
              <dl class="required">
                
                  <dt class="condition-isFridayPeriodeChoice-trigger">${message(code:'cccrr.property.fridayPeriod.label')}&nbsp;:</dt><dd id="fridayPeriod" class="action-editField validate-libredematEnum i18n-cccrr.property.fridayPeriod javatype-org.libredemat.business.request.school.DayPeriodType" ><g:libredematEnumToField var="${rqt?.fridayPeriod}" i18nKeyPrefix="cccrr.property.fridayPeriod" /></dd>
                
                  <dt class="">${message(code:'cccrr.property.fridayFirstPeriodBegining.label')}&nbsp;:</dt><dd id="fridayFirstPeriodBegining" class="action-editField validate-string i18n-cccrr.property.fridayFirstPeriodBegining" ><span>${rqt?.fridayFirstPeriodBegining}</span></dd>
                
                  <dt class="">${message(code:'cccrr.property.fridayFirstPeriodEnding.label')}&nbsp;:</dt><dd id="fridayFirstPeriodEnding" class="action-editField validate-string i18n-cccrr.property.fridayFirstPeriodEnding" ><span>${rqt?.fridayFirstPeriodEnding}</span></dd>
                
                  <dt class="condition-isFridayPeriodeChoice-filled">${message(code:'cccrr.property.fridaySecondPeriodBegining.label')}&nbsp;:</dt><dd id="fridaySecondPeriodBegining" class="action-editField validate-string i18n-cccrr.property.fridaySecondPeriodBegining" ><span>${rqt?.fridaySecondPeriodBegining}</span></dd>
                
                  <dt class="condition-isFridayPeriodeChoice-filled">${message(code:'cccrr.property.fridaySecondPeriodEnding.label')}&nbsp;:</dt><dd id="fridaySecondPeriodEnding" class="action-editField validate-string i18n-cccrr.property.fridaySecondPeriodEnding" ><span>${rqt?.fridaySecondPeriodEnding}</span></dd>
                
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
      <div id="page2">
        <h2><g:message code="property.form" />
          <span><g:message code="cccrr.step.welcoming.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'cccrr.property.welcomingChoice.label')}&nbsp;*&nbsp;:</dt><dd id="welcomingChoice" class="action-editField validate-localReferentialData required-true i18n-cccrr.property.welcomingChoice data-localReferentialData" >
           <g:render template="/backofficeRequestInstruction/widget/localReferentialDataStatic" 
                     model="['javaName':'welcomingChoice', 'lrEntries': lrTypes.welcomingChoice?.entries, 
                             'rqt':rqt, 'isMultiple':lrTypes.welcomingChoice?.isMultiple(), 'depth':0]" />
 
          </dd>
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
      
    
    
  </div>
  
</div>
