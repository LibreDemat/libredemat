CREATE TABLE tiers_informations
(
  id bigint NOT NULL,
  tiers_nom character varying(38),
  tiers_prenom character varying(38),
  tiers_telephone character varying(10),
  school_transport_registration_request_id bigint,
  tiers_autorises_index integer,
  CONSTRAINT tiers_informations_pkey PRIMARY KEY (id),
  CONSTRAINT fk58c18b7589395924 FOREIGN KEY (school_transport_registration_request_id)
      REFERENCES school_transport_registration_request (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table school_transport_registration_request add column autorisation character varying(255);
alter table school_transport_registration_request add column est_maternelle_elementaire_autorisations boolean;
alter table school_transport_registration_request add column frere_ou_soeur_classe character varying(255);
alter table school_transport_registration_request add column frere_ou_soeur_ecole character varying(255);
alter table school_transport_registration_request add column frere_ou_soeur_nom character varying(38);
alter table school_transport_registration_request add column frere_ou_soeur_prenom character varying(38);
