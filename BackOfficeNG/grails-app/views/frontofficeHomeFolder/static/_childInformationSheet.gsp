<a
	href="${createLink(action:'child', params:['id':child.id, 'fragment':'informationSheet'])}#informationSheet"
	class="modify"> ${message(code:'action.modify')} 
</a>
<dl>
	<g:if test="informationSheetFo != null}">
		<div class="information-box">${informationSheetFo}</div>
	</g:if>
</dl>
