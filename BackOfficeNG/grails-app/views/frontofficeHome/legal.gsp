<html>
  <head>
    <title>${message(code:'home.title.legal')}</title>
    <meta name="layout" content="fo_main"/>
  </head>
  <body>
    <div class="main-box">
      <h2>${message(code:'home.title.legal')}</h2>
      <g:if test="${legal}">
        ${legal}
      </g:if>
      <g:else>
        <div class="information-box">
          ${message(code:'message.legal.undefined')}
        </div>
      </g:else>
    </div>
  </body>
</html>
