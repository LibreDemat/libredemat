<html>
  <head>
    <title>${message(code:'planning.title')}</title>
    <meta name="layout" content="fo_main" />
    <link href="${resource(dir:'css/frontoffice', file:'planning.css')}" rel='stylesheet' media='screen'>
    <script src="${resource(dir:'js/frontoffice', file:'planning.js')}"></script>
    <script>
      zenexity.capdemat.fong.Planning.url = '${url}'
    </script>
  </head>
  <body>
    <g:if test="${message(code:'planning.desc') != 'planning.desc' && message(code:'planning.desc') != ''}">
       <div class="information-box information-box-booker">
           ${message(code:'planning.desc')}
       </div>
    </g:if>

    <iframe id="booker" src="${url}" name="booker" scrolling='no' frameborder=0 seamless>${message(code:'planning.no_iframe_support')}</iframe>
  </body>
</html>
