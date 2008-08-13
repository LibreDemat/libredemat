<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cvq-forms" prefix="cvqf" %>

	<cvqf:form name="Inscription" action="#" mode="static">
      <ul class="confirm_list">
        <li class="text_row">
          <p class="label">            
            Nom
          </p>
          <p class="text">
            <cvqf:text name="subjectChildLastName" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Prénom
          </p>
          <p class="text">
            <cvqf:text name="subjectChildFirstName" mode="static" maxlength="38"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Né(e) le
          </p>
          <p class="text">
            <cvqf:text name="subjectChildBirthDate" mode="static" maxlength="10"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Téléphone en cas d'urgence
          </p>
          <p class="text">
            <cvqf:text name="urgencyPhone" mode="static" maxlength="10"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Jours de présence
          </p>
          <p class="text">
            <cvqf:check name="canteenAttendingDays" mode="static" repository="CanteenAttendingDays">
            </cvqf:check>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Régime alimentaire
          </p>
          <p class="text">
            <cvqf:radio name="foodDiet" mode="static" repository="FoodDiet" label="[Oui,Non]"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Allergie
          </p>
          <p class="text">
            <cvqf:radio name="foodAllergy" mode="static" label="[Oui,Non]" />
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Nom du médecin traitant
          </p>
          <p class="text">
            <cvqf:text name="doctorName" mode="static"/>
          </p>
        </li>
        <li class="text_row">
          <p class="label">            
            Téléphone
          </p>
          <p class="text">
            <cvqf:text name="doctorPhone" mode="static" maxlength="10"/>
          </p>
        </li>
      </ul>
	</cvqf:form>
	<script type="text/javascript">
		function validationData() {
		}
  		setFocus("Inscription");
	</script>
