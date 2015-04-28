insert into agent (id, active, email, first_name, last_name, login, preferences, validation_code, validation_code_expiration, password) 
    values (nextval('hibernate_sequence'), true, 'garmetta@ciril.net', 'agent', 'ciril', 'agent.ciril', null, null, null, '$2a$10$wycUshix8AyKVJXTMxZtG.ViwWW6pz0SxWmn6xxjiCmxixjSE9XbG');

insert into agent_site_roles (agent_id, profile)
    values ((select id from agent where login = 'agent.ciril'), 'AGENT');

insert into user_security_rule (id, agent_id, profile)
    values (nextval('hibernate_sequence'), (select id from agent where login = 'agent.ciril'), 'MANAGE');

insert into agent_category_roles (category_id, agent_id, profile) select id, (select id from agent where login = 'agent.ciril'), 'MANAGER' from category;
