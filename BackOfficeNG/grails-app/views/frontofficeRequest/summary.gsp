<html>
  <head>
    <title>${message(code:'request.title.summary')}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'request.css')}" />
  </head>
  <body>

    <g:if test="${flash.successMessage}">
      <div class="success-box">
        <p>${flash.successMessage}</p>
      </div>
    </g:if>
      <div class="summary-contact created" id="contact">
        <h2>${message(code:'request.header.contact')}</h2>
        <div class="body">
          <div class="main action">
            <a href="${createLink(controller:'frontofficeRequestContact', action : 'reply', id : rqt.id)}" >${message(code:'request.contact.authority')}</a>
          </div>
          <g:if test="${contacts}">
            <ul class="contact">
              <g:each var="contact" in="${contacts}">
                <li>
                  <dl>
                    <dt>
                      <strong class="${contact?.user?.nature}" >${contact?.user?.name}</strong>
                    </dt>
                    <dd>
                      <span class="date"><g:formatDate formatName="format.fullDate" date="${contact.date}"/></span>
                      <div class="reply expand">
                        <span >${message(code:contact.channel?.i18nKey)}</span>
                        <q id="toggleReply_${contact.id}">${contact.note}</q>
                        <g:if test="${contact.attachment}">
                          </br>
                          <span> <g:message code="request.contact.pj.action.download" /> : 
                            <a title="<g:message code='requestAction.property.requestCertificate' />" href="${createLink(controller: 'frontofficeRequestContact', action: 'viewAttachment', id : rqt.id, params: ['requestNoteId': contact.id,'requestId': rqt.id])}">
                              <img alt="<g:message code='requestAction.action.download.Creation' />" src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                            </a>
                          </span>
                        </g:if>
                        <g:if test="${contact.hasFile}">
                          </br>
                          <span> <g:message code='${"requestAction.action.download." + contact.type.enumString}' /> :
                            <a title="<g:message code='requestAction.property.requestCertificate' />" href="${createLink(controller: 'frontofficeRequestContact', action: 'view', id : rqt.id,params: ['requestActionId': contact.id,'requestId': rqt.id])}">
                              <img alt="<g:message code='requestAction.action.download.Creation' />" src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                            </a>
                          </span>
                        </g:if>
                        <p>
                            <a href="${createLink(controller:'frontofficeRequestContact', action : 'reply', id : rqt.id, params: ['parentId': contact.id])}" >${message(code:'request.contact.authority.reply')}</a>
                        </p>
                        <g:if test="${contact.replies?.size() > 0}">
                          <ul class="replies">
                            <g:each var="reply" in="${contact.replies}">
                              <li class="${reply.type.enumString}">
                                <dl>
                                  <dt class="${reply.css}">
                                    <strong class="${reply?.user?.nature}">
                                      ${reply?.user?.name}
                                    </strong>
                                  </dt>
                                <dd>
                                <span class="date"><g:formatDate formatName="format.fullDate" date="${reply.date}" /></span>
                                <div class="reply expand">
                                  <span>${message(code:reply.channel?.i18nKey)}</span> 
                                  <q id="toggleReply_${reply.id}">
                                    ${reply.message?reply.message:reply.note}
                                  </q>
                                  <g:if test="${reply.attachment}">
                                    </br>
                                    <span> <g:message code="request.contact.pj.action.download" /> : 
                                      <a title="<g:message code='requestAction.property.requestCertificate' />" href="${createLink(controller: 'frontofficeRequestContact', action: 'viewAttachment', id : rqt.id,params: ['requestNoteId': reply.id,'requestId': rqt.id])}">
                                        <img alt="<g:message code='requestAction.action.download.Creation' />" src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                                      </a>
                                    </span>
                                  </g:if>
                                  <g:if test="${reply.file}">
                                    </br>
                                    <span> <g:message code='${"requestAction.action.download." + reply.type.enumString}' /> :
                                      <a title="<g:message code='requestAction.property.requestCertificate' />" href="${createLink(controller: 'frontofficeRequestContact', action: 'view', id : rqt.id,params: ['requestActionId': reply.id,'requestId': rqt.id])}">
                                        <img alt="<g:message code='requestAction.action.download.Creation' />" src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                                      </a>
                                    </span>
                                  </g:if>
                                  <br/>
                                </div>
                              </dd>
                            </dl>
                          </li>
                        </g:each>
                      </ul>
                    </g:if>
                  </div>
                </dd>
              </dl>
            </li>
          </g:each>
        </ul>
      </g:if>
    </div>
  </div>

    <g:if test="${externalInformations}">
      <div class="summary-box created" id="externalInformations">
        <h2><g:message code="request.header.externalInformations" /></h2>
        <div class="body">
          <dl>
            <g:each var="externalInformation" in="${externalInformations}">
              <dt><g:message code="${externalInformation.key}" /></dt>
              <dd>${externalInformation.value}</dd>
            </g:each>
          </dl>
        </div>
      </div>
    </g:if>
    <div class="summary-box created">
      <h2>
        <g:message code="request.header.summary"
          args="${[requestTypeLabel,rqt.id.toString()]}" />

        <g:if test="${rqt.requestSeason}">
          <span class="season-subtitle">${rqt.requestSeason.label} (<g:formatDate formatName="format.date" date="${rqt.requestSeason.effectStart.toDate()}"/> - <g:formatDate formatName="format.date" date="${rqt.requestSeason.effectEnd.toDate()}"/>)</span>
        </g:if>

        <span>
          <g:message code="requestAction.action.download.Creation" /> :
          <a title="<g:message code='requestAction.property.requestCertificate' />"
            href="${createLink(action : 'download', id : rqt.id)}">
            <img
              alt="<g:message code='requestAction.action.download.Creation' />"
              src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
          </a>
        </span>
      </h2>
      <div class="body">
        <g:render template="/frontofficeRequestType/${validationTemplateDirectory}/summary"
          model="['rqt':rqt]" />
      </div>
    </div>
  </body>
</html>
