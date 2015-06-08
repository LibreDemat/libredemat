


  
    
    
            <label for="subjectId" class="required">
              <g:message code="pcr.property.subject.label" /> *
              <span><g:message code="request.property.subject.help" /></span>
              <g:if test="${rqt.stepStates[currentStep].state != 'complete' && !rqt.requestType.getStepAccountCompletion()}">
                <g:if test="${!org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_NONE.equals(subjectPolicy)}">
                  <g:if test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_ADULT.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:if>
                  <g:elseif test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_CHILD.equals(subjectPolicy)}">
                    <span>(<a id="addSubjectLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addSubject" /></a>)</span>
                  </g:elseif>
                  <g:elseif test="${org.libredemat.service.request.IRequestWorkflowService.SUBJECT_POLICY_INDIVIDUAL.equals(subjectPolicy)}">
                    <span>(<a id="addAdultLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'adult', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addAdult" /></a>
                    <g:message code="message.or" />
                    <a id="addChildLink" href="${createLink(controller : 'frontofficeRequest', action : 'individual', params : ['type' : 'child', 'requestId' : rqt.id])}"><g:message code="homeFolder.action.addChild" /></a>)</span>
                  </g:elseif>
                </g:if>
              </g:if>
            </label>
            <select id="subjectId" name="subjectId" <g:if test="${isEdition || rqt.stepStates[currentStep].state == 'complete'}">disabled="disabled"</g:if> class="required validate-not-first autofill-subjectFilling-trigger ${rqt.stepStates['subject'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="request.property.subject.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${subjects}">
                <option value="${it.key}" ${it.key == rqt.subjectId ? 'selected="selected"': ''}>${it.value}</option>
              </g:each>
            </select>
            

  

  
    
    <label class="required"><g:message code="pcr.property.subjectAddress.label" /> *  <span><g:message code="pcr.property.subjectAddress.help" /></span></label>
            <div id="subjectAddress" class="address required autofill-subjectFilling-listener-Address ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress') ? 'validation-failed' : ''}">
            <label for="subjectAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input disabled=disabled type="text" class="validate-addressLine38 ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.additionalDeliveryInformation}" maxlength="38" id="subjectAddress.additionalDeliveryInformation" name="subjectAddress.additionalDeliveryInformation" />  
            <label for="subjectAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input disabled=disabled type="text" class="validate-addressLine38 ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.additionalGeographicalInformation}" maxlength="38" id="subjectAddress.additionalGeographicalInformation" name="subjectAddress.additionalGeographicalInformation" />
            <label for="subjectAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="subjectAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input disabled=disabled type="text" class="line1 validate-streetNumber ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.streetNumber}" size="5" maxlength="5" id="subjectAddress_streetNumber" name="subjectAddress.streetNumber" autocomplete="off" />
            <input disabled=disabled type="text" class="line2 required validate-streetName ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.streetName}" maxlength="32" id="subjectAddress_streetName" name="subjectAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" autocomplete="off" />
            <input disabled=disabled type="hidden" value="${rqt.subjectAddress?.streetMatriculation}" id="subjectAddress_streetMatriculation" name="subjectAddress.streetMatriculation" />
            <input disabled=disabled type="hidden" value="${rqt.subjectAddress?.streetRivoliCode}" id="subjectAddress_streetRivoliCode" name="subjectAddress.streetRivoliCode" />
            <label for="subjectAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input disabled=disabled type="text" class="validate-addressLine38 ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.placeNameOrService}" maxlength="38" id="subjectAddress.placeNameOrService" name="subjectAddress.placeNameOrService" />
            <label for="subjectAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="subjectAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input disabled=disabled type="text" class="line1 required validate-postalCode ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.postalCode}" size="5" maxlength="5" id="subjectAddress_postalCode" name="subjectAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" autocomplete="off" />
            <input disabled=disabled type="text" class="line2 required validate-city ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.city') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.city}" maxlength="32" id="subjectAddress_city" name="subjectAddress.city" title="<g:message code="address.property.city.validationError" />" autocomplete="off" />
            <input disabled=disabled type="hidden" value="${rqt.subjectAddress?.cityInseeCode}" id="subjectAddress_cityInseeCode" name="subjectAddress.cityInseeCode" />
            <label for="subjectAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input disabled=disabled type="text" class="validate-addressLine38 ${rqt.stepStates['subject'].invalidFields.contains('subjectAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.subjectAddress?.countryName}" maxlength="38" id="subjectAddress.countryName" name="subjectAddress.countryName" />
            </div>
            

  

