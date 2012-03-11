<form id="form_${agent?.id}" class="editable-list-form"
  action="${createLink(action:'agent')}" method="post">
  <div id="error-container_${agent?.getId()}" class="error"></div>
  <g:if test="${profile}">
    <label class="required" for="login"><g:message code="agent.property.login"/> * :</label>
    <input type="text" name="login" class="required" value="${agent?.login}" />
  </g:if>
  <g:if test="${password}">
    <label class="required" for="password"><g:message code="agent.property.password"/> * :</label>
    <input type="password" name="password" value="" class="required validate-regex" regex="^.{${passwordMinLength},}$" />
    <span class="legend"><g:message code="property.individualPassword.legend" args="${[passwordMinLength]}"/></span>
    <label class="required" for="passwordConfirmation"><g:message code="property.individualPasswordConfirmation"/> * :</label>
    <input type="password" name="passwordConfirmation" value="" class="required validate-regex" regex="^.{${passwordMinLength},}$" />
    <span class="legend"><g:message code="property.individualPassword.legend" args="${[passwordMinLength]}"/></span>
  </g:if>
  <g:if test="${profile}">
    <label class="required" for="firstName"><g:message code="property.individualFirstName"/> * :</label>
    <input type="text" name="firstName" class="required" value="${agent?.firstName}" />
    <label class="required" for="lastName"><g:message code="property.individualLastName"/> * :</label>
    <input type="text" name="lastName" class="required" value="${agent?.lastName}" />
    <label class="required" for="email"><g:message code="property.individualEmail"/> * :</label>
    <input type="text" name="email" class="required validate-email" value="${agent?.email}" />
    <label class="required" for="active"><g:message code="property.active"/> * :</label>
    <input type="checkbox" name="active" <g:if test="${agent?.active}">checked="checked" </g:if>/>
    <input type="hidden" name="_active" />
    <label class="required" for="siteProfile_${agent?.id}"><g:message code="agent.property.sitesRoles"/> * :</label>
    <select name="siteProfile" id="siteProfile_${agent?.id}" class="required validate-notfirst">
      <option value="">${message(code:'message.select.defaultOption')}</option>
      <option value="Agent" <g:if test="${isAgent}">selected="selected"</g:if>>
        ${message(code : "role.agent")}
      </option>
      <option value="Admin" <g:if test="${isAdmin}">selected="selected"</g:if>>
        ${message(code : "role.admin")}
      </option>
    </select>
    <input type="hidden" name="profile" value="1" />
  </g:if>
  <input type="hidden" name="id" class="required validate-number" value="${agent?.id}" />
  <p class="same-line">
    <input id="save_${agent?.getId()}" name="save" type="button"
      class="submitEditItem form-button first-button" value="${message(code:'action.save')}" />
    <input id="cancel_${agent?.getId()}" name="cancel" type="button"
      class="cancelEditItem form-button" value="${message(code:'action.close')}" />
  </p>
</form>
