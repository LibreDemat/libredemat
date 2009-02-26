<html>
  <head>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/frontoffice', file:'request.css')}" />
    <script type="text/javascript" src="${createLinkTo(dir:'js/frontoffice',file:'requestCreation.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/frontoffice',file:'condition.js')}"></script>
  </head>  
  <body>
    <g:render template="/frontofficeRequestType/draftPanel" />
    <h2 class="request-creation"> <g:message code="rsr.label" /></h2>
    <p><g:message code="rsr.description" /></p> 
    <p><g:message code="request.duration.label" /><strong> : <g:message code="rsr.duration.value" /></strong></p>
    <p>
      <g:message code="request.requiredDocuments.header" /> :
      <g:each in="${documentTypes}" var="documentType" status="index">
        <strong>
          <g:message code="${documentType.value.i18nKey}"/><g:if test="${index < documentTypes.size() - 1}">,</g:if>
        </strong>
      </g:each>
    </p>
    <g:if test="${flash.confirmationMessage}">
      <p class="message-confirmation">${flash.confirmationMessage}</p>
    </g:if>

<g:set var="requestTypeInfo">
  {"label": "${requestTypeLabel}"
    ,"steps": [  "subject-required",  "contact-required",  "document",  "validation"  ]
  }
</g:set>
<g:set var="requestTypeInfo" value="${requestTypeInfo.encodeAsHTML()}" />

    <div id="requestTabView" class="yui-navset">
      <ul class="yui-nav">

  
        <li class="${['subject', 'firstStep'].contains(currentStep) ? 'selected' : ''}">
  
          <a href="#subject"><em>
          <span class="tag-no_right">1</span>
          <span class="tag-state ${stepStates!= null ? stepStates.subject.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.subject.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="rsr.step.subject.label" />
          </strong>
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'contact' ? 'selected' : ''}">
  
          <a href="#contact"><em>
          <span class="tag-no_right">2</span>
          <span class="tag-state ${stepStates!= null ? stepStates.contact.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.contact.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="rsr.step.contact.label" />
          </strong>
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'document' ? 'selected' : ''}">
  
          <a href="#document"><em>
          <span class="tag-no_right">3</span>
          <span class="tag-state ${stepStates!= null ? stepStates.document.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.document.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <g:message code="request.step.document.label" />
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'validation' ? 'selected' : ''}">
  
          <a href="#validation"><em>
          <span class="tag-no_right">4</span>
          <span class="tag-state ${stepStates!= null ? stepStates.validation.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.validation.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="request.step.validation.label" />
          </strong>
            
          </em></a>
        </li>    

		 </ul>
		 
     <div class="yui-content">

       <div id="subject">
         <form method="POST"  id="stepForm-subject" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.subject.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.subject.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <span class="tag-state tag-required"><g:message code="request.step.required" /></span>
  
             <g:message code="rsr.step.subject.label" />
             <span><g:message code="rsr.step.subject.desc" /></span>
             <span class="error"><g:message code="${stepStates?.subject?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/remoteSupportRequest/subject" />         
           </div>
           <div class="error" id="stepForm-subject-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-subject" name="submit-step-subject" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
  
           <a id="next-tab" href="#contact"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.subject != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.subject}
         </div>
         </g:if>
       </div>  

       <div id="contact">
         <form method="POST"  id="stepForm-contact" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.contact.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.contact.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <span class="tag-state tag-required"><g:message code="request.step.required" /></span>
  
             <g:message code="rsr.step.contact.label" />
             <span><g:message code="rsr.step.contact.desc" /></span>
             <span class="error"><g:message code="${stepStates?.contact?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/remoteSupportRequest/contact" />         
           </div>
           <div class="error" id="stepForm-contact-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-contact" name="submit-step-contact" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#subject"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#document"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.contact != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.contact}
         </div>
         </g:if>
       </div>  

       <div id="document">
         <form method="POST" enctype="multipart/form-data" id="stepForm-document" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.document.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.document.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <g:message code="request.step.document.label" />
             <span><g:message code="request.step.document.desc" /></span>
             <span class="error"><g:message code="${stepStates?.document?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/document" />         
           </div>
           <div class="error" id="stepForm-document-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#contact"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#validation"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.document != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.document}
         </div>
         </g:if>
       </div>  

       <div id="validation">
         <form method="POST"  id="stepForm-validation" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.validation.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.validation.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <span class="tag-state tag-required"><g:message code="request.step.required" /></span>
  
             <g:message code="request.step.validation.label" />
             <span><g:message code="request.step.validation.desc" /></span>
             <span class="error"><g:message code="${stepStates?.validation?.errorMsg}" /></span>
           </h3>
           <div>
  
             <select name="meansOfContact">
               <g:each in="${meansOfContact}" var="moc">
                 <option value="${moc.key}">${moc.label}</option>
               </g:each>
             </select>
  
            <g:render template="/frontofficeRequestType/remoteSupportRequest/validation" />         
           </div>
           <div class="error" id="stepForm-validation-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-validation" name="submit-step-validation" value="${message(code:'action.send')}" ${!isRequestCreatable ? 'disabled="disabled"': ''}/>
           <g:if test="${!isRequestCreatable}">
           <span><g:message code="request.step.validation.requiredSteps"/></span>
           </g:if>
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#document"><g:message code="request.step.navigation.previous"/></a>
  
  
         </div>
         <g:if test="${helps.validation != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.validation}
         </div>
         </g:if>
       </div>  
        
 	    </div><!-- end yui-content -->
    </div><!-- end requestTabView -->
  
  </body>
</html>
