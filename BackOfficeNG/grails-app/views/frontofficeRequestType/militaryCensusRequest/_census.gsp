


  
    
            <label for="subjectId" class="required">
              <g:message code="mcr.property.subject.label" /> *
              <span><g:message code="request.property.subject.help" /></span>
              <g:if test="${rqt.stepStates[currentStep].state != 'complete' && !rqt.requestType.getStepAccountCompletion()}">
                <g:if test="${!fr.cg95.cvq.service.request.IRequestWorkflowService.SUBJECT_POLICY_NONE.equals(subjectPolicy)}">
                  <g:if test="${fr.cg95.cvq.service.request.IRequestWorkflowService.SUBJECT_POLICY_ADULT.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:if>
                  <g:elseif test="${fr.cg95.cvq.service.request.IRequestWorkflowService.SUBJECT_POLICY_CHILD.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:elseif>
                  <g:elseif test="${fr.cg95.cvq.service.request.IRequestWorkflowService.SUBJECT_POLICY_INDIVIDUAL.equals(subjectPolicy)}">
                    <span>(<a id="addAdultLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addAdult" /></a>
                    <g:message code="message.or" />
                    <a id="addChildLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addChild" /></a>)</span>
                  </g:elseif>
                </g:if>
              </g:if>
            </label>
            <select id="subjectId" name="subjectId" <g:if test="${isEdition || rqt.stepStates[currentStep].state == 'complete'}">disabled="disabled"</g:if> class="required validate-not-first autofill-subjectFilling-trigger ${rqt.stepStates['census'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="request.property.subject.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${subjects}">
                <option value="${it.key}" ${it.key == rqt.subjectId ? 'selected="selected"': ''}>${it.value}</option>
              </g:each>
            </select>
            

  

  
    <label for="childTitle" class="required"><g:message code="mcr.property.childTitle.label" /> *  <span><g:message code="mcr.property.childTitle.help" /></span></label>
            <select id="childTitle" name="childTitle" class="required  validate-not-first ${rqt.stepStates['census'].invalidFields.contains('childTitle') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childTitle.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['MISTER','MADAM','MISS','AGENCY','UNKNOWN']}">
                <option value="${it}" ${it == rqt.childTitle?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="mcr.property.childTitle" /></option>
              </g:each>
            </select>
            

  

  
    <label for="maidenName" class=""><g:message code="mcr.property.maidenName.label" />   <span><g:message code="mcr.property.maidenName.help" /></span></label>
            <input type="text" id="maidenName" name="maidenName" value="${rqt.maidenName?.toString()}" 
                    class=" autofill-subjectFilling-listener-LastName validate-lastName ${rqt.stepStates['census'].invalidFields.contains('maidenName') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.maidenName.validationError" />"  maxlength="38" />
            

  

  
    <label for="childBirthCountry" class="required"><g:message code="mcr.property.childBirthCountry.label" /> *  <span><g:message code="mcr.property.childBirthCountry.help" /></span></label>
            <select id="childBirthCountry" name="childBirthCountry" class="required  validate-not-first ${rqt.stepStates['census'].invalidFields.contains('childBirthCountry') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childBirthCountry.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['UNKNOWN','AF','ZA','AL','DZ','DE','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BJ','BS','BH','BD','BB','PW','BE','BZ','BM','BT','BY','MM','BO','BA','BW','BR','BN','BG','BF','BI','CI','KH','CM','CA','CV','CL','CN','CY','CO','KM','CG','KP','KR','CR','HR','CU','DK','DJ','DM','EG','AE','EC','ER','ES','EE','US','ET','FI','FR','GE','GA','GM','GH','GI','GR','GD','GL','GP','GU','GT','GN','GQ','GW','GY','GF','HT','HN','HK','HU','CK','FJ','MH','SB','IN','ID','IR','IQ','IE','IS','IL','IT','JM','JP','JO','KZ','KE','KG','KI','KW','LA','LS','LV','LB','LR','LY','LI','LT','LU','MG','MY','MW','MV','ML','MT','MA','MU','MR','MX','FM','MD','MC','MN','MZ','NP','NA','NR','NI','NE','NG','NU','NO','NZ','OM','UG','UZ','PE','PK','PA','PG','PY','NL','PH','PL','PT','QA','CF','CD','DO','CZ','RO','GB','RU','RW','SN','KN','SM','VA','VC','LC','SV','WS','ST','SC','SL','SG','SI','SK','SO','SD','LK','SE','CH','SR','SZ','SY','TW','TJ','TZ','TD','TH','TL','TG','TO','VT','TN','TM','TR','TV','UA','UY','VU','VE','VN','YE','ZM','ZW','MK']}">
                <option value="${it}" ${it == rqt.childBirthCountry?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="mcr.property.childBirthCountry" /></option>
              </g:each>
            </select>
            

  

  
    <label for="childResidenceCountry" class=""><g:message code="mcr.property.childResidenceCountry.label" />   <span><g:message code="mcr.property.childResidenceCountry.help" /></span></label>
            <select id="childResidenceCountry" name="childResidenceCountry" class="  validate-select ${rqt.stepStates['census'].invalidFields.contains('childResidenceCountry') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childResidenceCountry.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['UNKNOWN','AF','ZA','AL','DZ','DE','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BJ','BS','BH','BD','BB','PW','BE','BZ','BM','BT','BY','MM','BO','BA','BW','BR','BN','BG','BF','BI','CI','KH','CM','CA','CV','CL','CN','CY','CO','KM','CG','KP','KR','CR','HR','CU','DK','DJ','DM','EG','AE','EC','ER','ES','EE','US','ET','FI','FR','GE','GA','GM','GH','GI','GR','GD','GL','GP','GU','GT','GN','GQ','GW','GY','GF','HT','HN','HK','HU','CK','FJ','MH','SB','IN','ID','IR','IQ','IE','IS','IL','IT','JM','JP','JO','KZ','KE','KG','KI','KW','LA','LS','LV','LB','LR','LY','LI','LT','LU','MG','MY','MW','MV','ML','MT','MA','MU','MR','MX','FM','MD','MC','MN','MZ','NP','NA','NR','NI','NE','NG','NU','NO','NZ','OM','UG','UZ','PE','PK','PA','PG','PY','NL','PH','PL','PT','QA','CF','CD','DO','CZ','RO','GB','RU','RW','SN','KN','SM','VA','VC','LC','SV','WS','ST','SC','SL','SG','SI','SK','SO','SD','LK','SE','CH','SR','SZ','SY','TW','TJ','TZ','TD','TH','TL','TG','TO','VT','TN','TM','TR','TV','UA','UY','VU','VE','VN','YE','ZM','ZW','MK']}">
                <option value="${it}" ${it == rqt.childResidenceCountry?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="mcr.property.childResidenceCountry" /></option>
              </g:each>
            </select>
            

  

  
    <label for="childPhone" class="required"><g:message code="mcr.property.childPhone.label" /> *  <span><g:message code="mcr.property.childPhone.help" /></span></label>
            <input type="text" id="childPhone" name="childPhone" value="${rqt.childPhone?.toString()}" 
                    class="required  validate-phone ${rqt.stepStates['census'].invalidFields.contains('childPhone') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childPhone.validationError" />"  maxlength="10" />
            

  

  
    <label for="childMail" class=""><g:message code="mcr.property.childMail.label" />   <span><g:message code="mcr.property.childMail.help" /></span></label>
            <input type="text" id="childMail" name="childMail" value="${rqt.childMail?.toString()}" 
                    class="  validate-email ${rqt.stepStates['census'].invalidFields.contains('childMail') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childMail.validationError" />"   />
            

  

  
    <label for="childCountry" class="required"><g:message code="mcr.property.childCountry.label" /> *  <span><g:message code="mcr.property.childCountry.help" /></span></label>
            <select id="childCountry" name="childCountry" class="required  validate-not-first ${rqt.stepStates['census'].invalidFields.contains('childCountry') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childCountry.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['NONE','AF','ZA','AL','DZ','DE','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BS','BH','BD','BB','BE','BZ','BJ','BM','BT','BY','MM','BO','BA','BW','BR','BN','BG','BF','BI','KH','CM','CA','CV','CL','CN','CY','CO','KM','CG','KP','KR','CR','HR','CU','DK','DJ','DM','EG','AE','EC','ER','ES','EE','US','ET','FI','FR','GE','GA','GM','GH','GI','GR','GD','GL','GU','GT','GN','GQ','GW','HT','HN','HK','HU','CK','FJ','MH','SB','IN','ID','IR','IQ','IE','IS','IL','IT','CI','JM','JP','JO','KZ','KE','KG','KI','KW','LA','LS','LV','LB','LR','LY','LI','LT','LU','MG','MY','MW','MV','ML','MT','MA','MU','MR','MX','FM','MD','MC','MN','MZ','NP','NA','NR','NI','NE','NG','NU','NO','NZ','OM','UG','UZ','PE','PK','PA','PG','PY','NL','PH','PL','PT','QA','CF','CD','DO','CZ','RO','GB','RU','RW','SN','KN','SM','VA','VC','LC','SV','WS','ST','SC','SL','SG','SI','SK','SO','SD','LK','SE','CH','SR','SZ','SY','TW','TJ','TZ','TD','TH','TL','TG','TO','VT','TN','TM','TR','TV','UA','UY','VU','VE','VN','YE','ZM','ZW','MK']}">
                <option value="${it}" ${it == rqt.childCountry?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="mcr.property.childCountry" /></option>
              </g:each>
            </select>
            

  

  
    <label for="childOtherCountry" class=""><g:message code="mcr.property.childOtherCountry.label" />   <span><g:message code="mcr.property.childOtherCountry.help" /></span></label>
            <select id="childOtherCountry" name="childOtherCountry" class="  validate-select ${rqt.stepStates['census'].invalidFields.contains('childOtherCountry') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childOtherCountry.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['NONE','AF','ZA','AL','DZ','DE','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BS','BH','BD','BB','BE','BZ','BJ','BM','BT','BY','MM','BO','BA','BW','BR','BN','BG','BF','BI','KH','CM','CA','CV','CL','CN','CY','CO','KM','CG','KP','KR','CR','HR','CU','DK','DJ','DM','EG','AE','EC','ER','ES','EE','US','ET','FI','FR','GE','GA','GM','GH','GI','GR','GD','GL','GU','GT','GN','GQ','GW','HT','HN','HK','HU','CK','FJ','MH','SB','IN','ID','IR','IQ','IE','IS','IL','IT','CI','JM','JP','JO','KZ','KE','KG','KI','KW','LA','LS','LV','LB','LR','LY','LI','LT','LU','MG','MY','MW','MV','ML','MT','MA','MU','MR','MX','FM','MD','MC','MN','MZ','NP','NA','NR','NI','NE','NG','NU','NO','NZ','OM','UG','UZ','PE','PK','PA','PG','PY','NL','PH','PL','PT','QA','CF','CD','DO','CZ','RO','GB','RU','RW','SN','KN','SM','VA','VC','LC','SV','WS','ST','SC','SL','SG','SI','SK','SO','SD','LK','SE','CH','SR','SZ','SY','TW','TJ','TZ','TD','TH','TL','TG','TO','VT','TN','TM','TR','TV','UA','UY','VU','VE','VN','YE','ZM','ZW','MK']}">
                <option value="${it}" ${it == rqt.childOtherCountry?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="mcr.property.childOtherCountry" /></option>
              </g:each>
            </select>
            

  

  
    <label for="childConvention" class=""><g:message code="mcr.property.childConvention.label" />   <span><g:message code="mcr.property.childConvention.help" /></span></label>
            <textarea id="childConvention" name="childConvention" class="  validate-regex ${rqt.stepStates['census'].invalidFields.contains('childConvention') ? 'validation-failed' : ''}" title="<g:message code="mcr.property.childConvention.validationError" />" rows="3" cols="" regex="^[\w\W]{0,255}$" maxlength="255">${rqt.childConvention}</textarea>
            

  

