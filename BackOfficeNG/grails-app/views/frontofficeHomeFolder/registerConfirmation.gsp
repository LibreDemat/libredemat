<html>
  <head>
    <title>${message(code:'homeFolder.title.confirmation')}</title>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir : 'css/frontoffice', file : 'homefolder.css')}" />
  </head>
  <body>
    <div class="individual box">
      <h2>${message(code: 'homeFolder.title.confirmation')}</h2>
      <div class="main">
        <p>${message(code: 'homeFolder.action.confirmation.success')}</p>
        <p>${message(code: 'homeFolder.action.confirmation.validationRequired', args:["<strong>"+params.email+"</strong>"])}</p>
      </div>
    </div>
  </body>
</html>
