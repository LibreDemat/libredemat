<select name="requestNotesType" id="requestNotesType">
  <option id="filterNotes_all" value=""><g:message code="request.note.type.all" /></option>
  <g:each var="requestNoteType" in="${requestNoteTypes}">
    <option id="filterNotes_${requestNoteType.enumString}" value="${requestNoteType.enumString}"
      <g:if test="${currentType == requestNoteType.enumString}"> selected="selected"</g:if>>
      <g:message code="${requestNoteType.i18nKey}" />
    </option>
  </g:each>
</select>
<g:if test="${requestNoteList.isEmpty()}">
 <g:message code="request.message.noNote" /> !
</g:if>
<g:else>
  <dl class="notes">
    <g:each var="requestNote" in="${requestNoteList}">
      <dt>
        <span class="tag-${requestNote.nature}">${requestNote.nature}</span>
        ${requestNote.user_name}
      </dt>
      <dd>
        <p class="note">
          <g:capdematEnumToFlag var="${requestNote.type}" i18nKeyPrefix="request.note.type" />${requestNote.note}
        </p>
        <p class="noteMetadata">
          <g:message code="request.property.note" /> n° <strong>${requestNote.id}</strong>
          <g:if test="${requestNote.date != null}">
            <g:message code="request.note.date" /> <strong><g:formatDate formatName="format.fullDate" date="${requestNote.date}"/></strong>
          </g:if>
        </p>
      </dd>
    </g:each>
  </dl>
</g:else>
<form method="post" id="requestNoteForm" action="${createLink(action:'requestNote')}">
  <div id="noteMsg" style="display:none"></div>
  <label for="note"><g:message code="request.note.addLabel" /><span id="noteLimit"></span></label>
  <input type="text" id="note" name="note" size="60" maxlength="1024" />
  <select name="requestNoteType" id="requestNoteType">
    <g:each var="requestNoteType" in="${requestNoteTypes}">
      <option value="${requestNoteType.enumString}">
        <g:message code="${requestNoteType.i18nKey}" />
      </option>
    </g:each>
  </select>
  <input type="button" id="submitNote" name="submitNote"
    value="${message(code:'action.save')}"  />
  <input type="hidden" id="requestId" name="requestId" value="${requestId}" />
</form>
