<html>
    <head>
        <title><g:message code="payment.header.security" /></title>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${resource(dir:'css/backoffice',file:'configuration.css')}" />
        <script type="text/javascript" src="${resource(dir:'js/backoffice',file:'security.js')}"></script>
    </head>
    <body>
        <div id="yui-main">
            <div class="yui-b">
                <div class="head">
                    <h1><g:message code="payment.header.security" /></h1>
                </div>

                <div id="displayGroupAgentsBox" class="mainbox mainbox-yellow">
                        <h2>${message(code:'payment.header.securityConfiguration')}</h2>
                        <div class="editableListSwithcher">
                            <form id="sortAgentForm" method="post" action="${createLink(action:'filterSecurityAgent')}" />
                                <input type="hidden" name="scope" id="scope" value="${scope}" />
                                <a id="viewAgents_bounded" class="current">${message(code:'filter.viewBounded')}</a> / <a id="viewAgents_all">${message(code:'filter.viewAll')}</a>
                            </form>
                        </div>
                        <div id="displayGroupAgentsWrapper">
                            <g:render template="agents"
                                    model="['agents':agents]" />
                        </div>
                    </div>

            </div>
        </div>
        <div id="narrow" class="yui-b">
            <menu:subMenu i18nPrefix="header" data="${subMenuEntries}" />
        </div>
    </body>
</html>
