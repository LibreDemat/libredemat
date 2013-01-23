<div id="tabs" class="yui-navset yellow-yui-tabview">
  <div class="yui-content">
    <div id="emails">
      <form class="state-form">
        <p class="field">
          <label for="state">${message(code:'requestType.configuration.emails.which.state')}&nbsp;:</label>
          <select id="state">
          <g:each in="${states}">
            <option id="${it.code}" value="${it.code}"<g:if test="${it.enabled}"> class="mail-enabled"</g:if>>${it.label}<g:if test="${it.enabled}"> (${message(code:'requestType.configuration.emails.enabled')})</g:if></option>
          </g:each>
          </select>
        </p>
      </form>
      <form class="editor-form">
        <textarea id="editor"></textarea>
        <div class="cf">
          <input type="button" id="save" value="${message(code:'action.save')}"/>
          <input type="button" id="disable"<g:if test="${!states[0].enabled}"> disabled</g:if> value="${message(code:'requestType.configuration.emails.action.disable')}"/>
        </div>
      </form>
    </div>
  </div>
</div>
