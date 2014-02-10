<div id="adult_" class="individual">
  <h3>${message(code:'homeFolder.individual.header.newAdult')}</h3>
  <form id="addAdult" method="post" action="${g.createLink(action:'adult')}">
    <dl style="margin-top:1em">
      <dt class="required">${message(code:'homeFolder.adult.property.title')}</dt>
      <dd class="required">
        <select name="title">
          <g:each var="title" in="${org.libredemat.business.users.TitleType.allTitleTypes}">
            <option value="${title.name()}" ${title == adult.title ? 'selected="selected"' : ''}>
              ${g.libredematEnumToText(var:title, i18nKeyPrefix:'homeFolder.adult.title')}
            </option>
          </g:each>
        </select>
      </dd>
      <dt class="required">${message(code:'homeFolder.individual.property.lastName')}</dt>
      <dd class="required">
        <input type="text" name="lastName" value="${adult.lastName}" />
      </dd>
      <dt class="required">${message(code:'homeFolder.individual.property.firstName')}</dt>
      <dd class="required">
        <input type="text" name="firstName" value="${adult.firstName}" />
      </dd>
    </dl>
    <dl style="margin-top:1em">
      <dt class="required">${message(code:'homeFolder.adult.property.email')}</dt>
      <dd class="required">
        <input type="text" name="email" value="${adult.email}" />
      </dd>
      <dt>${message(code:'homeFolder.adult.property.homePhone')}</dt>
      <dd>
        <input type="text" name="homePhone" value="${adult.homePhone}" />
      </dd>
      <dt>${message(code:'homeFolder.adult.property.mobilePhone')}</dt>
      <dd>
        <input type="text" name="mobilePhone" value="${adult.mobilePhone}" />
      </dd>
      <dt>${message(code:'homeFolder.adult.property.officePhone')}</dt>
      <dd>
        <input type="text" name="officePhone" value="${adult.officePhone}" />
      </dd>
    </dl>
    <dl style="margin-top:1em">
      <g:render template="edit/submit" model="['object':adult]" />
    </dl>
  </form>
</div>
