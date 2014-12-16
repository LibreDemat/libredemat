alter table leisure_center_registration_request drop column centre_loisirs_json;
alter table leisure_center_registration_request drop column centre_loisirs_sumup;
alter table leisure_center_registration_request add column est_transport boolean;
alter table leisure_center_registration_request add column id_arret character varying(255);
alter table leisure_center_registration_request add column id_centre_loisirs character varying(255);
alter table leisure_center_registration_request add column id_ligne character varying(255);
alter table leisure_center_registration_request add column label_arret character varying(255);
alter table leisure_center_registration_request add column label_centre_loisirs character varying(255);
alter table leisure_center_registration_request add column label_ligne character varying(255);
alter table leisure_center_registration_request drop column lcrr_intention_frequentation;

CREATE TABLE leisure_center_registration_request_motifs_derogation_centre_lo
(
  leisure_center_registration_request_id bigint NOT NULL,
  motifs_derogation_centre_loisirs_id bigint NOT NULL,
  motifs_derogation_centre_loisirs_index integer NOT NULL,
  CONSTRAINT leisure_center_registration_request_motifs_derogation_cent_pkey PRIMARY KEY (leisure_center_registration_request_id, motifs_derogation_centre_loisirs_index),
  CONSTRAINT fk6899ccb75852f322 FOREIGN KEY (motifs_derogation_centre_loisirs_id)
      REFERENCES local_referential_data (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6899ccb79d930190 FOREIGN KEY (leisure_center_registration_request_id)
      REFERENCES leisure_center_registration_request (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table leisure_center_registration_request add column est_derogation boolean;
