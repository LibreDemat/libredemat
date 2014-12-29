-- update GSRR 

-- @Text Drop table global_school_registration_request_regime_alimentaire

alter table global_school_registration_request_regime_alimentaire 
    drop constraint if exists FK261E5D0CA7322BAE;

alter table global_school_registration_request_regime_alimentaire
    drop constraint if exists FK261E5D0C85DE12C2;

drop table if exists global_school_registration_request_regime_alimentaire cascade;

