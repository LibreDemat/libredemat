update child_care_center_registration_request set wednesday_period = 'ALL_DAY' where wednesday_period = 'allDay';
update child_care_center_registration_request set wednesday_period = 'HALF_DAY' where wednesday_period = 'halfDay';
update child_care_center_registration_request set thursday_period = 'ALL_DAY' where thursday_period = 'allDay';
update child_care_center_registration_request set thursday_period = 'HALF_DAY' where thursday_period = 'halfDay';
update child_care_center_registration_request set friday_period = 'ALL_DAY' where friday_period = 'allDay';
update child_care_center_registration_request set friday_period = 'HALF_DAY' where friday_period = 'halfDay';

update child_care_center_registration_request set subject_choice_gender = 'MALE' where subject_choice_gender = 'Male';
update child_care_center_registration_request set subject_choice_gender = 'FEMALE' where subject_choice_gender = 'Female';
update child_care_center_registration_request set subject_choice_gender = 'UNKNOWN' where subject_choice_gender = 'Unknown';
