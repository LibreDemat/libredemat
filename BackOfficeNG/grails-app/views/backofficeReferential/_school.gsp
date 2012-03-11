<form id="form_${school?.id}" class="editable-list-form"
  action="${createLink(action:'school')}" method="post">
  <div id="error-container_${school?.getId()}" class="error"></div>
  <label class="required" for="name"><g:message code="school.name"/> * :</label>
  <input type="text" name="name" class="required" value="${school?.name}" />
  <label class="required" for="address"><g:message code="school.address"/> * :</label>
  <input type="text" name="address" class="required" value="${school?.address}" />
  <label class="required" for="active"><g:message code="property.active"/> * :</label>
  <input type="checkbox" name="active" <g:if test="${school?.active}">checked="checked" </g:if>/>
  <input type="hidden" name="_active" />
  <input type="hidden" name="id" class="required validate-number" value="${school?.id}" />
  <p class="same-line">
    <input id="save_${school?.getId()}" name="save" type="button"
      class="submitEditItem form-button first-button" value="${message(code:'action.save')}" />
    <input id="cancel_${school?.getId()}" name="cancel" type="button"
      class="cancelEditItem form-button" value="${message(code:'action.close')}" />
  </p>
</form>
