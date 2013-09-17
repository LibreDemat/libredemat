
          <form method="post" action="${createLink(controller:'backofficeDocumentType', action:'editDocumentType')}" class="listItem" id="documentTypeForm_${documentType?.getId()}">

            <input type="hidden" value="${documentType?.getId()}" name="id" id="id">
            <input type="hidden" value="${codename?.toString()}" name="codename" id="codename">

              <p>
                <label for="name" class="required"><g:message code="documentType.property.name" /> * :</label>
                <input type="text" value="${name}" class="required" name="name" id="name">
              </p>
              <p>
                <label for="validityDurationType_${documentType?.getId()}" class="required"><g:message code="documentType.property.validityDurationType"/> * :</label>
                <select value="" class="required" name="validityDurationType" id="validityDurationType_${documentType?.getId()}">
                  <g:each in="${documentTypeValidityValues}" var="_documentValidityDurationType">
                    <option value="${_documentValidityDurationType.id}"

                    <%
                    if(documentType) {
                      if(documentType.getValidityDurationType().name.toUpperCase().replaceAll(" ", "_") == _documentValidityDurationType.id) {%>
                      selected='selected'
                    <% } } %>
                    >
                    ${_documentValidityDurationType.name}</option>
                  </g:each>
                </select>

                <label for="validityDuration_${documentType?.getId()}" class="required" style="${validityDurationFormStyle}" id="validityDuration_label_${documentType?.getId()}"><g:message code="documentType.property.validityDuration"/> * :</label>
                <input type="text" name="validityDuration" class="required validate-regex" regex="^[1-9][0-9]{0,9}$" maxlength="10" id="validityDuration_${documentType?.getId()}" value="${documentType?.getValidityDuration()}" style="${validityDurationFormStyle}"/>
              </p>
              <p>
                <label for="usageType" class="required"><g:message code="documentType.property.usageType"/> * :</label>
                <input type="checkbox" id="usageType" name="usageType" <% if(documentType && documentType.getUsageType().toString() == "Reusable"){ %> checked <% } %> >
              </p>
              <p class="error" id="error-container_${documentType?.getId()}"></p>
              <p class="button">
                <input type="button" value="Sauver" name="save" id="saveDocumentType_${documentType?.getId()}">
                <input type="button" value="Fermer" name="cancel" id="${documentType?'cancelEditDocumentType_'+documentType.getId() :'cancelNewDocumentType'}">
              </p>
            </form>
