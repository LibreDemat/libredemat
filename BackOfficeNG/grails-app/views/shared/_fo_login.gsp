<div id="hd">
  <div class="top">
    <g:if test="${session.currentCredentialBean?.ecitizen && !(session.currentCredentialBean.ecitizen.homeFolder.temporary)}">
      <g:if test="${org.libredemat.security.SecurityContext.getCurrentConfigurationBean().getAuthenticationMethodFront() == 'oauth2'}">
        <strong>${session.currentEcitizenName}</strong> <a class="menu" href="${session.proxyAgent ? createLink(controller:'frontofficeHome', action:'logoutAgent') : createLink(controller:'OAuth2', action: 'logout')}"><strong>${message(code:'action.logout')}</strong></a>
      </g:if>
      <g:else>
        <em>${session.currentEcitizenName}</em> <a class="menu" href="${createLink(controller:'frontofficeHome', action:session.proxyAgent ? 'logoutAgent': 'logout')}">${message(code:'action.logout')}</a>
      </g:else>
    </g:if>
    <g:else>
      <g:if test="${org.libredemat.security.SecurityContext.getCurrentConfigurationBean().getAuthenticationMethodFront() == 'oauth2'}">
        <a href="${createLink(controller:'OAuth2', action:'askLogin', params:[callback:params.callback])}" class="menu"><strong>${message(code:'action.login')}</strong></a>
      </g:if>
      <g:elseif test="${isLogin}">
        <form action="${createLink(controller:'frontofficeHome', action:'login')}" method="post">
          <g:if test="${error}"><p class="error">${error}</p></g:if>
          <label for="login">${message(code:'account.property.login')}</label>
          <input type="text" class="text" name="login" id="login"/>
          <input type="hidden" class="text" name="callback" id="callback" value="${params.callback}"/>
          <label for="password">${message(code:'account.property.password')}</label>
          <input type="password" class="text" name="password" id="password"/>
          <input type="submit" class="button" value="${message(code:'action.login')}"/>
          <a href="${createLink(controller:'frontofficeHomeFolder', action:'resetPassword')}">${message(code:'account.message.forgottenPassword')}</a>
        </form>
      </g:elseif>
    </g:else>
    <a href="${createLink(controller:'localAuthorityResource', action:'resource', id:'helpFo')}" class="menu" target="blank">${message(code:'menu.help')}</a>
    <a href="${createLink(controller:'localAuthorityResource', action:'resource', id:'faqFo')}" class="menu" target="blank">${message(code:'menu.faq')}</a>
  </div>
</div>
