<form id="adultIdentity_${individual.id}"  method="post" action="${g.createLink(action:'identity', id : individual.id)}">
  <dt class="required">${message(code:'homeFolder.adult.property.title')}</dt>
  <dd class="required">
    <select name="title">
      <g:each var="title" in="${fr.cg95.cvq.business.users.TitleType.allTitleTypes}">
        <option value="fr.cg95.cvq.business.users.TitleType_${title}" ${title == individual.title ? 'selected="selected"' : ''}>
          ${g.capdematEnumToText(var:title, i18nKeyPrefix:'homeFolder.adult.title')}
        </option>
      </g:each>
    </select>
  </dd>
  <dt>${message(code:'homeFolder.adult.property.familyStatus')}</dt>
  <dd>
    <select name="familyStatus">
      <option value="">${message(code:'homeFolder.adult.familyStatus.null')}</option>
      <g:each var="familyStatus" in="${fr.cg95.cvq.business.users.FamilyStatusType.allFamilyStatusTypes}">
        <option value="fr.cg95.cvq.business.users.FamilyStatusType_${familyStatus}" ${familyStatus == individual.familyStatus ? 'selected="selected"' : ''}>
          ${g.capdematEnumToText(var:familyStatus, i18nKeyPrefix:'homeFolder.adult.familyStatus')}
        </option>
      </g:each>
    </select>
  </dd>
  <dt class="required">${message(code:'homeFolder.individual.property.lastName')}</dt>
  <dd class="required">
    <input type="text" name="lastName" value="${individual.lastName}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.maidenName')}</dt>
  <dd>
    <input type="text" name="maidenName" value="${individual.maidenName}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.nameOfUse')}</dt>
  <dd>
    <input type="text" name="nameOfUse" value="${individual.nameOfUse}" />
  </dd>
  <dt class="required">${message(code:'homeFolder.individual.property.firstName')}</dt>
  <dd class="required">
    <input type="text" name="firstName" value="${individual.firstName}" />
  </dd>
  <dt>${message(code:'homeFolder.individual.property.firstName2')}</dt>
  <dd>
    <input type="text" name="firstName2" value="${individual.firstName2}" />
  </dd>
  <dt>${message(code:'homeFolder.individual.property.firstName3')}</dt>
  <dd>
    <input type="text" name="firstName3" value="${individual.firstName3}" />
  </dd>
  <dt>${message(code:'homeFolder.adult.property.profession')}</dt>
  <dd>
    <input type="text" name="profession" value="${individual.profession}" />
  </dd>
  <g:render template="edit/submit" model="['object':individual, 'template':'adultIdentity']" />
</form>
