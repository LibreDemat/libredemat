


  
    <fieldset class="required">
    <legend><g:message code="hcar.property.hcarSubject.label" /></legend>
    
      
            <label for="subjectId" class="required">
              <g:message code="hcar.property.subject.label" /> *
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
            <select id="subjectId" name="subjectId" <g:if test="${isEdition || rqt.stepStates[currentStep].state == 'complete'}">disabled="disabled"</g:if> class="required validate-not-first  ${rqt.stepStates['subject'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="request.property.subject.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${subjects}">
                <option value="${it.key}" ${it.key == rqt.subjectId ? 'selected="selected"': ''}>${it.value}</option>
              </g:each>
            </select>
            

    
      <label for="subjectTitle" class="required"><g:message code="hcar.property.subjectTitle.label" /> *  <span><g:message code="hcar.property.subjectTitle.help" /></span></label>
            <select id="subjectTitle" name="subjectTitle" class="required condition-isMadam-trigger  validate-not-first ${rqt.stepStates['subject'].invalidFields.contains('subjectTitle') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.subjectTitle.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['MISTER','MADAM','MISS','AGENCY','UNKNOWN']}">
                <option value="${it}" ${it == rqt.subjectTitle?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="hcar.property.subjectTitle" /></option>
              </g:each>
            </select>
            

    
      <label for="subjectMaidenName" class="required condition-isMadam-filled"><g:message code="hcar.property.subjectMaidenName.label" /> *  <span><g:message code="hcar.property.subjectMaidenName.help" /></span></label>
            <input type="text" id="subjectMaidenName" name="subjectMaidenName" value="${rqt.subjectMaidenName?.toString()}" 
                    class="required condition-isMadam-filled  validate-lastName ${rqt.stepStates['subject'].invalidFields.contains('subjectMaidenName') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.subjectMaidenName.validationError" />"  maxlength="38" />
            

    
      <label class="required"><g:message code="hcar.property.subjectBirthDate.label" /> *  <span><g:message code="hcar.property.subjectBirthDate.help" /></span></label>
            <div class="date required  validate-date required ">
              <select class="day ${rqt.stepStates['subject'].invalidFields.contains('subjectBirthDate') ? 'validation-failed' : ''}"
                id="subjectBirthDate_day"
                name="subjectBirthDate_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..31}">
                  <option value="${it}"
                    <g:if test="${rqt.subjectBirthDate?.date == it
                      || (rqt.subjectBirthDate == null && params['subjectBirthDate_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    ${it}
                  </option>
                </g:each>
              </select>
              <select class="month ${rqt.stepStates['subject'].invalidFields.contains('subjectBirthDate') ? 'validation-failed' : ''}"
                id="subjectBirthDate_month"
                name="subjectBirthDate_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..12}">
                  <option value="${it}"
                    <g:if test="${rqt.subjectBirthDate?.month == (it - 1)
                      || (rqt.subjectBirthDate == null && params['subjectBirthDate_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year ${rqt.stepStates['subject'].invalidFields.contains('subjectBirthDate') ? 'validation-failed' : ''}"
                id="subjectBirthDate_year"
                name="subjectBirthDate_year"
                value="${rqt.subjectBirthDate ? rqt.subjectBirthDate.year + 1900 : params['subjectBirthDate_year']}"
                title="<g:message code="hcar.property.subjectBirthDate.validationError" />" />
            </div>
            

    
      <label for="subjectBirthCity" class="required"><g:message code="hcar.property.subjectBirthCity.label" /> *  <span><g:message code="hcar.property.subjectBirthCity.help" /></span></label>
            <input type="text" id="subjectBirthCity" name="subjectBirthCity" value="${rqt.subjectBirthCity?.toString()}" 
                    class="required  validate-city ${rqt.stepStates['subject'].invalidFields.contains('subjectBirthCity') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.subjectBirthCity.validationError" />"  maxlength="32" />
            

    
      <label for="subjectBirthCountry" class="required"><g:message code="hcar.property.subjectBirthCountry.label" /> *  <span><g:message code="hcar.property.subjectBirthCountry.help" /></span></label>
            <input type="text" id="subjectBirthCountry" name="subjectBirthCountry" value="${rqt.subjectBirthCountry?.toString()}" 
                    class="required   ${rqt.stepStates['subject'].invalidFields.contains('subjectBirthCountry') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.subjectBirthCountry.validationError" />"  maxlength="50" />
            

    
    </fieldset>
  

  
    <fieldset class="required">
    <legend><g:message code="hcar.property.legalAccess.label" /></legend>
    
      <label class="required"><g:message code="hcar.property.legalAccessPresence.label" /> *  <span><g:message code="hcar.property.legalAccessPresence.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['subject'].invalidFields.contains('legalAccessPresence') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="legalAccessPresence_${it ? 'yes' : 'no'}" class="required condition-isLegalAccessPresence-trigger  validate-one-required boolean" title="" value="${it}" name="legalAccessPresence" ${it == rqt.legalAccessPresence ? 'checked="checked"': ''} />
                <label for="legalAccessPresence_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      <label for="legalAccessKind" class="required condition-isLegalAccessPresence-filled"><g:message code="hcar.property.legalAccessKind.label" /> *  <span><g:message code="hcar.property.legalAccessKind.help" /></span></label>
            <select id="legalAccessKind" name="legalAccessKind" class="required condition-isLegalAccessPresence-filled  validate-not-first ${rqt.stepStates['subject'].invalidFields.contains('legalAccessKind') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.legalAccessKind.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['SAFEGUARDING_JUSTICE','GUARDIANSHIP','CURATORSHIP']}">
                <option value="${it}" ${it == rqt.legalAccessKind?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="hcar.property.legalAccessKind" /></option>
              </g:each>
            </select>
            

    
      <label for="legalAccessRepresentativeKind" class="required condition-isLegalAccessPresence-filled"><g:message code="hcar.property.legalAccessRepresentativeKind.label" /> *  <span><g:message code="hcar.property.legalAccessRepresentativeKind.help" /></span></label>
            <select id="legalAccessRepresentativeKind" name="legalAccessRepresentativeKind" class="required condition-isLegalAccessPresence-filled condition-isOtherLegalAccessRepresentative-trigger  validate-not-first ${rqt.stepStates['subject'].invalidFields.contains('legalAccessRepresentativeKind') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.legalAccessRepresentativeKind.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['FAMILY_MEMBER','AGENCY','OTHER']}">
                <option value="${it}" ${it == rqt.legalAccessRepresentativeKind?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="hcar.property.legalAccessRepresentativeKind" /></option>
              </g:each>
            </select>
            

    
      <label for="legalAccessRepresentativeKindDetail" class="required condition-isOtherLegalAccessRepresentative-filled"><g:message code="hcar.property.legalAccessRepresentativeKindDetail.label" /> *  <span><g:message code="hcar.property.legalAccessRepresentativeKindDetail.help" /></span></label>
            <input type="text" id="legalAccessRepresentativeKindDetail" name="legalAccessRepresentativeKindDetail" value="${rqt.legalAccessRepresentativeKindDetail?.toString()}" 
                    class="required condition-isOtherLegalAccessRepresentative-filled   ${rqt.stepStates['subject'].invalidFields.contains('legalAccessRepresentativeKindDetail') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.legalAccessRepresentativeKindDetail.validationError" />"  maxlength="80" />
            

    
      <label for="legalAccessRepresentativeName" class="required condition-isLegalAccessPresence-filled"><g:message code="hcar.property.legalAccessRepresentativeName.label" /> *  <span><g:message code="hcar.property.legalAccessRepresentativeName.help" /></span></label>
            <input type="text" id="legalAccessRepresentativeName" name="legalAccessRepresentativeName" value="${rqt.legalAccessRepresentativeName?.toString()}" 
                    class="required condition-isLegalAccessPresence-filled  validate-lastName ${rqt.stepStates['subject'].invalidFields.contains('legalAccessRepresentativeName') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.legalAccessRepresentativeName.validationError" />"  maxlength="38" />
            

    
      <label for="legalAccessRepresentativeFirstName" class="required condition-isLegalAccessPresence-filled"><g:message code="hcar.property.legalAccessRepresentativeFirstName.label" /> *  <span><g:message code="hcar.property.legalAccessRepresentativeFirstName.help" /></span></label>
            <input type="text" id="legalAccessRepresentativeFirstName" name="legalAccessRepresentativeFirstName" value="${rqt.legalAccessRepresentativeFirstName?.toString()}" 
                    class="required condition-isLegalAccessPresence-filled  validate-firstName ${rqt.stepStates['subject'].invalidFields.contains('legalAccessRepresentativeFirstName') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.legalAccessRepresentativeFirstName.validationError" />"  maxlength="38" />
            

    
    </fieldset>
  

  
    <fieldset class="required">
    <legend><g:message code="hcar.property.family.label" /></legend>
    
      <label for="familyStatus" class="required"><g:message code="hcar.property.familyStatus.label" /> *  <span><g:message code="hcar.property.familyStatus.help" /></span></label>
            <select id="familyStatus" name="familyStatus" class="required  validate-not-first ${rqt.stepStates['subject'].invalidFields.contains('familyStatus') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.familyStatus.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['MARRIED','SINGLE','DIVORCED','WIDOW','COMMON_LAW_MARRIAGE','SEPARATED','P_A_C_S','OTHER']}">
                <option value="${it}" ${it == rqt.familyStatus?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="hcar.property.familyStatus" /></option>
              </g:each>
            </select>
            

    
      <label class="required"><g:message code="hcar.property.familyFamilyDependents.label" /> *  <span><g:message code="hcar.property.familyFamilyDependents.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['subject'].invalidFields.contains('familyFamilyDependents') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="familyFamilyDependents_${it ? 'yes' : 'no'}" class="required condition-isFamilyDependents-trigger  validate-one-required boolean" title="" value="${it}" name="familyFamilyDependents" ${it == rqt.familyFamilyDependents ? 'checked="checked"': ''} />
                <label for="familyFamilyDependents_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
    </fieldset>
  

  
    <div class="collection required condition-isFamilyDependents-filled summary-box">
      <h4 class="required condition-isFamilyDependents-filled"><g:message code="hcar.property.familyDependents.label" /> 
        <span><g:message code="hcar.property.familyDependents.help" /></span>
      </h4>
      <p>
        <g:message code="request.message.howToAddCollectionItem" />
        <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'subject', 'currentCollection':'familyDependents', 'collectionIndex':(rqt.familyDependents ? rqt.familyDependents.size() : 0)])}" style="font-size:1.3em;" />
          ${message(code:'request.action.newCollectionItem')}
        </a>
      </p>
    <g:each var="it" in="${rqt.familyDependents}" status="index">
      <div class="item">
        <dl>
        <dt class="head"><g:message code="hcar.property.familyDependents.label" /> : ${index + 1}</dt>
        <dd class="head">
          <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'subject', 'currentCollection':'familyDependents', 'collectionIndex':index])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>&nbsp;
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'subject', 'currentCollection':'familyDependents', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
        </dd>
    
        <dt><g:message code="hcar.property.familyDependentLastName.label" /></dt>
        <dd class="${rqt.stepStates['subject'].invalidFields.contains('familyDependents[' + index + '].familyDependentLastName') ? 'validation-failed' : ''}">${it.familyDependentLastName?.toString()}</dd>
    
        <dt><g:message code="hcar.property.familyDependentFirstName.label" /></dt>
        <dd class="${rqt.stepStates['subject'].invalidFields.contains('familyDependents[' + index + '].familyDependentFirstName') ? 'validation-failed' : ''}">${it.familyDependentFirstName?.toString()}</dd>
    
        <dt><g:message code="hcar.property.familyDependentBirthDate.label" /></dt>
        <dd class="${rqt.stepStates['subject'].invalidFields.contains('familyDependents[' + index + '].familyDependentBirthDate') ? 'validation-failed' : ''}"><g:formatDate formatName="format.date" date="${it.familyDependentBirthDate}"/></dd>
    
        <dt><g:message code="hcar.property.familyDependentActualSituation.label" /></dt>
        
              <dd class="${rqt.stepStates['subject'].invalidFields.contains('familyDependents[' + index + '].familyDependentActualSituation') ? 'validation-failed' : ''}">
                <g:if test="${it.familyDependentActualSituation}">
                  <g:capdematEnumToField var="${it.familyDependentActualSituation}" i18nKeyPrefix="hcar.property.familyDependentActualSituation" />
                </g:if>
              </dd>
              
    
        </dl>
      </div>
    </g:each>
    </div>
  

