<link rel="shortcut icon" type="image/png" href="${resource(dir:'images', file:'favicon.png')}?v=1">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'reset.css')}">
<!-- YUI -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/yui', file:'fonts-and-grids.css')}">
<!-- TODO: move to common. -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/backoffice/yui/container', file:'container.css')}">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/common', file:'tag.css')}">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'layout.css')}">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'box.css')}">
<link rel="stylesheet" type="text/css" href="${createLink(controller:'localAuthorityResource', action: 'resource', id:'cssFo')}">
<!-- Calendar CSS -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.css')}" />

<!-- YUI -->
<script src="${resource(dir:'js/yui/yahoo-dom-event', file: 'yahoo-dom-event.js')}"></script>
<script src="${resource(dir:'js/yui/connection', file: 'connection-min.js')}"></script>
<script src="${resource(dir:'js/yui/element', file: 'element-min.js')}"></script>
<script src="${resource(dir:'js/yui/container', file: 'container-min.js')}"></script>
<script src="${resource(dir:'js/yui/utilities', file:'utilities.js')}"></script>
<script src="${resource(dir:'js/yui/selector', file:'selector-min.js')}"></script>
<script src="${resource(dir:'js/yui/json', file:'json-min.js')}"></script>
<script src="${resource(dir:'js/yui/tabview', file:'tabview-min.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/yui/calendar',file:'calendar-min.js')}"></script>

<script type="text/javascript" src="${resource(dir:'js/common', file:'tools.js')}"></script>
<script type="text/javascript">
  zenexity.libredemat.tools.namespace('zenexity.libredemat.fong')
  zenexity.libredemat.baseUrl = '<g:createLink controller="${webRequest.controllerName}"/>'
  zenexity.libredemat.messages = { browser_alert_title: '${message(code:"browser.alert.title")}'
                               , browser_alert_content: '${message(code:"browser.alert.content")}'
                               }
</script>

<!-- Browser detection -->
<script src="${resource(dir:'js/common', file:'ua-parser-0.4.8.js')}"></script>
<script src="${resource(dir:'js/frontoffice', file:'browser-alert.js')}"></script>

<script type="text/javascript" src="${resource(dir:'js/common', file:'validation.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common', file:'validationRules.js')}"></script>
<script src="${resource(dir:'js/common', file:'date.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common', file:'date-fr-FR.js')}"></script>
