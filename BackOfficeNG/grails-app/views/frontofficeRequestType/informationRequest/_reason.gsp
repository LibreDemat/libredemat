


  
    
    <label class="required"><g:message code="ir.property.motive.label" /> *  <span><g:message code="ir.property.motive.help" /></span></label>
            <g:set var="motiveIndex" value="${0}" scope="flash" />
            <g:render template="/frontofficeRequestType/widget/localReferentialData" 
                      model="['javaName':'motive', 'i18nPrefixCode':'ir.property.motive', 'htmlClass':'required  ', 
                              'lrEntries': lrTypes.motive.entries, 'depth':0]" />
            

  

  
    
    <label for="message" class="required"><g:message code="ir.property.message.label" /> *  <span><g:message code="ir.property.message.help" /></span></label>
            <textarea id="message" name="message" class="required  validate-regex ${rqt.stepStates['reason'].invalidFields.contains('message') ? 'validation-failed' : ''}" title="<g:message code="ir.property.message.validationError" />" rows="8" cols="" regex="^.{0,255}$" maxlength="255">${rqt.message}</textarea>
            

  

