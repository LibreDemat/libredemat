create table global_user_configuration (
    id int8 not null,
    instruction_alert_delay int4 not null DEFAULT 3,
    instruction_alerts_detailed bool not null DEFAULT false,
    instruction_alerts_enabled bool not null DEFAULT false,
    instruction_max_delay int4 not null DEFAULT 10,
    primary key (id)
);