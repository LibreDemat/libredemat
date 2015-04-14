<html>
  <head>
    <title>${message(code:'reservation.title')}</title>
    <meta name="layout" content="fo_main"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'data-detail.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'reservation.css')}" />
  </head>

  <body>

  <div id="yui-main">
    <div id="main" class="yui-b activities">
       <g:render template="individuals" />
    </div>
  </div>
  <!-- end of yui-main -->
  <div id="narrow" class="yui-b">
    <g:render template="filter" />
  </div>
  </body>
</html>