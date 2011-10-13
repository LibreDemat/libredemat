alter table local_authority add column adresses_referential_url varchar(255);

alter table request_action add column external_service varchar(255);

alter table individual add column duplicate_alert bool;
alter table individual add column duplicate_data TEXT;

update individual set duplicate_alert = false;

-- an old state found in old DB
DELETE FROM request_action where resulting_state = 'Active';

-- clean existing DBs : remove crap left by HFMRs
delete from individual_role where owner_id is null;
delete from individual_role where owner_id in (select id from individual where home_folder_id is null);

-- add home folder's documents model
alter table document add column linked_home_folder_id int8;

-- add standalone home folder creation model
create table global_home_folder_configuration (
    id int8 not null,
    independentCreation bool not null,
    primary key (id)
);

create table home_folder_wished_document_types (
    global_home_folder_configuration_id int8 not null,
    document_type_id int8 not null,
    primary key (global_home_folder_configuration_id, document_type_id),
    unique (document_type_id)
);

alter table document
    add constraint FK335CD11BAE5B2A57
    foreign key (linked_home_folder_id)
    references home_folder;

alter table home_folder_wished_document_types
    add constraint FK1C339C7D8EAF8712
    foreign key (document_type_id)
    references document_type;

alter table home_folder_wished_document_types
    add constraint FK1C339C7DC3E3DBFF
    foreign key (global_home_folder_configuration_id)
    references global_home_folder_configuration;

alter table external_invoice_item_detail RENAME COLUMN quatity TO quantity;

-- migrate Notified state (no longer exist in 4.6) to Closed state
create or replace function migrate_notified() returns void as $$
  declare
    r record;
  begin
    for r in select * from request where state = 'NOTIFIED' or state = 'Notified' loop
      insert into request_action (id, agent_id, note, date, message, request_id, resulting_state, file, type) values (
        nextval('hibernate_sequence'),
        r.requester_id,
        null,
        r.creation_date,
        null,
        r.id,
        'CLOSED',
        null,
        'STATE_CHANGE'
      );
    end loop;
  end;
$$ LANGUAGE plpgsql;

select * from migrate_notified();
drop function migrate_notified();

update request set state = 'CLOSED' where state = 'NOTIFIED' or state = 'Notified';

-- TS Standard Electoral Roll Registration (CERFA)
create table standard_electoral_roll_registration_request (
        id int8 not null,
        ambassade_ou_poste_consulaire varchar(255),
        ancienne_commune varchar(32),
        commune_ou_localite_precedente varchar(32),
        date_naissance timestamp,
        departement_ancienne_commune varchar(255),
        deuxieme_prenom varchar(38),
        lieu_naissance_departement varchar(255),
        lieu_naissance_pays varchar(255),
        nationalite varchar(255),
        nom_marital varchar(38),
        nom_naissance varchar(38),
        pays_precedent varchar(255),
        pays_radiation varchar(255),
        precision_nationalite varchar(255),
        prenom varchar(38),
        sexe varchar(255),
        situation varchar(255),
        subdivision_administrative_precedente varchar(255),
        troisieme_prenom varchar(38),
        type_election varchar(255),
        type_inscription varchar(255),
        ville_naissance_code_postal varchar(32),
        primary key (id)
);
