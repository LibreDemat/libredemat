alter table holiday_security_request 
    add column animal_information varchar(255);

alter table holiday_security_request 
    add column is_animal_owner bool;

alter table holiday_security_request 
    add column is_security_company bool;

alter table holiday_security_request 
    add column other_contact_duplicate_key bool;

alter table holiday_security_request 
    add column security_company_address varchar(255);

alter table holiday_security_request 
    add column security_company_name varchar(255);

alter table holiday_security_request 
    add column security_company_telephone varchar(10);
