<g:if test="${canResetPassword}">
  <div class="nobox taskstate">
    <h3>${message(code:'property.security')}</h3>
    <div class="body form" id="reset-pwd-body">
      <input type="button" id="reset-pwd" class="" value="${message(code:'homeFolder.action.resetPwd')}" />
    </div>
  </div>
</g:if>