ALTER TABLE child ADD COLUMN child_information_sheet_id int8;

create table child_information_sheet (
    id int8 not null,
    allergie varchar(255),
    autorisation_droit_image bool,
    autorisation_hospitalisation bool,
    autorisation_maquillage bool,
    autorisation_rentrer_seul bool,
    autorisation_transporter_transport_commun bool,
    autorisation_transporter_vehicule_municipal bool,
    difficulte_sante varchar(255),
    email_enfant varchar(50),
    nom_medecin_traitant varchar(255),
    nom_organisme_assurance varchar(255),
    numero_organisme_assurance varchar(255),
    personne_autorise_nom1 varchar(255),
    personne_autorise_nom2 varchar(255),
    personne_autorise_nom3 varchar(255),
    personne_autorise_prenom1 varchar(255),
    personne_autorise_prenom2 varchar(255),
    personne_autorise_prenom3 varchar(255),
    personne_autorise_telephone1 varchar(32),
    personne_autorise_telephone2 varchar(32),
    personne_autorise_telephone3 varchar(32),
    pathologie_enfant bool,
    recommandation_parent varchar(255),
    telephone_medecin_traitant varchar(32),
    telephone_portable varchar(32),
    vaccin_autre varchar(255),
    vaccin_bcg timestamp,
    vaccin_dt_polio timestamp,
    vaccin_injection_serum timestamp,
    vaccin_ror timestamp,
    vaccin_tetracoq_pentacoq timestamp,
    date_validation timestamp,
    primary key (id)
 );

create table diet (
    id int8 not null,
    diet_type varchar(255),
    child_information_sheet_id int8,
    primary key (id)
);

alter table diet 
  add constraint FK2F0BF41A5D42C9 
  foreign key (child_information_sheet_id) 
  references child_information_sheet;

alter table child 
    add constraint FK5A3F51C1A5D42C9 
    foreign key (child_information_sheet_id) 
    references child_information_sheet;
