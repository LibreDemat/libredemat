


<div id="requestData" class="yellow-yui-tabview">
  <ul class="yui-nav">
  
    <li class="selected ">
      <a href="#page0"><em><g:message code="srwrcr.step.registration.label" /></em></a>
    </li>
  
    <li class="">
      <a href="#page1"><em><g:message code="srwrcr.step.rules.label" /></em></a>
    </li>
  
    <li class="administration ">
      <a href="#page4"><em><g:message code="request.step.administration.label" /></em></a>
    </li>
  
  </ul>
   
  <div class="yui-content">
    
      
      <!-- step start -->
      <div id="page0">
        <h2><g:message code="property.form" />
          <span><g:message code="srwrcr.step.registration.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required"><g:message code="srwrcr.property.subject.label" /> : </dt>
              <dd><span>${subjectIsChild && !subject?.born ? message(code:'request.subject.childNoBorn', args:[subject?.getFullName()]) : subject?.fullName}</span></dd>
          
              </dl>
              
            
              
              <dl>
                <dt class="required">${message(code:'srwrcr.property.section.label')} &nbsp;*&nbsp;:</dt><dd id="section" class="action-editField validate-libredematEnum required-true i18n-srwrcr.property.section javatype-org.libredemat.business.users.SectionType maxLength-32" ><g:libredematEnumToField var="${rqt?.section}" i18nKeyPrefix="srwrcr.property.section" /></dd>
              </dl>
              
            
          </div>
          <!-- column end -->
          
          <!-- column start -->
          <div class="yui-u">
            
              
              <h3><g:message code="srwrcr.property.theSchool.label" /></h3>
              <dl class="required">
                
                  <dt class="required">${message(code:'srwrcr.property.idSchoolName.label')} &nbsp;*&nbsp;:</dt><dd id="idSchoolName" class="action-editField validate-string required-true i18n-srwrcr.property.idSchoolName" ><span >${rqt?.idSchoolName}</span></dd>
                
                  <dt class="required">${message(code:'srwrcr.property.labelSchoolName.label')} &nbsp;*&nbsp;:</dt><dd id="labelSchoolName" class="action-editField validate-string required-true i18n-srwrcr.property.labelSchoolName" ><span >${rqt?.labelSchoolName}</span></dd>
                
              </dl>
              
            
          </div>
          <!-- column end -->
          
        </div>
        <!-- data step  end -->
      </div>
      <!-- step end -->
      
      <!-- step start -->
      <div id="page1">
        <h2><g:message code="property.form" />
          <span><g:message code="srwrcr.step.rules.label" /></span>
        </h2>
        <div class="yui-g">
          
          
          <!-- column start -->
          <div class="yui-u first">
            
              
              <dl>
                <dt class="required">${message(code:'srwrcr.property.rulesAndRegulationsAcceptance.label')} &nbsp;*&nbsp;:</dt><dd id="rulesAndRegulationsAcceptance" class="action-editField validate-acceptance required-true i18n-srwrcr.property.rulesAndRegulationsAcceptance" ><span class="value-${rqt?.rulesAndRegulationsAcceptance}"><g:message code="message.${rqt?.rulesAndRegulationsAcceptance ? 'yes' : 'no'}" /></span></dd>
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
