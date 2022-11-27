create table if not exists session
(
    id bigserial primary key not null,
    person_id bigserial not null
        references person(id),
    token varchar(255) not null,
    created_at date not null,
    expired boolean not null
);

create index on session using hash (person_id);
create index on session using hash (token);