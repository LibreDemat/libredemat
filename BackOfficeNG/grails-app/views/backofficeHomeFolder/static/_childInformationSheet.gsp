<g:if test="${child?.childInformationSheet?.validationDate}">
	${message(code:'homeFolder.individual.header.informationSheet.validationok')}
	<g:formatDate formatName="format.date" date="${child?.childInformationSheet?.validationDate}" />
</g:if>
<g:else>
	${message(code:'homeFolder.individual.header.informationSheet.validationko')}
</g:else>
  	
<dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutoriseCourt')}</dt>

<dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise1')}</dt>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom1')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseNom1}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom1')}</dt>
<dd>${child?.childInformationSheet?.personneAutorisePrenom1}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone1')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseTelephone1}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise2')}</dt>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom2')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseNom2}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom2')}</dt>
<dd>${child?.childInformationSheet?.personneAutorisePrenom2}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone2')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseTelephone2}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.personneAutorise3')}</dt>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseNom3')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseNom3}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutorisePrenom3')}</dt>
<dd>${child?.childInformationSheet?.personneAutorisePrenom3}</dd>
<dt>${message(code:'homeFolder.individual.property.informationSheet.personneAutoriseTelephone3')}</dt>
<dd>${child?.childInformationSheet?.personneAutoriseTelephone3}</dd>
 	
<dt>${message(code:'homeFolder.individual.header.informationSheet.emailEnfant')}</dt>
<dd>${child?.childInformationSheet?.emailEnfant}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.telephonePortable')}</dt>
<dd>${child?.childInformationSheet?.telephonePortable}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.nomOrganismeAssurance')}</dt>
<dd>${child?.childInformationSheet?.nomOrganismeAssurance}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.numeroOrganismeAssurance')}</dt>
<dd>${child?.childInformationSheet?.numeroOrganismeAssurance}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.nomMedecinTraitantCourt')}</dt>
<dd>${child?.childInformationSheet?.nomMedecinTraitant}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.telephoneMedecinTraitantCourt')}</dt>
<dd>${child?.childInformationSheet?.telephoneMedecinTraitant}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.allergies')}</dt>
<dd>${child?.childInformationSheet?.allergie}</dd>

<g:if test="${dietsList}">
	<dt>${message(code:'homeFolder.individual.header.informationSheet.regimeAlimentaire')}</dt>
		<br/>
			<g:each in="${dietsListKey}" var="dietKey">
			<dt/>
					<dd>
						${dietsList?.get(dietKey)}
						<g:set var="choix" value="" />
							<g:each in="${child?.childInformationSheet?.diets}" var="diet" status="i">
								<g:if test="${dietKey.toLowerCase() == diet.getType().name?.toLowerCase()}">
									<g:set var="choix" value="oui" /> 
								</g:if>
							</g:each>

						<g:if test="${choix == 'oui'}">
							${message(code:'message.yes')}
						</g:if>
						<g:else>
							${message(code:'message.no')}
						</g:else>
					</dd>
			</g:each>
</g:if>
	
<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinBcg')}</dt>
<dd><g:formatDate formatName="format.date" date="${child?.childInformationSheet?.vaccinBcg}"/></dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinDtPolio')}</dt>
<dd><g:formatDate formatName="format.date" date="${child?.childInformationSheet?.vaccinDtPolio}"/></dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinInjectionSerum')}</dt>
<dd><g:formatDate formatName="format.date" date="${child?.childInformationSheet?.vaccinInjectionSerum}"/></dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinRor')}</dt>
<dd><g:formatDate formatName="format.date" date="${child?.childInformationSheet?.vaccinRor}"/></dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinTetracoqPentacoq')}</dt>
<dd><g:formatDate formatName="format.date" date="${child?.childInformationSheet?.vaccinTetracoqPentacoq}"/></dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.vaccinAutre')}</dt>
<dd>${child?.childInformationSheet?.vaccinAutre}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.recommandationParent')}</dt>
<dd>${child?.childInformationSheet?.recommandationParent}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.difficulteSante')}</dt>
<dd>${child?.childInformationSheet?.difficulteSante}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.projetAccueilIndividualiseCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.projetAccueilIndividualise == null ? 'nc' : child?.childInformationSheet?.projetAccueilIndividualise ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationDroitImageCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationDroitImage == null ? 'nc' : child?.childInformationSheet?.autorisationDroitImage ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.property.informationSheet.autorisation.autorisationMaquillageCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationMaquillage == null ? 'nc' : child?.childInformationSheet?.autorisationMaquillage ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterVehiculeMunicipalCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal == null ? 'nc' : child?.childInformationSheet?.autorisationTransporterVehiculeMunicipal ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationTransporterTransportCommunCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationTransporterTransportCommun == null ? 'nc' : child?.childInformationSheet?.autorisationTransporterTransportCommun ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationHospitalisationCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationHospitalisation == null ? 'nc' : child?.childInformationSheet?.autorisationHospitalisation ? 'yes' : 'no'))}</dd>

<dt>${message(code:'homeFolder.individual.header.informationSheet.autorisation.autorisationRentrerSeulCourt')}</dt>
<dd>${message(code:'message.' + (child?.childInformationSheet?.autorisationRentrerSeul == null ? 'nc' : child?.childInformationSheet?.autorisationRentrerSeul ? 'yes' : 'no'))}</dd>



