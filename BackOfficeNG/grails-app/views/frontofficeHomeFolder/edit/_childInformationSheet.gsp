<form id="childInformationSheet_${child.id}" name="editFieldsForm" method="post" action="${g.createLink(action:'child')}">

	<span id="errorFields" name="errorFields" class="error" ></span>
	<p>
		<label>
	    	${message(code:'request.message.requiredFieldsNotice')}
	  	</label>
	  	<br/>
	</p>
	
	<a
		href="${createLink(action:'child', params:['id':child.id, 'fragment':'informationSheet'])}#informationSheet"
		class="modify"> ${message(code:'action.modify')} 
	</a>

	<label for="emailEnfant">
    	${message(code:'homeFolder.individual.header.informationSheet.emailEnfant')}
  	</label>
  	<input type="text" id="emailEnfant" name="emailEnfant" value="${child?.childInformationSheet?.emailEnfant}"
    	class="validate-email ${flash.invalidFields?.contains('emailEnfant') ? 'validation-failed' : ''}"
      	title="${message(code:'homeFolder.individual.header.informationSheet.emailEnfant')}" 
      	size="50"/>

    <label for="telephonePortable">
    	${message(code:'homeFolder.individual.property.informationSheet.telephonePortable')}
    </label>
    <input type="text" id="telephonePortable" name="telephonePortable" value="${child?.childInformationSheet?.telephonePortable}" 
      class="${flash.invalidFields?.contains('telephonePortable') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.telephonePortable')}" 
      size="15"/>

	<!-- Personnes autorisÃ©es -->            
    <h4>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise')}</h4>
    <p><label>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise.information')}</label></p>
    
    <label class="${informationSheetRequiredFieldsActived ? 'required' : '' }">${message(code:'homeFolder.individual.header.informationSheet.personneAutorise1')}</label>

    <label for="personneAutoriseNom1" class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom1')} ${informationSheetRequiredFieldsActived ? ' *' : '' }
    </label>	
    <input type="text" id="personneAutoriseNom1" name="personneAutoriseNom1" value="${child?.childInformationSheet?.personneAutoriseNom1}" 
      class="${informationSheetRequiredFieldsActived ? 'required' : '' } ${flash.invalidFields?.contains('personneAutoriseNom1') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom1')}" />
    
    <label for="personneAutorisePrenom1" class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom1')} ${informationSheetRequiredFieldsActived ? ' *' : '' }
    </label>	
    <input type="text" id="personneAutorisePrenom1" name="personneAutorisePrenom1" value="${child?.childInformationSheet?.personneAutorisePrenom1}" 
      class="${informationSheetRequiredFieldsActived ? 'required' : '' } ${flash.invalidFields?.contains('personneAutorisePrenom1') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom1')}" />
    
    <label for="personneAutoriseTelephone1" class="${informationSheetRequiredFieldsActived ? 'required' : '' }">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone1')} ${informationSheetRequiredFieldsActived ? ' *' : '' }
    </label>	
    <input type="text" id="personneAutoriseTelephone1" name="personneAutoriseTelephone1" value="${child?.childInformationSheet?.personneAutoriseTelephone1}" 
      class="${informationSheetRequiredFieldsActived ? 'required' : '' } ${flash.invalidFields?.contains('personneAutoriseTelephone1') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone1')}" />             
       

	<label>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise2')}</label>          
    <label for="personneAutoriseNom2">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom2')} 
    </label>	
    <input type="text" id="personneAutoriseNom2" name="personneAutoriseNom2" value="${child?.childInformationSheet?.personneAutoriseNom2}" 
      class="${flash.invalidFields?.contains('personneAutoriseNom2') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom2')}" />
    
    <label for="personneAutorisePrenom2">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom2')} 
    </label>	
    <input type="text" id="personneAutorisePrenom2" name="personneAutorisePrenom2" value="${child?.childInformationSheet?.personneAutorisePrenom2}" 
      class="${flash.invalidFields?.contains('personneAutorisePrenom2') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom2')}" />
    
    <label for="personneAutoriseTelephone2">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone2')} 
    </label>	
    <input type="text" id="personneAutoriseTelephone2" name="personneAutoriseTelephone2" value="${child?.childInformationSheet?.personneAutoriseTelephone2}" 
      class="${flash.invalidFields?.contains('personneAutoriseTelephone2') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone2')}" /> 
                
	<label>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise3')}</label>                
    <label for="personneAutoriseNom3">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom3')} 
    </label>	
    <input type="text" id="personneAutoriseNom3" name="personneAutoriseNom3" value="${child?.childInformationSheet?.personneAutoriseNom3}" 
      class="${flash.invalidFields?.contains('personneAutoriseNom3') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom3')}" />            
    
    <label for="nomOrganismeAssurance">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom3')} 
    </label>	
    <input type="text" id="personneAutorisePrenom3" name="personneAutorisePrenom3" value="${child?.childInformationSheet?.personneAutorisePrenom3}" 
      class="${flash.invalidFields?.contains('personneAutorisePrenom3') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom3')}" /> 
    
    <label for="personneAutoriseTelephone3">
    	${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone3')} 
    </label>	
    <input type="text" id="personneAutoriseTelephone3" name="personneAutoriseTelephone3" value="${child?.childInformationSheet?.personneAutoriseTelephone3}" 
      class="${flash.invalidFields?.contains('personneAutoriseTelephone3') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone3')}" />        
           
          
	<h4>${message(code:'homeFolder.individual.header.informationSheet.assurance')}</h4>
	<p><label>${message(code:'homeFolder.individual.header.informationSheet.assurance.information')}</label></p>
	
    <label for="nomOrganismeAssurance" class="${informationSheetRequired}">
    	${message(code:'homeFolder.individual.property.informationSheet.nomOrganismeAssurance')} 
    		${informationSheetRequired?.contains('required') ? '*' : ''}
    </label>	
    <input type="text" id="nomOrganismeAssurance" name="nomOrganismeAssurance" value="${child?.childInformationSheet?.nomOrganismeAssurance}" 
      class="required ${flash.invalidFields?.contains('nomOrganismeAssurance') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.nomOrganismeAssurance')}" />
      
    <label for="numeroOrganismeAssurance" class="${informationSheetRequired}">
    	${message(code:'homeFolder.individual.property.informationSheet.numeroOrganismeAssurance')} 
    		${informationSheetRequired?.contains('required') ? '*' : ''}
    </label>	
    <input type="text" id="numeroOrganismeAssurance" name="numeroOrganismeAssurance" value="${child?.childInformationSheet?.numeroOrganismeAssurance}" 
      class="required ${flash.invalidFields?.contains('numeroOrganismeAssurance') ? 'validation-failed' : ''}" 
      title="${message(code:'homeFolder.individual.property.informationSheet.numeroOrganismeAssurance')}" />

	<h4>${message(code:'homeFolder.individual.header.informationSheet.medecinTraitant')}</h4>
    
    <label for="nomMedecinTraitant">
    	${message(code:'homeFolder.individual.property.informationSheet.nomMedecinTraitant')}
    </label>	
    <input type="text" id="nomMedecinTraitant" name="nomMedecinTraitant" value="${child?.childInformationSheet?.nomMedecinTraitant}" 
      class="${flash.invalidFields?.contains('nomMedecinTraitant') ? 'validation-failed' : ''}" 
      title="${message(code:'homeFolder.individual.property.informationSheet.nomMedecinTraitant')}" />
      
    <label for="telephoneMedecinTraitant">
    	${message(code:'homeFolder.individual.property.informationSheet.telephoneMedecinTraitant')}
    </label>
    <input type="text" id="telephoneMedecinTraitant" name="telephoneMedecinTraitant" value="${child?.childInformationSheet?.telephoneMedecinTraitant}" 
      class="${flash.invalidFields?.contains('telephoneMedecinTraitant') ? 'validation-failed' : ''}"
      title="${message(code:'homeFolder.individual.property.informationSheet.telephoneMedecinTraitant')}" 
      size="15"/>

	<h4>${message(code:'homeFolder.individual.header.informationSheet.allergies')}</h4>
	<textarea id="allergie" name="allergie" 
		class="${flash.invalidFields?.contains('allergie') ? 'validation-failed' : ''}"
		title="" 
		rows="5" >${child?.childInformationSheet?.allergie}</textarea>

	<g:if test="${dietsList}">
		<h4>${message(code:'homeFolder.individual.header.informationSheet.regimeAlimentaire')}</h4>
		<p><label>${message(code:'homeFolder.individual.header.informationSheet.regimeAlimentaire.information')}</label></p>
		
		<g:each in="${dietsListKey}" var="dietKey">
			<input type="checkbox" id=${dietKey} name=${dietKey} 
				<g:each in="${child?.childInformationSheet?.diets}" var="diet" status="i">
					<g:if test="${dietKey == diet.getType().name()}">checked="checked" </g:if>
				</g:each>
				>
			</input>
			${dietsList?.get(dietKey)}
			<br/>
		</g:each>
		<p>
			<label>${message(code:'homeFolder.individual.header.informationSheet.regimeAlimentaire.informationSuite')}</label>
			<br/>
		</p>
	</g:if>
	
	<h4>${message(code:'homeFolder.individual.header.informationSheet.vaccins')}</h4>
    <label>${message(code:'homeFolder.individual.property.informationSheet.vaccinBcg')}</label>
    <table><tr><td>
      	<input type="text" id="vaccinBcg" name="vaccinBcg" size="10"  class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinBcg)}" />
	    </td>
	    <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
	    <a>
	       <img id="vaccinBcgShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <span id="vaccinBcgCalContainer" class="yui-cal yui-calcontainer"></span>
	</td></tr></table>
      			
    <label class="${informationSheetRequired}">${message(code:'homeFolder.individual.property.informationSheet.vaccinDtPolio')} 
		${informationSheetRequired?.contains('required') ? '*' : ''}
	</label>
	 <table><tr><td>
      	<input type="text" id="vaccinDtPolio" name="vaccinDtPolio" size="10"  class="required validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinDtPolio)}" />
	    </td>
	    <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
	    <a>
	       <img id="vaccinDtPolioShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <span id="vaccinDtPolioCalContainer" class="yui-cal yui-calcontainer"></span>
	</td></tr></table>
      	
    <label>${message(code:'homeFolder.individual.property.informationSheet.vaccinInjectionSerum')}</label>
    <table><tr><td>
      	<input type="text" id="vaccinInjectionSerum" name="vaccinInjectionSerum" size="10"  class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinInjectionSerum)}" />
	    </td>
	    <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
	    <a>
	       <img id="vaccinInjectionSerumShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <span id="vaccinInjectionSerumCalContainer" class="yui-cal yui-calcontainer"></span>
	</td></tr></table>  

    <label>${message(code:'homeFolder.individual.property.informationSheet.vaccinRor')}</label>
    <table><tr><td>
      	<input type="text" id="vaccinRor" name="vaccinRor" size="10"  class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinRor)}" />
	    </td>
	    <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
	    <a>
	       <img id="vaccinRorShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <span id="vaccinRorCalContainer" class="yui-cal yui-calcontainer"></span>
	</td></tr></table>    

    <label>${message(code:'homeFolder.individual.property.informationSheet.vaccinTetracoqPentacoq')}</label>
     <table><tr><td>
      	<input type="text" id="vaccinTetracoqPentacoq" name="vaccinTetracoqPentacoq" size="10"  class="validate-calendar"
				value="${g.formatDate(formatName:'format.date', date: child?.childInformationSheet?.vaccinTetracoqPentacoq)}" />
	    </td>
	    <td class="yui-skin-sam calendar" style="padding-top: 4px;vertical-align:top">
	    <a>
	       <img id="vaccinTetracoqPentacoqShow" src="${resource(dir:'css/frontoffice/yui/calendar',file:'calendar.gif')}" class="calendar" />
	    </a>
	    <span id="vaccinTetracoqPentacoqCalContainer" class="yui-cal yui-calcontainer"></span>
	</td></tr></table>      
                  
    <label for="vaccinAutre">
    	${message(code:'homeFolder.individual.property.informationSheet.vaccinAutre')}
    </label>	
    <input type="text" id="vaccinAutre" name="vaccinAutre" value="${child?.childInformationSheet?.vaccinAutre}" 
      class="${flash.invalidFields?.contains('vaccinAutre') ? 'validation-failed' : ''}" 
      title="${message(code:'homeFolder.individual.property.informationSheet.vaccinAutre')}" />
    
	<h4>${message(code:'homeFolder.individual.property.informationSheet.recommandationParent')}</h4>
	<p><label>${message(code:'homeFolder.individual.header.informationSheet.recommandationParentSuite')}</label></p>
	<textarea name="recommandationParent" id="recommandationParent"
		class="${flash.invalidFields?.contains('recommandationParent') ? 'validation-failed' : ''}"
		title="" 
		rows="5" >${child?.childInformationSheet?.recommandationParent}</textarea>
	
	<p><label>${message(code:'homeFolder.individual.header.informationSheet.difficulteSanteSuite')}</label></p>
	<textarea name="difficulteSante" id="difficulteSante"
		class="${flash.invalidFields?.contains('difficulteSante') ? 'validation-failed' : ''}"
		title="" 
		rows="5" >${child?.childInformationSheet?.difficulteSante}</textarea>
	
	<p>
		<label>
			<input type="checkbox" id="projetAccueilIndividualise" name="projetAccueilIndividualise" 
				<g:if test="${child?.childInformationSheet?.projetAccueilIndividualise}">checked="checked" </g:if> >
			</input>
			${message(code:'homeFolder.individual.property.informationSheet.projetAccueilIndividualise')}
		</label>
	</p>
	
	<h4>${message(code:'homeFolder.individual.header.informationSheet.autorisation')}</h4>
	
	<p><label>${message(code:'homeFolder.individual.header.informationSheet.autorisation.informationDroitImage')}</label></p>
	
    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationDroitImage" class="required">
                ${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationDroitImage')} *
            </label>
            <select id="autorisationDroitImage" name="autorisationDroitImage"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationDroitImage ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationDroitImage == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
            <label>
                <input type="checkbox" id="autorisationDroitImage" name="autorisationDroitImage" 
                    <g:if test="${child?.childInformationSheet?.autorisationDroitImage}">checked="checked" </g:if> >
                </input>
                ${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationDroitImage')}
            </label>
        </g:else>
		<br/>
	</p>
	
    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationMaquillage" class="required">
                ${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationMaquillage')} *
            </label>
            <select id="autorisationMaquillage" name="autorisationMaquillage"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationMaquillage ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationMaquillage == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
        <label>
            <input type="checkbox" id="autorisationMaquillage" name="autorisationMaquillage" 
                <g:if test="${child?.childInformationSheet?.autorisationMaquillage}">checked="checked" </g:if> >
            </input>
            ${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationMaquillage')}
        </label>
        </g:else>
        <br/>
    </p>

    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationTransporterVehiculeMunicipal" class="required">
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterVehiculeMunicipal')} *
            </label>
            <select id="autorisationTransporterVehiculeMunicipal" name="autorisationTransporterVehiculeMunicipal"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
            <label>
                <input type="checkbox" id="autorisationTransporterVehiculeMunicipal" name="autorisationTransporterVehiculeMunicipal" 
                    <g:if test="${child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal}">checked="checked" </g:if> >
                </input>
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterVehiculeMunicipal')}
            </label>
        </g:else>
        <br/>
    </p>

    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationTransporterTransportCommun" class="required">
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterTransportCommun')} *
            </label>
            <select id="autorisationTransporterTransportCommun" name="autorisationTransporterTransportCommun"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationTransporterTransportCommun ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationTransporterTransportCommun == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
            <label>
                <input type="checkbox" id="autorisationTransporterTransportCommun" name="autorisationTransporterTransportCommun" 
                    <g:if test="${child?.childInformationSheet?.autorisationTransporterTransportCommun}">checked="checked" </g:if> >
                </input>
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterTransportCommun')}
            </label>
        </g:else>
        <br/>
    </p>

    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationHospitalisation" class="required">
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationHospitalisation')} *
            </label>
            <select id="autorisationHospitalisation" name="autorisationHospitalisation"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationHospitalisation ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationHospitalisation == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
            <label>
                <input type="checkbox" id="autorisationHospitalisation" name="autorisationHospitalisation" 
                    <g:if test="${child?.childInformationSheet?.autorisationHospitalisation}">checked="checked" </g:if> >
                </input>
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationHospitalisation')}
            </label>
        </g:else>
        <br/>
    </p>

    <p>
        <g:if test="${informationSheetRequiredFieldsActived}">
            <label for="autorisationRentrerSeul" class="required">
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationRentrerSeul')} *
            </label>
            <select id="autorisationRentrerSeul" name="autorisationRentrerSeul"  class="required validate-not-first">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <option value="yes" ${child?.childInformationSheet?.autorisationRentrerSeul ? 'selected="selected"': ''}><g:message code="message.yes" />
              <option value="no" ${child?.childInformationSheet?.autorisationRentrerSeul == false ? 'selected="selected"': ''}><g:message code="message.no" />
            </select>
        </g:if>
        <g:else>
            <label>
                <input type="checkbox" id="autorisationRentrerSeul" name="autorisationRentrerSeul" 
                    <g:if test="${child?.childInformationSheet?.autorisationRentrerSeul}">checked="checked" </g:if> >
                </input>
                ${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationRentrerSeul')}
            </label>
        </g:else>
		<br/>
	</p>
		
	<p>
		<label>
			${message(code:'homeFolder.individual.header.informationSheet.autorisation.informationLoi')}
			<br/>
			${message(code:'homeFolder.individual.header.informationSheet.autorisation.informationChangement')}
		</label>
	</p>

    <g:if test="${informationSheetRequiredFieldsActived}">
    <g:if test="${availableRules.contains('acceptationReglementInterieur')}">
    <br/>
    <ul>
      <li>
        <input type="hidden" name="_childInformationSheetAcceptationReglement">
        <input type="checkbox" id="childInformationSheetAcceptationReglement" name="childInformationSheetAcceptationReglement" class="required  validate-acceptance" title="" value="true">
        <label for="childInformationSheetAcceptationReglement" class="required" style="font-size:1.2em">
            ${message(code:'homeFolder.individual.header.informationSheet.autorisation.acceptance')}
              <a target="_blank" href="${createLink(controller:'localAuthorityResource', action:'rule', params:['requestTypeLabel':'childInformationSheet', 'filename':'acceptationReglementInterieur']).encodeAsXML()}"><span><g:message code="request.action.consult.rules" /></span></a>
          <span></span>
        </label>
      </li>
    </ul>
    <br/>
    </g:if>
    </g:if>
    <g:render template="edit/submit" model="['individual':child, 'fragment':'informationSheet']" />
</form>
