


  
    <fieldset class="" id="CurrentSchool">
    <legend><g:message code="sgr.property.currentSchool.label" /></legend>
    
      
      <label class="required"><g:message code="sgr.property.currentSchoolName.label" /> *  <span><g:message code="sgr.property.currentSchoolName.help" /></span></label>
            <g:set var="currentSchoolNameIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'currentSchoolName', 'i18nPrefixCode':'sgr.property.currentSchoolName', 'htmlClass':'required condition-isCurrentSchoolNameOther-trigger  ', 
                              'lrEntries': lrTypes.currentSchoolName.entries, 'depth':0]" />
            

    
      
      <label for="currentSchoolNamePrecision" class="required condition-isCurrentSchoolNameOther-filled"><g:message code="sgr.property.currentSchoolNamePrecision.label" /> *  <span><g:message code="sgr.property.currentSchoolNamePrecision.help" /></span></label>
            <input  type="text" id="currentSchoolNamePrecision"
                   name="currentSchoolNamePrecision"
                   value="${rqt.currentSchoolNamePrecision?.toString()}"
                   class="required condition-isCurrentSchoolNameOther-filled  validate-string ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolNamePrecision') ? 'validation-failed' : ''}"
                   title="<g:message code="sgr.property.currentSchoolNamePrecision.validationError" />"   />
            

    
      
      <label class="required condition-isCurrentSchoolNameOther-filled"><g:message code="sgr.property.currentSchoolAddress.label" /> *  <span><g:message code="sgr.property.currentSchoolAddress.help" /></span></label>
            <div id="currentSchoolAddress" class="address required condition-isCurrentSchoolNameOther-filled  ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress') ? 'validation-failed' : ''}">
            <label for="currentSchoolAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.additionalDeliveryInformation}" maxlength="38" id="currentSchoolAddress.additionalDeliveryInformation" name="currentSchoolAddress.additionalDeliveryInformation" />  
            <label for="currentSchoolAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.additionalGeographicalInformation}" maxlength="38" id="currentSchoolAddress.additionalGeographicalInformation" name="currentSchoolAddress.additionalGeographicalInformation" />
            <label for="currentSchoolAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="currentSchoolAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input  type="text" class="line1 validate-streetNumber ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.streetNumber}" size="5" maxlength="5" id="currentSchoolAddress_streetNumber" name="currentSchoolAddress.streetNumber" autocomplete="off" />
            <input  type="text" class="line2 required validate-streetName ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.streetName}" maxlength="32" id="currentSchoolAddress_streetName" name="currentSchoolAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.currentSchoolAddress?.streetMatriculation}" id="currentSchoolAddress_streetMatriculation" name="currentSchoolAddress.streetMatriculation" />
            <input  type="hidden" value="${rqt.currentSchoolAddress?.streetRivoliCode}" id="currentSchoolAddress_streetRivoliCode" name="currentSchoolAddress.streetRivoliCode" />
            <label for="currentSchoolAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.placeNameOrService}" maxlength="38" id="currentSchoolAddress.placeNameOrService" name="currentSchoolAddress.placeNameOrService" />
            <label for="currentSchoolAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="currentSchoolAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input  type="text" class="line1 required validate-postalCode ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.postalCode}" size="5" maxlength="5" id="currentSchoolAddress_postalCode" name="currentSchoolAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" autocomplete="off" />
            <input  type="text" class="line2 required validate-city ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.city') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.city}" maxlength="32" id="currentSchoolAddress_city" name="currentSchoolAddress.city" title="<g:message code="address.property.city.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.currentSchoolAddress?.cityInseeCode}" id="currentSchoolAddress_cityInseeCode" name="currentSchoolAddress.cityInseeCode" />
            <label for="currentSchoolAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['currentStudies'].invalidFields.contains('currentSchoolAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.currentSchoolAddress?.countryName}" maxlength="38" id="currentSchoolAddress.countryName" name="currentSchoolAddress.countryName" />
            </div>
            

    
    </fieldset>
  

  
    <fieldset class="required" id="ALevelsInformations">
    <legend><g:message code="sgr.property.aLevelsInformations.label" /></legend>
    
      
      <label for="alevelsDate" class="required"><g:message code="sgr.property.alevelsDate.label" /> *  <span><g:message code="sgr.property.alevelsDate.help" /></span></label>
            <input  type="text" id="alevelsDate"
                   name="alevelsDate"
                   value="${rqt.alevelsDate?.toString()}"
                   class="required  validate-regex ${rqt.stepStates['currentStudies'].invalidFields.contains('alevelsDate') ? 'validation-failed' : ''}"
                   title="<g:message code="sgr.property.alevelsDate.validationError" />" regex="^\d{2,4}$" maxlength="4" />
            

    
      
      <label for="alevels" class="required"><g:message code="sgr.property.alevels.label" /> *  <span><g:message code="sgr.property.alevels.help" /></span></label>
            <select id="alevels" name="alevels" class="required  validate-not-first ${rqt.stepStates['currentStudies'].invalidFields.contains('alevels') ? 'validation-failed' : ''}" title="<g:message code="sgr.property.alevels.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['ES','L','S','STG','STI','STL','ST2S','STAV','TMD','H','P']}">
                <option value="${it}" ${it == rqt.alevels?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="sgr.property.alevels" /></option>
              </g:each>
            </select>
            

    
    </fieldset>
  

  
    <fieldset class="" id="CurrentStudiesInformations">
    <legend><g:message code="sgr.property.currentStudiesInformations.label" /></legend>
    
      
      <label for="currentStudiesDiploma" class="required"><g:message code="sgr.property.currentStudiesDiploma.label" /> *  <span><g:message code="sgr.property.currentStudiesDiploma.help" /></span></label>
            <select id="currentStudiesDiploma" name="currentStudiesDiploma" class="required condition-isInOtherStudies-trigger  validate-not-first ${rqt.stepStates['currentStudies'].invalidFields.contains('currentStudiesDiploma') ? 'validation-failed' : ''}" title="<g:message code="sgr.property.currentStudiesDiploma.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['LICENCE','LICENCE_PRO','MASTER','BTS','DUT','OTHER_STUDIES']}">
                <option value="${it}" ${it == rqt.currentStudiesDiploma?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="sgr.property.currentStudiesDiploma" /></option>
              </g:each>
            </select>
            

    
      
      <label for="otherStudiesLabel" class="required condition-isInOtherStudies-filled"><g:message code="sgr.property.otherStudiesLabel.label" /> *  <span><g:message code="sgr.property.otherStudiesLabel.help" /></span></label>
            <input  type="text" id="otherStudiesLabel"
                   name="otherStudiesLabel"
                   value="${rqt.otherStudiesLabel?.toString()}"
                   class="required condition-isInOtherStudies-filled  validate-string ${rqt.stepStates['currentStudies'].invalidFields.contains('otherStudiesLabel') ? 'validation-failed' : ''}"
                   title="<g:message code="sgr.property.otherStudiesLabel.validationError" />"   />
            

    
      
      <label for="currentStudiesLevel" class="required"><g:message code="sgr.property.currentStudiesLevel.label" /> *  <span><g:message code="sgr.property.currentStudiesLevel.help" /></span></label>
            <select id="currentStudiesLevel" name="currentStudiesLevel" class="required  validate-not-first ${rqt.stepStates['currentStudies'].invalidFields.contains('currentStudiesLevel') ? 'validation-failed' : ''}" title="<g:message code="sgr.property.currentStudiesLevel.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['FIRST_YEAR','SECOND_YEAR','THIRD_YEAR']}">
                <option value="${it}" ${it == rqt.currentStudiesLevel?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="sgr.property.currentStudiesLevel" /></option>
              </g:each>
            </select>
            

    
      
      <label class="required"><g:message code="sgr.property.sandwichCourses.label" /> *  <span><g:message code="sgr.property.sandwichCourses.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['currentStudies'].invalidFields.contains('sandwichCourses') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="sandwichCourses_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="sandwichCourses" ${it == rqt.sandwichCourses ? 'checked="checked"': ''} />
                <label for="sandwichCourses_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required"><g:message code="sgr.property.abroadInternship.label" /> *  <span><g:message code="sgr.property.abroadInternship.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternship') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="abroadInternship_${it ? 'yes' : 'no'}" class="required condition-makesAbroadInternship-trigger  validate-one-required boolean" title="" value="${it}" name="abroadInternship" ${it == rqt.abroadInternship ? 'checked="checked"': ''} />
                <label for="abroadInternship_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

    
      
      <label class="required condition-makesAbroadInternship-filled"><g:message code="sgr.property.abroadInternshipStartDate.label" /> *  <span><g:message code="sgr.property.abroadInternshipStartDate.help" /></span></label>
            <div class="date required condition-makesAbroadInternship-filled  validate-date required condition-makesAbroadInternship-filled ">
              <select class="day ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipStartDate') ? 'validation-failed' : ''}"
                id="abroadInternshipStartDate_day"
                name="abroadInternshipStartDate_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..31}">
                  <option value="${it}"
                    <g:if test="${rqt.abroadInternshipStartDate?.date == it
                      || (rqt.abroadInternshipStartDate == null && params['abroadInternshipStartDate_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    ${it}
                  </option>
                </g:each>
              </select>
              <select class="month ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipStartDate') ? 'validation-failed' : ''}"
                id="abroadInternshipStartDate_month"
                name="abroadInternshipStartDate_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..12}">
                  <option value="${it}"
                    <g:if test="${rqt.abroadInternshipStartDate?.month == (it - 1)
                      || (rqt.abroadInternshipStartDate == null && params['abroadInternshipStartDate_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipStartDate') ? 'validation-failed' : ''}"
                id="abroadInternshipStartDate_year"
                name="abroadInternshipStartDate_year"
                value="${rqt.abroadInternshipStartDate ? rqt.abroadInternshipStartDate.year + 1900 : params['abroadInternshipStartDate_year']}"
                title="<g:message code="sgr.property.abroadInternshipStartDate.validationError" />" />
            </div>
            

    
      
      <label class="required condition-makesAbroadInternship-filled"><g:message code="sgr.property.abroadInternshipEndDate.label" /> *  <span><g:message code="sgr.property.abroadInternshipEndDate.help" /></span></label>
            <div class="date required condition-makesAbroadInternship-filled  validate-date required condition-makesAbroadInternship-filled ">
              <select class="day ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipEndDate') ? 'validation-failed' : ''}"
                id="abroadInternshipEndDate_day"
                name="abroadInternshipEndDate_day">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..31}">
                  <option value="${it}"
                    <g:if test="${rqt.abroadInternshipEndDate?.date == it
                      || (rqt.abroadInternshipEndDate == null && params['abroadInternshipEndDate_day'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    ${it}
                  </option>
                </g:each>
              </select>
              <select class="month ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipEndDate') ? 'validation-failed' : ''}"
                id="abroadInternshipEndDate_month"
                name="abroadInternshipEndDate_month">
                <option value=""><g:message code="message.select.defaultOption" /></option>
                <g:each in="${1..12}">
                  <option value="${it}"
                    <g:if test="${rqt.abroadInternshipEndDate?.month == (it - 1)
                      || (rqt.abroadInternshipEndDate == null && params['abroadInternshipEndDate_month'] == it.toString())}">
                      selected="selected"
                    </g:if>>
                    <g:message code="month.${it}" />
                  </option>
                </g:each>
              </select>
              <input type="text" maxlength="4" size="3"
                class="year ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipEndDate') ? 'validation-failed' : ''}"
                id="abroadInternshipEndDate_year"
                name="abroadInternshipEndDate_year"
                value="${rqt.abroadInternshipEndDate ? rqt.abroadInternshipEndDate.year + 1900 : params['abroadInternshipEndDate_year']}"
                title="<g:message code="sgr.property.abroadInternshipEndDate.validationError" />" />
            </div>
            

    
      
      <label for="abroadInternshipSchoolName" class="required condition-makesAbroadInternship-filled"><g:message code="sgr.property.abroadInternshipSchoolName.label" /> *  <span><g:message code="sgr.property.abroadInternshipSchoolName.help" /></span></label>
            <input  type="text" id="abroadInternshipSchoolName"
                   name="abroadInternshipSchoolName"
                   value="${rqt.abroadInternshipSchoolName?.toString()}"
                   class="required condition-makesAbroadInternship-filled  validate-string ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipSchoolName') ? 'validation-failed' : ''}"
                   title="<g:message code="sgr.property.abroadInternshipSchoolName.validationError" />"   />
            

    
      
      <label for="abroadInternshipSchoolCountry" class="required condition-makesAbroadInternship-filled"><g:message code="sgr.property.abroadInternshipSchoolCountry.label" /> *  <span><g:message code="sgr.property.abroadInternshipSchoolCountry.help" /></span></label>
            <select id="abroadInternshipSchoolCountry" name="abroadInternshipSchoolCountry" class="required condition-makesAbroadInternship-filled  validate-not-first ${rqt.stepStates['currentStudies'].invalidFields.contains('abroadInternshipSchoolCountry') ? 'validation-failed' : ''}" title="<g:message code="sgr.property.abroadInternshipSchoolCountry.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['UNKNOWN','AF','ZA','AL','DZ','DE','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BJ','BS','BH','BD','BB','PW','BE','BZ','BM','BT','BY','MM','BO','BA','BW','BR','BN','BG','BF','BI','CI','KH','CM','CA','CV','CL','CN','CY','CO','KM','CG','KP','KR','CR','HR','CU','DK','DJ','DM','EG','AE','EC','ER','ES','EE','US','ET','FI','FR','GE','GA','GM','GH','GI','GR','GD','GL','GP','GU','GT','GN','GQ','GW','GY','GF','HT','HN','HK','HU','CK','FJ','MH','SB','IN','ID','IR','IQ','IE','IS','IL','IT','JM','JP','JO','KZ','KE','KG','KI','KW','LA','LS','LV','LB','LR','LY','LI','LT','LU','MG','MY','MW','MV','ML','MT','MA','MU','MR','MX','FM','MD','MC','MN','MZ','NP','NA','NR','NI','NE','NG','NU','NO','NZ','OM','UG','UZ','PE','PK','PA','PG','PY','NL','PH','PL','PT','QA','CF','CD','DO','CZ','RO','GB','RU','RW','SN','KN','SM','VA','VC','LC','SV','WS','ST','SC','SL','SG','SI','SK','SO','SD','LK','SE','CH','SR','SZ','SY','TW','TJ','TZ','TD','TH','TL','TG','TO','VT','TN','TM','TR','TV','UA','UY','VU','VE','VN','YE','ZM','ZW','MK']}">
                <option value="${it}" ${it == rqt.abroadInternshipSchoolCountry?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="sgr.property.abroadInternshipSchoolCountry" /></option>
              </g:each>
            </select>
            

    
    </fieldset>
  

