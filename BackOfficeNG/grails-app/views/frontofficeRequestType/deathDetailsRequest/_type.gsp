


  
    
    <label class="required"><g:message code="ddr.property.format.label" /> *  <span><g:message code="ddr.property.format.help" /></span></label>
            <ul class="required ${rqt.stepStates['type'].invalidFields.contains('format') ? 'validation-failed' : ''}">
              <g:each in="${['FULL_COPY','MULTILINGUAL_EXTRACT']}">
              <li>
                <input type="radio" id="format_${it}" class="required  validate-one-required" value="${it}" name="format" ${it == rqt.format.toString() ? 'checked="checked"': ''} title="<g:message code="ddr.property.format.validationError" />" />
                <label for="format_${it}"><g:libredematEnumToText var="${it}" i18nKeyPrefix="ddr.property.format" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label for="copies" class="required"><g:message code="ddr.property.copies.label" /> *  <span><g:message code="ddr.property.copies.help" /></span></label>
            <input  type="text" id="copies" name="copies" value="${rqt.copies?.toString()}" 
                    class="required  validate-positiveInteger ${rqt.stepStates['type'].invalidFields.contains('copies') ? 'validation-failed' : ''}" title="<g:message code="ddr.property.copies.validationError" />"   />
            

  

  
    
    <label for="motive" class=""><g:message code="ddr.property.motive.label" />   <span><g:message code="ddr.property.motive.help" /></span></label>
            <select id="motive" name="motive" class="  validate-select ${rqt.stepStates['type'].invalidFields.contains('motive') ? 'validation-failed' : ''}" title="<g:message code="ddr.property.motive.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['NOTARY_ACT','MARRIAGE','PASSPORT','PENSION','OTHER']}">
                <option value="${it}" ${it == rqt.motive?.toString() ? 'selected="selected"': ''}><g:libredematEnumToText var="${it}" i18nKeyPrefix="ddr.property.motive" /></option>
              </g:each>
            </select>
            

  

  
    
    <label for="comment" class=""><g:message code="ddr.property.comment.label" />   <span><g:message code="ddr.property.comment.help" /></span></label>
            <textarea id="comment" name="comment" class="  validate-regex ${rqt.stepStates['type'].invalidFields.contains('comment') ? 'validation-failed' : ''}" title="<g:message code="ddr.property.comment.validationError" />" rows="3" cols="" regex="^[\w\W]{0,255}$" maxlength="255">${rqt.comment}</textarea>
            

  

