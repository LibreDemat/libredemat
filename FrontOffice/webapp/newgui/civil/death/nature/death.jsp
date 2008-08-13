<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cvq-forms" prefix="cvqf" %>

	<cvqf:form name="Nature" action="#" >
      <fieldset class="fieldset_zone">
        <ul class="insert_list">
          <li class="select_row">
			<label for="requesterQuality" class="label">Qualité du demandeur</label>
            <cvqf:select name="requesterQuality" mode="">
              <option value="">Choisissez la qualité du demandeur</option>
              <option value="Spouse">Son conjoint</option>
              <option value="Parent">Son père / sa mère</option>
              <option value="GrandParent">Son grand-père / sa grand-mère</option>
              <option value="Child">Son fils / sa fille</option>
              <option value="LegalRepresentant">Son représentant légal</option>
              <option value="Agent">Son mandataire</option>
              <option value="HeirFamily">Son héritier et aussi son frère ou sa soeur</option>
              <option value="Heir">Son héritier sans être son frère ou sa soeur</option>
              <option value="Authorized">Autorisé par le procureur de la République</option>
              <option value="LawyerNotary">Avocat, notaire</option>
              <option value="Other">Autre</option>
            </cvqf:select>
          </li>
        </ul>
        <ul class="insert_list" id="LawyerNotary">
          <li class="text_row">
			<label for="requesterQualityPrecision" class="label">Précisez</label>
            <cvqf:text name="requesterQualityPrecision" mode=""/>
          </li>
        </ul>
		<br class="clear-both" />
        <ul class="insert_list" id="Other">
          <li class="text_row">
			<label for="requesterQualityPrecision" class="label">Précisez</label>
            <cvqf:text name="requesterQualityPrecision" mode=""/>
          </li>
        </ul>
		<br class="clear-both" />
        <ul class="insert_list">
          <li class="text_row">
			<label for="deathLastName" class="label">Nom<span class="required">*</span></label>
            <cvqf:text name="deathLastName" mode="" maxlength="38"/>
          </li>
          <li class="text_row">
			<label for="deathFirstNames" class="label">Prénom(s)<span class="required">*</span></label>
            <cvqf:text name="deathFirstNames" mode=""/>
          </li>
          <li class="date_row">
			<label for="deathDate" class="label">Date du décès<span class="required">*</span></label>
            <cvqf:text name="deathDate" mode="" maxlength="10"/>
            <span class="inline_text">ex: 21/06/1956</span>
          </li>
          <li class="text_row">
			<label for="deathCity" class="label">Ville de décès<span class="required">*</span></label>
            <cvqf:text name="deathCity" mode="" maxlength="32"/>
          </li>
          <li class="text_row">
			<label for="deathPostalCode" class="label">Département de décès<span class="required">*</span></label>
            <cvqf:text name="deathPostalCode" mode="" maxlength="2"/>
          </li>
        </ul>
      </fieldset>
	</cvqf:form>
	<script type="text/javascript">
		function validationData() {
   		  this.requesterQuality = new Function("key","this.label='Qualité du demandeur'; this.msg=null; this.required=false; this.minlength=0; this.maxlength=0; this.type=null; return this[key];");
     		  this.requesterQualityPrecision = new Function("key","this.label='Précisez'; this.msg=null; this.required=false; this.minlength=0; this.maxlength=0; this.type=null; return this[key];");
     		  this.requesterQualityPrecision = new Function("key","this.label='Précisez'; this.msg=null; this.required=false; this.minlength=0; this.maxlength=0; this.type=null; return this[key];");
     		  this.deathLastName = new Function("key","this.label='Nom'; this.msg=null; this.required=true; this.mask=/[A-Z.]*/; this.minlength=0; this.maxlength=38; this.transform='uppercase'; this.type=null; return this[key];");
     		  this.deathFirstNames = new Function("key","this.label='Prénom(s)'; this.msg=null; this.required=true; this.minlength=0; this.maxlength=0; this.type=null; return this[key];");
     		  this.deathDate = new Function("key","this.label='Date du décès'; this.msg=null; this.required=true; this.mask=/[0-9]{1,2}[\\/][0-9]{1,2}[\\/][0-9]{4}/; this.minlength=0; this.maxlength=10; this.transform='date'; this.type=null; return this[key];");
     		  this.deathCity = new Function("key","this.label='Ville de décès'; this.msg=null; this.required=true; this.minlength=0; this.maxlength=32; this.type=null; return this[key];");
     		  this.deathPostalCode = new Function("key","this.label='Département de décès'; this.msg=null; this.required=true; this.mask=/[0-9]{2}/; this.minlength=0; this.maxlength=2; this.type=null; return this[key];");
  		}
        updateDisplay("requesterQuality");
  		setFocus("Nature");
	</script>
