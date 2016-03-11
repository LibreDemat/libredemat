<html>
  <head>
    <title>${message(code:'request.title.summary')}</title>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/frontoffice/common', file:'form.css')}" />
    <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/frontoffice', file:'request.css')}" />
  </head>
  <body>
    <g:if test="${flash.errorMessage}"><div class="error-box"><p>${flash.errorMessage}</p></div></g:if>
    <div id="yui-main">
      <div id="main" class="yui-b">
        <div class="main-box">
          <h2>${message(code:'request.contact.header.writeMessage')}</h2>
            <g:if test="${parentReply != null && parentReply.size > 0}">
              <g:each var="reply" in="${parentReply}">
                <dl class="contact" style="padding: 1.5em;">
                  <dt><strong class="${reply?.user?.nature}">${reply?.user?.name}</strong></dt>
                  <dd>
                    <span class="date"><g:formatDate formatName="format.fullDate" date="${reply.date}"/></span>
                    <div class="reply expand">
                      <q>${reply.note}</q>
                      <form action="${createLink(action:'reply', id: requestId)}" enctype="multipart/form-data" method="post">
                        <label class="required" for="message">${message(code:'request.contact.label.reply')}</label>
                        <textarea id="message" name="message" class="required" rows="5" cols=""></textarea>
                        <input type="hidden" name="requestId" value="${requestId}" style="display : none;" />
                        <input type="hidden" name="parentId" value="${reply.id}" style="display : none;" />
                        <p class="field" id="attachField">
                          <label for="attachment">
                            <g:message code="request.contact.property.attachment" /> :
                          </label>
                          <input type="file" id="attachment" name="attachment"/>
                        </p>
                        <input type="submit" value="${message(code:'action.send')}" />
                      </form>
                    </div>
                  </dd>
                </dl>
              </g:each>
            </g:if>
            <g:else>
                <form action="${createLink(action:'reply', id: requestId)}" enctype="multipart/form-data" method="post">
                  <label class="required" for="message">${message(code:'request.contact.label.message')}</label>
                  <textarea id="message" name="message" class="required" rows="5" cols=""></textarea>
                  <input type="hidden" name="requestId" value="${requestId}" style="display : none;" />
                  <input type="hidden" name="parentId" value="${parentId}" style="display : none;" />
                  <label for="attachment">${message(code:'request.contact.property.attachment')} :</label>
                  <input type="file" id="attachment" name="attachment"/></br>
                  <input type="submit" value="${message(code:'action.send')}" />
                </form>
            </g:else>
        </div>
      </div>
    </div>
    <div id="narrow" class="yui-b">
      <div class="narrow-box">
        <h3>${message(code:'header.display')}</h3>
        <div class="body">
        <a href="${createLink(controller:'frontofficeRequest',action:'summary',id:requestId)}">
          ${message(code:'request.action.backToRequestSummary')}
        </a>
        </div>
      </div>
    </div>
  </body>
</html>