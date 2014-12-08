insert into user_security_rule (id, agent_id, profile) (
    select nextval('hibernate_sequence'), agent_id, 'WRITE' from agent_site_roles where profile = 'AGENT'
);
