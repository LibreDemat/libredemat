<h3 class="docu"  style="padding-top: 8px; ">
	<g:message code="documents.familiales" /> :
</h3>

<table>
	<g:each var="record" in="${certificates.family}">
		<tr id="family_TR_${record[1]}">
			<td width="100%" style="padding-left: 5px">
				${record[0]}
			</td>
			<td style="padding-right: 20px">
				<p id="${record[0]}_${record[1]}" class="icone"
						onClick="showDocPanel(this,'${message(code:'documents.familiales')}')">
						<img src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
				</p>
			</td>
		</tr>
	</g:each>
</table>

<h3 class="docu" style="padding-top: 8px; ">
	<g:message code="documents.individuels" /> :
</h3>

<table>
	<g:each var="docIndiv" in="${certificates.individual}">
		<tr><th style="font-weight: bold; text-decoration: none; padding-top: 5px; padding-left: 5px">
			Documents de ${docIndiv[0]}
		</th></tr>
		<!-- L'id de l'individu correspond à l'id des documents à afficher ALORS feu  -->
		<g:each var="doc" in="${docIndiv[1]}">
			<tr id="individuals_TR_${doc[1]}">

				<td width="100%" style="padding-left: 10px">
					${doc[0]}
				</td>
				<td style="padding-right: 20px">
					<p id="${doc[0]}_${doc[1]}" class="icone"
						onClick="showDocPanel(this,'${message(code:'documents.individuels')}')">
						<img src="${resource(dir:'images/icons',file:'pdficon_small.gif')}" />
					</p>
				</td>
			</tr>
		</g:each>
		<tr><th style="padding-top: 10px;"></th></tr>
	</g:each>

</table>
