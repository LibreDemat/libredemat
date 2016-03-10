create table information_request (
  id int8 not null,
  message varchar(255),
  primary key (id)
);

create table information_request_motive (
  information_request_id int8 not null,
  motive_id int8 not null,
  motive_index int4 not null,
  primary key (information_request_id, motive_index)
);
