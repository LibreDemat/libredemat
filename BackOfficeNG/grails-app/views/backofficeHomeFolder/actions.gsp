<style>
  dl.action dd a:hover { background : blue; }
</style>
<ul id="userActions">
  <g:each var="action" in="${actions}">
    <li>
      <dl class="action">
        <dt class="title">
          <span class="tag ${action.type.cssClass}">
            ${message(code:action.type.i18nKey)}
          </span>
        </dt>
        <dd class="title">
          <span class="date"><g:formatDate formatName="format.fullDate" date="${action.date}"/></span>
          <g:if test="${action.state}">
            <span class="tag ${action.state.cssClass}">${message(code:action.state.i18nKey)}</span>
          </g:if>
          <strong>${action.target.name}</strong>
        </dd>
        <dd class="user">
          <g:if test="${action.user.name}">
            ${message(code:'layout.by')}
            <strong class="${action.user.nature}">${action.user.name}</strong>
          </g:if>
        </dd>
        <g:if test="${action.responsible}">
          <g:if test="${action.responsible.deleted}">
            <dt style="text-decoration : line-through">
              <g:each var="type" in="${action.responsible.deleted}">
                <g:libredematEnumToFlag var="${type}" i18nKeyPrefix="homeFolder.role" />
              </g:each>
            </dt>
            <dd style="text-decoration : line-through">
              ${action.responsible.name}
            </dd>
          </g:if>
          <g:else>
            <dt>
              <g:each var="type" in="${action.responsible.types}">
                <g:libredematEnumToFlag var="${type}" i18nKeyPrefix="homeFolder.role" />
              </g:each>
            </dt>
            <dd>
              ${action.responsible.name}
            </dd>
          </g:else>
        </g:if>
        <g:if test="${action.atom}">
          <dt>${message(code:'user.atom.' + action.atom.name)}</dt>
          <dd>
            <dl class="atom">
            <g:each var="field" in="${action.atom.fields}">
              <dt>${message(code:'user.atom.field.' + field.key)}</dt>
              <dd>
                <span class="previous">${!(message(code:'user.atom.field.'+field.key+'.'+field.value.from.toLowerCase()).equals('user.atom.field.'+field.key+'.'+field.value.from.toLowerCase()))?message(code:'user.atom.field.'+field.key+'.'+field.value.from.toLowerCase()):field.value.from}</span> ↪ ${!(message(code:'user.atom.field.'+field.key+'.'+field.value.to.toLowerCase()).equals('user.atom.field.'+field.key+'.'+field.value.to.toLowerCase()))?message(code:'user.atom.field.'+field.key+'.'+field.value.to.toLowerCase()):field.value.to}
              </dd>
            </g:each>
            </dl>
          </dd>
        </g:if>
        <g:if test="${action.message}">
                    <dt>
                        ${message(code:'user.atom.message')}
                    </dt>
                    <dd>
                        <span>
                            ${action.message.value}
                        </span>
                    </dd>
                </g:if>
        <g:if test="${action.contact}">
          <dt>${message(code : "contact.property.meansOfContact")}</dt>
          <dd><g:libredematEnumToText var="${action.contact.meansOfContact}" i18nKeyPrefix="meansOfContact" /></dd>
          <g:if test="${action.contact.recipient}">
            <dt>${message(code : "contact.property.recipient")}</dt>
            <dd>${action.contact.recipient}</dd>
          </g:if>
          <g:if test="${action.contact.message}">
            <dt>${message(code : "contact.property.message")}</dt>
            <dd>${action.contact.message}</dd>
          </g:if>
        </g:if>
        <g:if test="${action.note}">
          <dt>${message(code : "contact.property.note")}</dt>
          <dd>${action.note}</dd>
        </g:if>
        <g:if test="${action.merge}">
          <dt>${message(code : "property.target")}</dt>
          <dd>
              ${action.merge}
              <g:if test="${action.target.name.contains('Compte')}">
                  (<a href="${createLink(action:'details',id:action.merge)}">${message(code : "homeFolder.header.goToAccount")}</a>)
              </g:if>
          </dd>
        </g:if>
        <g:if test="${action.hasFile}">
          <dt>
            <g:message code="requestAction.property.requestCertificate" />
          </dt>
          <dd>
            <a title="<g:message code='${"requestAction.action.download." + action.type.enumString}' />"
               href="${createLink(controller : 'backofficeHomeFolder', action : 'view', params : ['id' : action.target.id, 'requestActionId' : action.actionId])}">
                  <img alt="<g:message code='${"requestAction.action.download." + action.type.enumString}' />" src="${resource(dir:'images/icons',file:'pdficon_small.gif')}"/>
            </a>
          </dd>
        </g:if>

      </dl>
    </li>
  </g:each>
</ul>
