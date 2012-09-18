<%
  def displayWidget(element, valuePrefix, namePrefix) {
      def IdRefNamePrefix = namePrefix.replace('[','.').replace(']','')
      def validationNamePrefix = namePrefix.replaceAll("\\\$\\{", "'+").replaceAll("\\}", "+'")
      def widgets = [
        'boolean' : 
            """
            <ul class="yes-no ${element.listenerConditionsClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
              <g:each in="\${[true,false]}">
              <li>
                <input type="radio" id="${IdRefNamePrefix}${element.javaFieldName}_\${it ? 'yes' : 'no'}" class="${element.htmlClass}" title="" value="\${it}" name="${namePrefix}${element.javaFieldName}" \${it == ${valuePrefix}.${element.javaFieldName} ? 'checked="checked"': ''} />
                <label for="${IdRefNamePrefix}${element.javaFieldName}_\${it ? 'yes' : 'no'}"><g:message code="message.\${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            """
        ,'checkbox' :
            """
            <ul \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'class="validation-failed"' : ''}>
              <li>
                <input type="hidden" name="_${namePrefix}${element.javaFieldName}" /><!-- Grails 1.2.x convention to bind checkboxes. -->
                <input type="checkbox" id="${IdRefNamePrefix}${element.javaFieldName}" name="${namePrefix}${element.javaFieldName}"
                       class="${element.htmlClass}"
                       title="\${message(code:'${element.i18nPrefixCode}.validationError')}"
                       \${${valuePrefix}.${element.javaFieldName} ? 'checked="checked"' : ''} value="true" />
                <label for="${IdRefNamePrefix}${element.javaFieldName}" class="${element.listenerConditionsClass}">
                  \${message(code:'${element.i18nPrefixCode}.label')}${element.mandatory ? '*' : ''}
                  <g:if test="\${availableRules.contains('$validationNamePrefix${element.javaFieldName}')}">
                  <a target="_blank"
                     href="\${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':rqt.requestType.label, 'filename':'${element.javaFieldName}']).encodeAsXML()}">
                    <span>\${message(code:'request.action.consult.rules')}</span>
                  </a>
                  </g:if>
                  <span>\${message(code:'${element.i18nPrefixCode}.help')}</span>
                </label>
              </li>
            </ul>
            """
        ,'radio' :
            """
            <ul class="${element.listenerConditionsClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
              <g:each in="\${${element.enumValuesAsString}}">
              <li>
                <input type="radio" id="${IdRefNamePrefix}${element.javaFieldName}_\${it}" class="${element.htmlClass}" value="\${it}" name="${namePrefix}${element.javaFieldName}" \${it == ${valuePrefix}.${element.javaFieldName}.toString() ? 'checked="checked"': ''} title="<g:message code="${element.i18nPrefixCode}.validationError" />" />
                <label for="${IdRefNamePrefix}${element.javaFieldName}_\${it}"><g:capdematEnumToText var="\${it}" i18nKeyPrefix="${element.i18nPrefixCode}" /></label>
              </li>
              </g:each>
            </ul>
            """
        ,'select' :
            """
            <select id="${IdRefNamePrefix}${element.javaFieldName}" name="${namePrefix}${element.javaFieldName}" class="${element.htmlClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}" title="<g:message code="${element.i18nPrefixCode}.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="\${${element.enumValuesAsString}}">
                <option value="\${it}" \${it == ${valuePrefix}.${element.javaFieldName}?.toString() ? 'selected=\"selected\"': ''}><g:capdematEnumToText var="\${it}" i18nKeyPrefix="${element.i18nPrefixCode}" /></option>
              </g:each>
            </select>
            """
        ,'address' :
            """
            <div class="address ${element.listenerConditionsClass} ${element.autofillClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
            <label for="${IdRefNamePrefix}${element.javaFieldName}.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input type="text" class="validate-addressLine38 \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.additionalDeliveryInformation}" maxlength="38" id="${IdRefNamePrefix}${element.javaFieldName}.additionalDeliveryInformation" name="${namePrefix}${element.javaFieldName}.additionalDeliveryInformation" />  
            <label for="${IdRefNamePrefix}${element.javaFieldName}.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input type="text" class="validate-addressLine38 \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.additionalGeographicalInformation}" maxlength="38" id="${IdRefNamePrefix}${element.javaFieldName}.additionalGeographicalInformation" name="${namePrefix}${element.javaFieldName}.additionalGeographicalInformation" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="${IdRefNamePrefix}${element.javaFieldName}.streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input type="text" class="line1 validate-streetNumber \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.streetNumber') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.streetNumber}" size="5" maxlength="5" id="${IdRefNamePrefix}${element.javaFieldName}.streetNumber" name="${namePrefix}${element.javaFieldName}.streetNumber" />
            <input type="text" class="line2 required validate-streetName \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.streetName') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.streetName}" maxlength="32" id="${IdRefNamePrefix}${element.javaFieldName}.streetName" name="${namePrefix}${element.javaFieldName}.streetName" title="<g:message code="address.property.streetName.validationError" />" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input type="text" class="validate-addressLine38 \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.placeNameOrService') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.placeNameOrService}" maxlength="38" id="${IdRefNamePrefix}${element.javaFieldName}.placeNameOrService" name="${namePrefix}${element.javaFieldName}.placeNameOrService" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="${IdRefNamePrefix}${element.javaFieldName}.city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input type="text" class="line1 required validate-postalCode \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.postalCode') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.postalCode}" size="5" maxlength="5" id="${IdRefNamePrefix}${element.javaFieldName}.postalCode" name="${namePrefix}${element.javaFieldName}.postalCode" title="<g:message code="address.property.postalCode.validationError" />" />
            <input type="text" class="line2 required validate-city \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.city') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.city}" maxlength="32" id="${IdRefNamePrefix}${element.javaFieldName}.city" name="${namePrefix}${element.javaFieldName}.city" title="<g:message code="address.property.city.validationError" />" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.countryName"><g:message code="address.property.countryName" /></label>
            <input type="text" class="validate-addressLine38 \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.countryName') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.countryName}" maxlength="38" id="${IdRefNamePrefix}${element.javaFieldName}.countryName" name="${namePrefix}${element.javaFieldName}.countryName" />
            </div>
            """
         ,'frenchRIB' :
            """
            <div class="address ${element.listenerConditionsClass} ${element.autofillClass} ${element.autofillClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
            <label for="${IdRefNamePrefix}${element.javaFieldName}.bankCode"><g:message code="frenchRIB.property.bankCode" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.bankCode') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.bankCode}" maxlength="5" id="${IdRefNamePrefix}${element.javaFieldName}.bankCode" name="${namePrefix}${element.javaFieldName}.bankCode" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.counterCode"><g:message code="frenchRIB.property.counterCode" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.counterCode') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.counterCode}" maxlength="5" id="${IdRefNamePrefix}${element.javaFieldName}.counterCode" name="${namePrefix}${element.javaFieldName}.counterCode" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.accountNumber"><g:message code="frenchRIB.property.accountNumber" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.accountNumber') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.accountNumber}" maxlength="11" id="${IdRefNamePrefix}${element.javaFieldName}.accountNumber" name="${namePrefix}${element.javaFieldName}.accountNumber" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.accountKey"><g:message code="frenchRIB.property.accountKey" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.accountKey') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.accountKey}" maxlength="2" id="${IdRefNamePrefix}${element.javaFieldName}.accountKey" name="${namePrefix}${element.javaFieldName}.accountKey" />
            </div>
            """
         ,'bankAccount' :
            """
            <div class="address ${element.listenerConditionsClass} ${element.autofillClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
            <label for="${IdRefNamePrefix}${element.javaFieldName}.BIC"><g:message code="bankAccount.property.BIC" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.BIC') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.BIC}" maxlength="11" id="${IdRefNamePrefix}${element.javaFieldName}.BIC" name="${namePrefix}${element.javaFieldName}.BIC" />
            <label for="${IdRefNamePrefix}${element.javaFieldName}.IBAN"><g:message code="bankAccount.property.IBAN" /></label>
            <input type="text" class="\${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}.IBAN') ? 'validation-failed' : ''}" value="\${${valuePrefix}.${element.javaFieldName}?.IBAN}" maxlength="34" id="${IdRefNamePrefix}${element.javaFieldName}.IBAN" name="${namePrefix}${element.javaFieldName}.IBAN" />
            </div>
            """
         ,'textarea' :
            """
            <textarea id="${IdRefNamePrefix}${element.javaFieldName}" name="${namePrefix}${element.javaFieldName}" class="${element.htmlClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}" title="<g:message code="${element.i18nPrefixCode}.validationError" />" rows="${element.rows}" cols="" ${element.jsRegexp} ${element.lengthLimits}>\${${valuePrefix}.${element.javaFieldName}}</textarea>
            """
         ,'localReferentialData':
            """
            <g:set var="${element.javaFieldName}Index" value="\${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'${element.javaFieldName}', 'i18nPrefixCode':'${element.i18nPrefixCode}', 'htmlClass':'${element.htmlClass.replace('validate-localReferentialData','')}', 
                              'lrEntries': lrTypes.${element.javaFieldName}.entries, 'depth':0]" />
            """
         ,'date' :
            """
            <div class="date ${element.htmlClass} ${element.listenerConditionsClass} ${element.autofillClass}">
              <select class="day \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}"
                id="${IdRefNamePrefix}${element.javaFieldName}_day"
                name="${namePrefix}${element.javaFieldName}_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="\${1..31}">
                  <option value="\${it}"
                    <g:if test="\${${valuePrefix}.${element.javaFieldName}?.date == it
                      || (${valuePrefix}.${element.javaFieldName} == null && params['${namePrefix}${element.javaFieldName}_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    \${it}
                  </option>
                </g:each>
              </select>
              <select class="month \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}"
                id="${IdRefNamePrefix}${element.javaFieldName}_month"
                name="${namePrefix}${element.javaFieldName}_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="\${1..12}">
                  <option value="\${it}"
                    <g:if test="\${${valuePrefix}.${element.javaFieldName}?.month == (it - 1)
                      || (${valuePrefix}.${element.javaFieldName} == null && params['${namePrefix}${element.javaFieldName}_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.\${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}"
                id="${IdRefNamePrefix}${element.javaFieldName}_year"
                name="${namePrefix}${element.javaFieldName}_year"
                value="\${${valuePrefix}.${element.javaFieldName} ? ${valuePrefix}.${element.javaFieldName}.year + 1900 : params['${validationNamePrefix}${element.javaFieldName}_year']}"
                title="<g:message code="${element.i18nPrefixCode}.validationError" />" />
            </div>
            """
         ,'text' :
            """
            <input type="text" id="${IdRefNamePrefix}${element.javaFieldName}" name="${namePrefix}${element.javaFieldName}" value="\${${valuePrefix}.${element.javaFieldName}?.toString()}" 
                    class="${element.htmlClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}" title="<g:message code="${element.i18nPrefixCode}.validationError" />" ${element.jsRegexp} ${element.lengthLimits} />
            """
         ,'number' :
            """
            <input type="text" id="${IdRefNamePrefix}${element.javaFieldName}" name="${namePrefix}${element.javaFieldName}" value="\${formatNumber(number: ${valuePrefix}.${element.javaFieldName}, type: 'number')}"
                    class="${element.htmlClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}" title="<g:message code="${element.i18nPrefixCode}.validationError" />" ${element.jsRegexp} ${element.lengthLimits} />
            """
         ,'subject' :
            """
            <label for="${IdRefNamePrefix}${element.javaFieldName}Id" class="${element.listenerConditionsClass}"><g:message code="${acronym}.property.subject.label" /> ${element.mandatory ? '*' : ''}  <span><g:message code="${element.i18nPrefixCode}.help" /></span></label>
            <select id="${IdRefNamePrefix}${element.javaFieldName}Id" name="subjectId" <g:if test="\${isEdition}">disabled="disabled"</g:if> class="required validate-not-first ${element.autofillClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('subjectId') ? 'validation-failed' : ''}" title="<g:message code="${element.i18nPrefixCode}.validationError" /> ">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="\${subjects}">
                <option value="\${it.key}" \${it.key == rqt.subjectId ? 'selected=\"selected\"': ''}>\${it.value}</option>
              </g:each>
            </select>
            """
         ,'requester' :
            """
              <g:render template="/frontofficeRequestType/widget/requester" model="['requester':requester, 'hasHomeFolder':hasHomeFolder]" />
            """
         ,'label' :
            """<label class="${element.listenerConditionsClass}"><g:message code="${element.i18nPrefixCode}.label" />${element.mandatory ? ' *' : ''} <span><g:message code="${element.i18nPrefixCode}.help" /></span></label>"""
         ,'labelWithFor' :
            """<label for="${IdRefNamePrefix}${element.javaFieldName}" class="${element.listenerConditionsClass}"><g:message code="${element.i18nPrefixCode}.label" /> ${element.mandatory ? '*' : ''}  <span><g:message code="${element.i18nPrefixCode}.help" /></span></label>"""
         ,'acceptance' :
            """
              <label class="${element.listenerConditionsClass}">
                <g:message code="${element.i18nPrefixCode}.label" /> ${element.mandatory ? '*' : ''}
                <g:if test="\${availableRules.contains('$validationNamePrefix${element.javaFieldName}')}">
                  <a target="_blank" href="\${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':requestTypeLabel, 'filename':'${element.javaFieldName}']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a>
                </g:if>
                <span><g:message code="${element.i18nPrefixCode}.help" /></span>
              </label>
              <ul class="yes-no ${element.listenerConditionsClass} \${rqt.stepStates['${step.name}'].invalidFields.contains('$validationNamePrefix${element.javaFieldName}') ? 'validation-failed' : ''}">
                <g:each in="\${[true,false]}">
                  <li>
                    <input type="radio" id="${IdRefNamePrefix}${element.javaFieldName}_\${it ? 'yes' : 'no'}" class="${element.htmlClass}" title="" value="\${it}" name="${namePrefix}${element.javaFieldName}" \${it == ${valuePrefix}.${element.javaFieldName} ? 'checked="checked"': ''} />
                    <label for="${IdRefNamePrefix}${element.javaFieldName}_\${it ? 'yes' : 'no'}"><g:message code="message.\${it ? 'yes' : 'no'}" /></label>
                  </li>
                </g:each>
              </ul>
            """
      ]

      def output

      switch (element.widget) {
        case ['requester', 'subject', 'acceptance', 'checkbox']:
          output = widgets[element.widget]
          break
        case ['radio', 'boolean', 'localReferentialData', 'address', 'date']:
          output = widgets['label'] + widgets[element.widget]
          break
        case ['decimal', 'double', 'float']:
          output = widgets['labelWithFor'] + widgets['number']
          break
        default:
          output = widgets['labelWithFor']
          output += (widgets[element.widget] != null) ? widgets[element.widget] : widgets['text']
          break
      }

      println output
  }
%>
  <g:set var="currentCollectionItem" value="\${rqt?.${element.javaFieldName}.size() > collectionIndex ? rqt.${element.javaFieldName}.get(collectionIndex) : null}" />
  <h4>
    \${message(code:'${element.i18nPrefixCode}.label')}
    <span>
      <g:if test="\${currentCollectionItem != null}">
        \${message(code:'request.message.editCollectionItem', args:[collectionIndex + 1])}
      </g:if>
      <g:else>
        \${message(code:'request.message.addCollectionItem')}
      </g:else>
    </span>
  </h4>
  <% element.elements.each { subElement -> %>
    <% displayWidget(subElement, 'currentCollectionItem?', element.javaFieldName + '[${collectionIndex}].' ) %>
  <% } %>
  <input type="hidden" name="currentCollection" value="\${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="\${collectionIndex}" />
  <input type="submit" id="collectionSave" name="collectionSave" value="\${message(code:'action.' + (currentCollectionItem != null ? 'save' : 'add'))}" />
  <a href="\${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': '${step.name}'])}">
    \${message(code:'request.action.backToMainForm')}
  </a>
  
