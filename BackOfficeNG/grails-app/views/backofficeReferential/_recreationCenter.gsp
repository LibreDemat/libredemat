<form id="form_${recreationCenter?.id}" class="editable-list-form"
  action="${createLink(action:'recreationCenter')}" method="post">
  <div id="error-container_${recreationCenter?.getId()}" class="error"></div>
  <label class="required" for="name"><g:message code="recreationCenter.name"/> * :</label>
  <input type="text" name="name" class="required" value="${recreationCenter?.name}" />
  <label class="required" for="address"><g:message code="recreationCenter.address"/> * :</label>
  <input type="text" name="address" class="required" value="${recreationCenter?.address}" />
  <label class="required" for="active"><g:message code="property.active"/> * :</label>
  <input type="checkbox" name="active" <g:if test="${recreationCenter?.active}">checked="checked" </g:if>/>
  <input type="hidden" name="_active" />
  <input type="hidden" name="id" class="required validate-number" value="${recreationCenter?.id}" />
  <p class="same-line">
    <input id="save_${recreationCenter?.getId()}" name="save" type="button"
      class="submitEditItem form-button first-button" value="${message(code:'action.save')}" />
    <input id="cancel_${recreationCenter?.getId()}" name="cancel" type="button"
      class="cancelEditItem form-button" value="${message(code:'action.close')}" />
  </p>
</form>
