alter table park_card_request add column information_card_limit_rest varchar(255);
alter table park_card_request add column payment_reference varchar(255);
alter table park_card_request add column payment_total varchar(255);
alter table park_card_request add column subject_address_id int8;

alter table park_card_request 
    add constraint FK9647369558B2AF6 
    foreign key (subject_address_id) 
    references address;
