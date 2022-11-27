create table if not exists training
(
    id bigserial primary key not null,
    training_date timestamp not null
        check (training_date >= now()),
    coach_id bigserial not null
        references coach(person_id),
    link varchar(255) not null,
    group_id bigserial not null
        references groups(id),
    unique (training_date, coach_id)
);

create index on training using hash (coach_id);
create index on training using hash (group_id);