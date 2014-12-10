drop table IF EXISTS urm_office_agent_mapping ;
drop table IF EXISTS urm_agent_workflow_mapping ;
drop table IF EXISTS urm_history_trace ;
drop table IF EXISTS urm_document ;
drop table IF EXISTS urm_demand ;
drop table IF EXISTS urm_knowledge_base ;
drop table IF EXISTS urm_workflow_step_definition ;
drop table IF EXISTS urm_office_workflow_mapping ;
drop table IF EXISTS urm_office ;
drop table IF EXISTS urm_thematic ;

alter table agent drop column IF EXISTS urm;
