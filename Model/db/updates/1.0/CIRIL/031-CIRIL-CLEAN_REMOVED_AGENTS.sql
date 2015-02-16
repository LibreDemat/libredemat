delete from agent_category_roles where agent_id not in (select id from agent) ;
