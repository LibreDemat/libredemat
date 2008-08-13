<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cvq-forms" prefix="cvqf" %>

	<cvqf:form name="Subject" action="#" mode="static">
      <ul class="confirm_list">
        <li class="text_row">
          <p class="label">            
            Nom
          </p>
          <p class="text">
            <cvqf:text name="subjectAdultLastName" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Prénom
          </p>
          <p class="text">
            <cvqf:text name="subjectAdultFirstName" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">
				Etg. - Esc. - App.				
          </p>
          <p class="text">
    		<cvqf:text name="subjectAdultAddressAdditionalDeliveryInformation" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">
				Imm. - Bât. - Rés.				
          </p>
          <p class="text">
    		<cvqf:text name="subjectAdultAddressAdditionalGeographicalInformation" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">
				N&deg;, libellé de la voie				
          </p>
          <p class="text">
    		<cvqf:text name="subjectAdultAddressStreetNumber" mode="static" maxlength="5"/>
    		<cvqf:text name="subjectAdultAddressStreetName" mode="static" maxlength="32" size="35"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">
				Lieu-dit, boite postale				
          </p>
          <p class="text">
    		<cvqf:text name="subjectAdultAddressPlaceNameOrService" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">
				Code postal, Localité				
          </p>
          <p class="text">
    		<cvqf:text name="subjectAdultAddressPostalCode" mode="static" maxlength="5"/>
    		<cvqf:text name="subjectAdultAddressCity" mode="static" maxlength="32" size="35"/>
          </p>
        </li>

        <li class="text_row">
          <p class="label">            
            Téléphone portable
          </p>
          <p class="text">
            <cvqf:text name="subjectAdultMobilePhone" mode="static" maxlength="10"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Téléphone domicile
          </p>
          <p class="text">
            <cvqf:text name="subjectAdultHomePhone" mode="static" maxlength="10"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Courriel
          </p>
          <p class="text">
            <cvqf:text name="subjectAdultEmail" mode="static"/>
          </p>
        </li>
      </ul>
	</cvqf:form>
	<script type="text/javascript">
		function validationData() {
		}
  		setFocus("Subject");
	</script>
