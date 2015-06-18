alter table holiday_security_request 
    add column animal_information varchar(255),
    add column is_animal_owner bool,
    add column is_security_company bool,
    add column other_contact_duplicate_key bool,
    add column security_company_address varchar(255),
    add column security_company_name varchar(255),
    add column security_company_telephone varchar(10);
