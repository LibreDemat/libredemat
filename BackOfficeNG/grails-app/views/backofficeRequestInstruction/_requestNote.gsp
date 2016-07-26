<dl class="action">
  <dt class="title">
    <g:if test="${action.user.nature == 'eCitizen'}">
      <span class="tag tag-comment">
        Echange usager
      </span>
    </g:if>
    <g:else>
      <span class="tag tag-note">
        <g:message code="request.property.note" />
      </span>
    </g:else>
    <g:if test="${action.user.name}">
      <br/>
      <br/>
      <strong>${action.user.name}</strong>
    </g:if>
  </dt>
  <dd class="title noDashBorder">
    <span class="date"><g:formatDate formatName="format.fullDate"
          date="${action.date}" />
    </span>
  </dd>
  <dd class="title replyBot">${action.note}
  <g:if test="${action.attachmentName}">
  </br>
  <strong><g:message code="request.contact.pj" /> :</strong>
   <a href="${createLink(controller : 'backofficeContact', action : 'viewNote',
      params : ['requestId' : requestId, 'requestNoteId' : action.id])}">
    <img src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
  </a>
  </g:if>

  <g:if test="${action.replies?.size() > 0}">
      <ul>
        <g:each var="reply" in="${action.replies}">
          <li class="reply">
            <dl>
              <dt class="name note-${reply.css}">
                  <g:if test="${reply.user?.name}">
                    <strong>
                      ${reply.user.name}
                    </strong>
                  </g:if>
              </dt>
              <dd class="noDashBorder">
                <span class="date"><g:formatDate formatName="format.fullDate"
                    date="${reply.date}" />
                </span>
              </dd>
              <dd class="replyBot note-${reply.css}">
                <div class=""><strong><g:message code="requestAction.property.note" /> :</strong>${reply.note}</div>
                <div class=""><strong><g:message code="requestAction.property.message" /> :</strong>${reply.message}</div>
                <g:if test="${reply.attachmentName}">
                  <strong><g:message code="request.contact.pj" /> :</strong>
                   <a href="${createLink(controller : 'backofficeContact', action : 'viewNote',
          params : ['requestId' : requestId, 'requestNoteId' : reply.id])}">
                    <img src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                  </a>
                </g:if>
                <g:if test="${reply.fileName}">
                  <strong><g:message code="request.contact.pj" /> :</strong>
                    <a title="<g:message code='${"requestAction.action.download." + reply.type.enumString}' />"
                        href="${createLink(controller : 'backofficeContact', action : 'view',
                          params : ['requestId' : requestId, 'requestActionId' : reply.id])}">
                      <img
                          alt="<g:message code='${"requestAction.action.download." + reply.type.enumString}' />"
                          src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
                    </a>
                </g:if>
              </dd>
            </dl>
          </li>
        </g:each>
      </ul>
    </dd>
  </g:if>
  <br/>

  <g:if test="${!action.type.enumString.equals('Internal')}">
    <p>
      <a class="replyNote" data-parent="${action.id}" data-requet="${requestId}" >${message(code:'request.contact.authority.reply')}</a>
    </p>
  </g:if>
</dl>