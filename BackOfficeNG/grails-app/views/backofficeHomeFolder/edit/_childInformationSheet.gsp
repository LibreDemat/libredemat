<form id="childInformationSheet_${child.id}" method="post" name="editFieldsForm" action="${g.createLink(action:'childInformationSheet', id : child.id)}">
  	<dt>${message(code:'homeFolder.individual.header.informationSheet.emailEnfant')}</dt>
  	<dd>
    	<input type="text" name="emailEnfant" value="${child?.childInformationSheet?.emailEnfant}" 
    		   title="${message(code:'homeFolder.individual.property.informationSheet.email.validationError')}" 
    		   class="validate-email" />
  	</dd>
  	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.telephonePortable')}</dt>
	<dd>
		<input type="text" name="telephonePortable" value="${child?.childInformationSheet?.telephonePortable}" 
			   title="${message(code:'homeFolder.individual.property.informationSheet.phone.validationError')}" 
    		   class="validate-phone" />
	</dd>

	<!-- Personnes autorisées -->            
    <dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise')}</dt>
    
    <dt class="${informationSheetRequiredFieldsActived ? 'required' : '' }">${message(code:'homeFolder.individual.header.informationSheet.personneAutorise1')}</dt>
    <dt class="${informationSheetRequiredFieldsActived ? 'required' : '' }">${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom1')} ${informationSheetRequiredFieldsActived ? '*' : '' }</dt>
    <dd class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
	    <input type="text" id="personneAutoriseNom1" name="personneAutoriseNom1" value="${child?.childInformationSheet?.personneAutoriseNom1}" 
	      class="${informationSheetRequiredFieldsActived ? 'required' : '' } ${flash.invalidFields?.contains('personneAutoriseNom1') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom1')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom1')}</dt>
    <dd class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
	    <input type="text" id="personneAutorisePrenom1" name="personneAutorisePrenom1" value="${child?.childInformationSheet?.personneAutorisePrenom1}" 
	      class="${flash.invalidFields?.contains('personneAutorisePrenom1') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom1')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone1')}</dt>
    <dd class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
	    <input type="text" id="personneAutoriseTelephone1" name="personneAutoriseTelephone1" value="${child?.childInformationSheet?.personneAutoriseTelephone1}" 
	      class="${flash.invalidFields?.contains('personneAutoriseTelephone1') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone1')}" />             
    </dd>

    <dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise2')}</dt>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom2')}</dt>	
    <dd>
	    <input type="text" id="personneAutoriseNom2" name="personneAutoriseNom2" value="${child?.childInformationSheet?.personneAutoriseNom2}" 
	      class="${flash.invalidFields?.contains('personneAutoriseNom2') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom2')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom2')}</dt>
    <dd>	
	    <input type="text" id="personneAutorisePrenom2" name="personneAutorisePrenom2" value="${child?.childInformationSheet?.personneAutorisePrenom2}" 
	      class="${flash.invalidFields?.contains('personneAutorisePrenom2') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom2')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone2')}</dt>
    <dd>	
	    <input type="text" id="personneAutoriseTelephone2" name="personneAutoriseTelephone2" value="${child?.childInformationSheet?.personneAutoriseTelephone2}" 
	      class="${flash.invalidFields?.contains('personneAutoriseTelephone2') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone2')}" />             
    </dd>
    
    <dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise3')}</dt>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom3')}</dt>	
    <dd>
	    <input type="text" id="personneAutoriseNom3" name="personneAutoriseNom3" value="${child?.childInformationSheet?.personneAutoriseNom3}" 
	      class="${flash.invalidFields?.contains('personneAutoriseNom3') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom3')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom3')}</dt>
    <dd>	
	    <input type="text" id="personneAutorisePrenom3" name="personneAutorisePrenom3" value="${child?.childInformationSheet?.personneAutorisePrenom3}" 
	      class="${flash.invalidFields?.contains('personneAutorisePrenom3') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom3')}" />
    </dd>
    <dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone3')}</dt>
    <dd>	
	    <input type="text" id="personneAutoriseTelephone3" name="personneAutoriseTelephone3" value="${child?.childInformationSheet?.personneAutoriseTelephone3}" 
	      class="${flash.invalidFields?.contains('personneAutoriseTelephone3') ? 'validation-failed' : ''}"
	      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone3')}" />             
    </dd>    
      
	<dt>${message(code:'homeFolder.individual.property.informationSheet.nomOrganismeAssurance')}</dt>
	<dd class="required">
		<input class="required" type="text" name="nomOrganismeAssurance" value="${child?.childInformationSheet?.nomOrganismeAssurance}" />
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.numeroOrganismeAssurance')}</dt>
	<dd class="required">
		<input class="required" type="text" name="numeroOrganismeAssurance" value="${child?.childInformationSheet?.numeroOrganismeAssurance}" />
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.nomMedecinTraitantCourt')}</dt>
	<dd>
		<input type="text" name="nomMedecinTraitant" value="${child?.childInformationSheet?.nomMedecinTraitant}" />
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.telephoneMedecinTraitantCourt')}</dt>
	<dd>
		<input type="text" name="telephoneMedecinTraitant" value="${child?.childInformationSheet?.telephoneMedecinTraitant}" />
	</dd>
	
	<dt>${message(code:'homeFolder.individual.header.informationSheet.allergies')}</dt>
	<dd>
		<textarea id="allergie" name="allergie" 
		class="${flash.invalidFields?.contains('allergie') ? 'validation-failed' : ''}"
		title="" 
		rows="3" cols="17">${child?.childInformationSheet?.allergie}</textarea>
	</dd>

	<g:if test="${dietsList}">
		<dt>${message(code:'homeFolder.individual.header.informationSheet.regimeAlimentaire')}</dt>
		<dd>
		<ul>
		<g:each in="${dietsListKey}" var="dietKey">
			<li>
			<input type="checkbox" id="${dietKey}" name="${dietKey}" 
				<g:each in="${child?.childInformationSheet?.diets}" var="diet" status="i">
					<g:if test="${dietKey == diet.getType().name()}">checked="checked" </g:if>
				</g:each> />
			<span  >${dietsList?.get(dietKey)}</span>
			</li>
		</g:each>
		</ul>
		</dd>
	</g:if>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinBcg')}</dt>
	<dd>
		<input type="text" id="vaccinBcg" name="vaccinBcg" size="10"  class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinBcg)}" />
	    <a>
	       <img id="vaccinBcgShow" src="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <div id="vaccinBcgCalContainer" class="yui-cal yui-calcontainer"></div>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinDtPolio')}</dt>
	<dd>
		<input type="text" id="vaccinDtPolio" name="vaccinDtPolio" size="10" class="required validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinDtPolio)}" />
	    <a>
	       <img id="vaccinDtPolioShow" src="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <div id="vaccinDtPolioCalContainer" class="yui-cal yui-calcontainer"></div>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinInjectionSerum')}</dt>
	<dd>
		<input type="text" id="vaccinInjectionSerum" name="vaccinInjectionSerum" size="10" class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinInjectionSerum)}" />
	    <a>
	       <img id="vaccinInjectionSerumShow" src="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <div id="vaccinInjectionSerumCalContainer" class="yui-cal yui-calcontainer"></div>
	    
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinRor')}</dt>
	<dd>
	
		<input type="text" id="vaccinRor" name="vaccinRor" size="10" class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinRor)}" />
	    <a>
	       <img id="vaccinRorShow" src="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <div id="vaccinRorCalContainer" class="yui-cal yui-calcontainer"></div>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinTetracoqPentacoq')}</dt>
	<dd>
		
		<input type="text" id="vaccinTetracoqPentacoq" name="vaccinTetracoqPentacoq" size="10" class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinTetracoqPentacoq)}" />
	    <a>
	       <img id="vaccinTetracoqPentacoqShow" src="${resource(dir:'css/backoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <div id="vaccinTetracoqPentacoqCalContainer" class="yui-cal yui-calcontainer"></div>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinAutre')}</dt>
	<dd>
		<input type="text" name="vaccinAutre" value="${child?.childInformationSheet?.vaccinAutre}" />
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.recommandationParent')}</dt>
	<dd>
		<textarea id="recommandationParent" name="recommandationParent" 
		class="${flash.invalidFields?.contains('recommandationParent') ? 'validation-failed' : ''}"
		title="" 
		rows="3" cols="17">${child?.childInformationSheet?.recommandationParent}</textarea>	
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.difficulteSante')}</dt>
	<dd>
		<textarea id="difficulteSante" name="difficulteSante" 
		class="${flash.invalidFields?.contains('difficulteSante') ? 'validation-failed' : ''}"
		title="" 
		rows="3" cols="17">${child?.childInformationSheet?.difficulteSante}</textarea>		
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.projetAccueilIndividualiseCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input type="radio" value="${it}" name="projetAccueilIndividualise" 
					${it == child?.childInformationSheet?.projetAccueilIndividualise ? 'checked="checked"': ''} 
					class="required" />
        			<label for="projetAccueilIndividualise_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationDroitImageCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input type="radio" value="${it}" name="autorisationDroitImage" 
					${it == child?.childInformationSheet?.autorisationDroitImage ? 'checked="checked"': ''} 
					class="required"/>
        			<label for="autorisationDroitImage_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationMaquillageCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input class="required" type="radio" value="${it}" name="autorisationMaquillage" ${it == child?.childInformationSheet?.autorisationMaquillage ? 'checked="checked"': ''} />
        			<label for="autorisationMaquillage_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterVehiculeMunicipalCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input class="required" type="radio" value="${it}" name="autorisationTransporterVehiculeMunicipal" ${it == child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal ? 'checked="checked"': ''} />
        			<label for="autorisationTransporterVehiculeMunicipal_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterTransportCommunCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input class="required" type="radio" value="${it}" name="autorisationTransporterTransportCommun" ${it == child?.childInformationSheet?.autorisationTransporterTransportCommun ? 'checked="checked"': ''} />
        			<label for="autorisationTransporterTransportCommun_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationHospitalisationCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input class="required" type="radio" value="${it}" name="autorisationHospitalisation" ${it == child?.childInformationSheet?.autorisationHospitalisation ? 'checked="checked"': ''} />
        			<label for="autorisationHospitalisation_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationRentrerSeulCourt')}</dt>
	<dd>
		<ul class="yes-no">
			<g:each in="${[true,false]}">
        		<li>
					<input class="required" type="radio" value="${it}" name="autorisationRentrerSeul" ${it == child?.childInformationSheet?.autorisationRentrerSeul ? 'checked="checked"': ''} />
        			<label for="autorisationRentrerSeul_${it ? 'yes' : 'no'}">${message(code:'message.' + (it ? 'yes' : 'no'))}</label>
        		</li>
        	</g:each>	
		</ul>
	</dd>
	
	<g:render template="edit/submit" model="['object':child, 'template':'childInformationSheet']" />
</form>
