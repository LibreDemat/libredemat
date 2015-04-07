insert into agent (id, active, email, first_name, last_name, login, preferences, validation_code, validation_code_expiration, password) 
    values (nextval('hibernate_sequence'), true, 'support-capdemat@zenexity.com', 'agent', 'zengularity', 'agent.zengularity', null, null, null, '$2a$10$mTpQVRoYYrMR.UeavyKXHeEitzbB347ScBu5JEhiuiIu5n5r/poru');
insert into agent_site_roles (agent_id, profile)
    values ((select id from agent where login = 'agent.zengularity'), 'AGENT');
insert into user_security_rule (id, agent_id, profile)
    values (nextval('hibernate_sequence'), (select id from agent where login = 'agent.zengularity'), 'MANAGE');
insert into agent_category_roles select id, (select id from agent where login = 'agent.zengularity'), 'MANAGER' from category;

insert into agent (id, active, email, first_name, last_name, login, preferences, validation_code, validation_code_expiration, password) 
    values (nextval('hibernate_sequence'), true, 'support-capdemat@zenexity.com', 'admin', 'zengularity', 'admin.zengularity', null, null, null, '$2a$10$OPsShQpOVhYRe7huUan9JuV9DmO1vM20y8rsZYIAqtcfBsZGul/QS');
insert into agent_site_roles (agent_id, profile)
    values ((select id from agent where login = 'admin.zengularity'), 'ADMIN');
