
<html>
  <head>
     <title>${message(code:'gsrr.description')}</title>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'request.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'document.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/yui/calendar', file:'calendar.css')}" />
    
    <g:if test="${flash.addressesReferentialEnabled}">
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css/common', file:'autocomplete.css')}" />
    </g:if>
    <g:if test="${customCSS}">
      <link rel="stylesheet" type="text/css" href="${resource(dir:customCSS.dir,file:customCSS.file)}" />
    </g:if>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'homeFolder.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'requestCreation.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'condition.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/frontoffice',file:'autofill.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js/common',file:'calendar_fo.js')}"></script>
    
    <g:if test="${flash.addressesReferentialEnabled}">
        <script type="text/javascript" src="${resource(dir:'js/common',file:'addressAutocomplete.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/common',file:'autocomplete.js')}"></script>
    </g:if>
    <g:if test="${customJS}">
      <script src="${resource(dir:'js/common', file:'events.js')}"></script>
      <script src="${resource(dir:'js/frontoffice/alterer', file:'drop-down.js')}"></script>
      <script src="${resource(dir:'js/frontoffice/alterer', file:'alterer.js')}"></script>
      <script type="text/javascript" src="${resource(dir:customJS.dir,file:customJS.file)}"></script>
    </g:if>
    <g:if test="${specificConfigurationData}">
      <script type="text/javascript">
        zenexity.libredemat.gsrrSpecificConfigurationData = ${specificConfigurationData};
      </script>
    </g:if>
    <script type="text/javascript">
        zenexity.libredemat.contextPath = "${request.contextPath}";
    </script>
  </head>
  <body>
    <form action="${createLink(controller:'frontofficeRequest',action:'condition')}"
      method="post" id="conditionsForm">
      <input type="hidden" id="conditionsContainer" name="conditionsContainer" value="" />
      <input type="hidden" name="requestTypeLabel" value="${rqt.requestType.label}" />
    </form>
    <form action="${createLink(controller:'frontofficeRequest',action:'autofill')}"
      method="post" id="autofillForm">
      <input type="hidden" id="autofillContainer" name="autofillContainer" value="" />
      <input type="hidden" id="triggerName" name="triggerName" value="" />
      <input type="hidden" id="triggerValue" name="triggerValue" value="" />
    </form>
    <g:if test="${flash.successMessage}">
      <div class="success-box">
        <p>${flash.successMessage}</p>
      </div>
    </g:if>
    <g:if test="${flash.confirmationMessage}">
      <div class="information-box">
      	<p>${flash.confirmationMessage}</p>
      	<g:if test="${flash.confirmationMessageNotice}">
      	  <strong>${flash.confirmationMessageNotice}</strong>
      	</g:if>
      </div>
    </g:if>
    <g:if test="${flash.errorMessage}">
      <div class="error-box">
        <p>${flash.errorMessage}</p>
        <g:if test="${flash.errorMessageNotice}">
          <strong>${flash.errorMessageNotice}</strong>
        </g:if>
      </div>
    </g:if>
    
    <div id="request" class="main-box ${rqt.requestType.displayGroup == null ? '' : rqt.requestType.displayGroup.name}">
      <h2>
        <g:if test="${temporary}">
          <a href="${createLink(controller : 'frontofficeHome', action : 'logout')}" class="button">
            <g:message code="request.action.discard"/>
          </a>
        </g:if>
        <g:else>
          <a href="${createLink(controller:'frontofficeHome')}" class="button">
            <g:message code="request.action.continueLater"/>
          </a>
          <g:if test="${!isEdition}">
            <a href="${createLink(action:'deleteDraft', controller:'frontofficeRequest', params : ['id' : rqt.id, 'from' : 'edition'])}" class="button">
              <g:message code="request.action.discardDarft"/>
            </a>
          </g:if>
        </g:else>
        <g:message code="gsrr.label" /> <g:message code="message.number" args="[rqt.id]" />
        <g:if test="${rqt.requestSeason}">
          <span id="requestSeasonLabel">${rqt.requestSeason.label} (<g:formatDate formatName="format.date" date="${rqt.requestSeason.effectStart.toDate()}"/> - <g:formatDate formatName="format.date" date="${rqt.requestSeason.effectEnd.toDate()}"/>)</span>
        </g:if>
      </h2>
      <p><g:message code="request.duration.label" /><strong> : <g:message code="gsrr.duration.value" /></strong></p>
      <p>
        <g:message code="request.requiredDocuments.header" /> :
        <g:if test="${!documentTypes.isEmpty()}">
          <g:each var="documentType" in="${documentTypes}" status="index">
            <strong>${documentType?.name}${index < documentTypes.size() - 1 ? ', ' : ''}</strong>
          </g:each>
        </g:if>
        <g:else>
          <g:message code="message.none" />
        </g:else>
      </p>
      <div class="datas">
         <g:set var="requestTypeAcronym" value="gsrr" scope="request" />
         <g:render template="/frontofficeRequestType/step" /> 
      </div>

      <div  class="steps">
      <ul>


      <g:set var="stepNumber" value="1"/>


  

  
        <g:if test="${rqt.requestType.getStepAccountCompletion() && !session.proxyAgent}">
  

        <li class="${currentStep == 'homeFolder' ? 'current ' : ''}
          
            ${individual ? rqt.stepStates['homeFolder-' + params.type].state : rqt.stepStates['homeFolder'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'homeFolder' && rqt.stepStates['homeFolder'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'homeFolder'])}"
            </g:if>
          >
            
              <g:message code="${individual ? 'homeFolder.action.add' + org.apache.commons.lang.StringUtils.capitalize(params.type) : 'request.step.homeFolder.label'}" />

              

              <span class="help">
                <g:message code="request.step.message.${rqt.stepStates['homeFolder' + (individual ? '-' + params.type : '')].state}" />
              </span>
            
          </a>
        </li>

  
        </g:if>
  

  

  

  

        <li class="${currentStep == 'enfant' ? 'current ' : ''}
          
            ${rqt.stepStates['enfant'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'enfant' && rqt.stepStates['enfant'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'enfant'])}"
            </g:if>
          >
            
              <g:message code="gsrr.step.enfant.label" /> *
              <span class="help">
                
                  <g:message code="request.step.message.${rqt.stepStates['enfant'].state}" />
                
              </span>
            
          </a>
        </li>

  

  

  

  

        <li class="${currentStep == 'restauration' ? 'current ' : ''}
          
            ${rqt.stepStates['restauration'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'restauration' && rqt.stepStates['restauration'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'restauration'])}"
            </g:if>
          >
            
              <g:message code="gsrr.step.restauration.label" /> *
              <span class="help">
                
                  <g:message code="request.step.message.${rqt.stepStates['restauration'].state}" />
                
              </span>
            
          </a>
        </li>

  

  

  

  

        <li class="${currentStep == 'periscolaire' ? 'current ' : ''}
          
            ${rqt.stepStates['periscolaire'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'periscolaire' && rqt.stepStates['periscolaire'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'periscolaire'])}"
            </g:if>
          >
            
              <g:message code="gsrr.step.periscolaire.label" /> *
              <span class="help">
                
                  <g:message code="request.step.message.${rqt.stepStates['periscolaire'].state}" />
                
              </span>
            
          </a>
        </li>

  

  

  

  

        <li class="${currentStep == 'reglements' ? 'current ' : ''}
          
            ${rqt.stepStates['reglements'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'reglements' && rqt.stepStates['reglements'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'reglements'])}"
            </g:if>
          >
            
              <g:message code="gsrr.step.reglements.label" /> *
              <span class="help">
                
                  <g:message code="request.step.message.${rqt.stepStates['reglements'].state}" />
                
              </span>
            
          </a>
        </li>

  

  

  
        <g:if test="${!documentTypes.isEmpty()}">
  

  

        <li class="${currentStep == 'document' ? 'current ' : ''}
          
            ${rqt.stepStates['document'].state}
          "
          >
          <span class="number">${stepNumber++}</span>
          <a
            <g:if test="${currentStep != 'document' && rqt.stepStates['document'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'document'])}"
            </g:if>
          >
            
              
                <g:message code="request.step.document.label" />${rqt.requestType.isMandatoryDocumentStep ? ' *' :''}
              
              <span class="help">
                
                  <g:message code="request.step.message.${rqt.stepStates['document'].state}" />
                
              </span>
            
          </a>
        </li>

  

  
        </g:if>
  

  

  

        <li class="${currentStep == 'validation' ? 'current ' : ''}
          
            ${rqt.stepStates['validation'].state}
          "
          >
          <span class="number"></span>
          <a
            <g:if test="${currentStep != 'validation' && rqt.stepStates['validation'].state != 'unavailable'}">
              href="${createLink(controller:'frontofficeRequest', action : 'edit', params:['id':rqt.id,'currentStep':'validation'])}"
            </g:if>
          >
            
              <g:message code="request.step.validation.label" /> *
              <span class="help">
                
                <g:if test="${rqt.stepStates.validation.state == 'unavailable'}">
                  <g:message code="request.step.validation.allRequiredSteps" />
                </g:if>
                <g:else>
                  <g:message code="request.step.message.${rqt.stepStates['validation'].state}" />
                </g:else>
                
              </span>
            
          </a>
        </li>

  

  

		 </ul>
	  </div>
  </div>

  </body>
</html>
