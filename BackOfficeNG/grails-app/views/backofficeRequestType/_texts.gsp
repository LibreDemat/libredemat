<div id="tabs" class="yui-navset yellow-yui-tabview">
  <div class="yui-content">
    <div id="texts">
      <form class="step-form">
        <p class="field">
          <label for="step">${message(code:'requestType.configuration.texts.which')}&nbsp;:</label>
          <select id="step">
          <g:each in="${steps}">
            <option value="${it.key}">${it.value}</option>
          </g:each>
          </select>
        </p>
      </form>
      <form class="editor-form">
        <textarea id="editor"></textarea>
        <div class="cf">
          <input type="button" id="save" value="${message(code:'action.save')}"/>
        </div>
      </form>
    </div>
  </div>
</div>
