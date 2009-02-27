<html>
  <head>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/frontoffice', file:'request.css')}" />
    <script type="text/javascript" src="${createLinkTo(dir:'js/frontoffice',file:'requestCreation.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/frontoffice',file:'condition.js')}"></script>
  </head>  
  <body>
    <g:set var="requestTypeInfo">
      {"label": "${requestTypeLabel}"
        ,"steps": [  "subject-required",  "familyReferent",  "spouse",  "dwelling-required",  "resources-required",  "taxes",  "document",  "validation"  ]
      }
    </g:set>
    <g:set var="requestTypeInfo" value="${requestTypeInfo.encodeAsHTML()}" scope="request" />
    <g:render template="/frontofficeRequestType/draftPanel" />
    <g:render template="/frontofficeRequestType/cancelPanel" />
    <g:set var="requestTypeInfo" value="${requestTypeInfo.encodeAsHTML()}" />
    
    <h2 class="request-creation"> <g:message code="dhr.label" /></h2>
    <p><g:message code="dhr.description" /></p> 
    <p><g:message code="request.duration.label" /><strong> : <g:message code="dhr.duration.value" /></strong></p>
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

    <div id="requestTabView" class="yui-navset">
      <ul class="yui-nav">

  
        <li class="${['subject', 'firstStep'].contains(currentStep) ? 'selected' : ''}">
  
          <a href="#subject"><em>
          <span class="tag-no_right">1</span>
          <span class="tag-state ${stepStates!= null ? stepStates.subject.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.subject.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="dhr.step.subject.label" /> *
          </strong>
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'familyReferent' ? 'selected' : ''}">
  
          <a href="#familyReferent"><em>
          <span class="tag-no_right">2</span>
          <span class="tag-state ${stepStates!= null ? stepStates.familyReferent.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.familyReferent.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <g:message code="dhr.step.familyReferent.label" />
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'spouse' ? 'selected' : ''}">
  
          <a href="#spouse"><em>
          <span class="tag-no_right">3</span>
          <span class="tag-state ${stepStates!= null ? stepStates.spouse.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.spouse.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <g:message code="dhr.step.spouse.label" />
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'dwelling' ? 'selected' : ''}">
  
          <a href="#dwelling"><em>
          <span class="tag-no_right">4</span>
          <span class="tag-state ${stepStates!= null ? stepStates.dwelling.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.dwelling.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="dhr.step.dwelling.label" /> *
          </strong>
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'resources' ? 'selected' : ''}">
  
          <a href="#resources"><em>
          <span class="tag-no_right">5</span>
          <span class="tag-state ${stepStates!= null ? stepStates.resources.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.resources.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="dhr.step.resources.label" /> *
          </strong>
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'taxes' ? 'selected' : ''}">
  
          <a href="#taxes"><em>
          <span class="tag-no_right">6</span>
          <span class="tag-state ${stepStates!= null ? stepStates.taxes.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.taxes.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <g:message code="dhr.step.taxes.label" />
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'document' ? 'selected' : ''}">
  
          <a href="#document"><em>
          <span class="tag-no_right">7</span>
          <span class="tag-state ${stepStates!= null ? stepStates.document.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.document.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <g:message code="request.step.document.label" />
            
          </em></a>
        </li>    

    
        <li class="${currentStep == 'validation' ? 'selected' : ''}">
  
          <a href="#validation"><em>
          <span class="tag-no_right">8</span>
          <span class="tag-state ${stepStates!= null ? stepStates.validation.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.validation.i18nKey : 'request.step.state.uncomplete'}" /></span>
    
          <strong>
            <g:message code="request.step.validation.label" /> *
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
  
             <g:message code="dhr.step.subject.label" />
             <span><g:message code="dhr.step.subject.desc" /></span>
             <span class="error"><g:message code="${stepStates?.subject?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/subject" />         
           </div>
           <div class="error" id="stepForm-subject-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-subject" name="submit-step-subject" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
  
           <a id="next-tab" href="#familyReferent"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.subject != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.subject}
         </div>
         </g:if>
       </div>  

       <div id="familyReferent">
         <form method="POST"  id="stepForm-familyReferent" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.familyReferent.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.familyReferent.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <g:message code="dhr.step.familyReferent.label" />
             <span><g:message code="dhr.step.familyReferent.desc" /></span>
             <span class="error"><g:message code="${stepStates?.familyReferent?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/familyReferent" />         
           </div>
           <div class="error" id="stepForm-familyReferent-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-familyReferent" name="submit-step-familyReferent" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#subject"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#spouse"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.familyReferent != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.familyReferent}
         </div>
         </g:if>
       </div>  

       <div id="spouse">
         <form method="POST"  id="stepForm-spouse" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.spouse.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.spouse.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <g:message code="dhr.step.spouse.label" />
             <span><g:message code="dhr.step.spouse.desc" /></span>
             <span class="error"><g:message code="${stepStates?.spouse?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/spouse" />         
           </div>
           <div class="error" id="stepForm-spouse-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-spouse" name="submit-step-spouse" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#familyReferent"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#dwelling"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.spouse != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.spouse}
         </div>
         </g:if>
       </div>  

       <div id="dwelling">
         <form method="POST"  id="stepForm-dwelling" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.dwelling.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.dwelling.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <span class="tag-state tag-required"><g:message code="request.step.required" /></span>
  
             <g:message code="dhr.step.dwelling.label" />
             <span><g:message code="dhr.step.dwelling.desc" /></span>
             <span class="error"><g:message code="${stepStates?.dwelling?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/dwelling" />         
           </div>
           <div class="error" id="stepForm-dwelling-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-dwelling" name="submit-step-dwelling" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#spouse"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#resources"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.dwelling != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.dwelling}
         </div>
         </g:if>
       </div>  

       <div id="resources">
         <form method="POST"  id="stepForm-resources" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.resources.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.resources.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <span class="tag-state tag-required"><g:message code="request.step.required" /></span>
  
             <g:message code="dhr.step.resources.label" />
             <span><g:message code="dhr.step.resources.desc" /></span>
             <span class="error"><g:message code="${stepStates?.resources?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/resources" />         
           </div>
           <div class="error" id="stepForm-resources-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-resources" name="submit-step-resources" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#dwelling"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#taxes"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.resources != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.resources}
         </div>
         </g:if>
       </div>  

       <div id="taxes">
         <form method="POST"  id="stepForm-taxes" action="<g:createLink action="step" />">
           <h3>
             <span class="tag-state ${stepStates!= null ? stepStates.taxes.cssClass : 'tag-pending'}"><g:message code="${stepStates != null ? stepStates.taxes.i18nKey : 'request.step.state.uncomplete'}" /></span>
  
             <g:message code="dhr.step.taxes.label" />
             <span><g:message code="dhr.step.taxes.desc" /></span>
             <span class="error"><g:message code="${stepStates?.taxes?.errorMsg}" /></span>
           </h3>
           <div>
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/taxes" />         
           </div>
           <div class="error" id="stepForm-taxes-error"> </div>
           <!-- Input submit-->
           <input type="hidden" id="requestTypeInfo" name="requestTypeInfo" value="${requestTypeInfo}" />
           <input type="hidden" name="uuidString" value="${uuidString}" />
  
           <input type="submit" id="submit-step-taxes" name="submit-step-taxes" value="${message(code:'action.save')}" />
  
         </form>
         <div class="navTab">
  
           <a id="prev-tab" href="#resources"><g:message code="request.step.navigation.previous"/></a>
  
  
           <a id="next-tab" href="#document"><g:message code="request.step.navigation.next"/></a>
  
         </div>
         <g:if test="${helps.taxes != null}">       
         <div class="requestHelp">
           <h3><g:message code="header.help"/></h3>
           ${helps.taxes}
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
  
           <a id="prev-tab" href="#taxes"><g:message code="request.step.navigation.previous"/></a>
  
  
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
  
            <g:render template="/frontofficeRequestType/domesticHelpRequest/validation" />         
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
