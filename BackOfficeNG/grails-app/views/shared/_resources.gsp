<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" />
<!-- Grid and common settings CSS -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/backoffice/yui/reset-fonts-grids',file:'reset-fonts-grids.css')}" />
<!-- Dialog Container CSS -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/backoffice/yui/container',file:'container.css')}" />
<!-- Tabview CSS -->
<link type="text/css" rel="stylesheet" href="${resource(dir:'css/backoffice/yui/tabview',file:'tabview.css')}" />
<!-- Calendar CSS -->
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.css')}" />

<!-- BONG CSS -->
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common',file:'layout.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common',file:'box.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/common',file:'tag.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/common',file:'cf.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common',file:'form.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common',file:'list.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common',file:'panel.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/common/yui-skin',file:'tabview.css')}" />

<!--[if IE 6]>
<link rel="stylesheet" href="${resource(dir:'css/backoffice/hacks',file:'ie6.css')}" />
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" href="${resource(dir:'css/backoffice/hacks',file:'ie6.css')}" />
<link rel="stylesheet" href="${resource(dir:'css/backoffice/hacks',file:'ie7.css')}" />
<![endif]-->

<!-- DOM event -->
<script src="${resource(dir:'js/yui/yahoo-dom-event',file:'yahoo-dom-event.js')}"></script>
<!-- Element -->
<script src="${resource(dir:'js/yui/element',file:'element-min.js')}"></script>
<!-- Utilities -->
<script type="text/javascript" src="${resource(dir:'js/yui/utilities',file:'utilities.js')}"></script>
<!-- Dialog Container -->
<script type="text/javascript" src="${resource(dir:'js/yui/container',file:'container-min.js')}"></script>
<!-- Data source -->
<script type="text/javascript" src="${resource(dir:'js/yui/datasource',file:'datasource-min.js')}"></script>
<!-- Datatable -->
<script type="text/javascript" src="${resource(dir:'js/yui/paginator',file:'paginator-min.js')}"></script>
<!-- safe JSON parsing -->
<script type="text/javascript" src="${resource(dir:'js/yui/json',file:'json-min.js')}"></script>
<!-- Tabview -->
<script type="text/javascript" src="${resource(dir:'js/yui/tabview',file:'tabview-min.js')}"></script>
<!-- Calendar -->
<script type="text/javascript" src="${resource(dir:'js/yui/calendar',file:'calendar-min.js')}"></script>
<!-- Selector -->
<script type="text/javascript" src="${resource(dir:'js/yui/selector',file:'selector-min.js')}"></script>

<g:if test="${Locale.FRENCH.getLanguage().equals(request.locale.getLanguage())}">
  <script type="text/javascript" src="${resource(dir:'js/common', file:'date-fr-FR.js')}"></script>
</g:if>
<g:else>
  <script type="text/javascript" src="${resource(dir:'js/common', file:'date-en-US.js')}"></script>
</g:else>

<script type="text/javascript" src="${resource(dir:'js/common',file:'tools.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common', file:'aspect.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common',file:'common.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common',file:'validation.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/common',file:'validationRules.js')}"></script>

<script type="text/javascript">
  zenexity.capdemat.tools.namespace('zenexity.capdemat.bong');
  zenexity.capdemat.bong.currentMenu = "${session['currentMenu']}";
  zenexity.capdemat.baseUrl = '<g:baseUrl controller="${webRequest.controllerName}" />';
  zenexity.capdemat.contextPath = '${request.contextPath}';
</script>
