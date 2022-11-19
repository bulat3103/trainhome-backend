create table if not exists transactions
(
    id bigserial primary key not null,
    date timestamp not null,
    coach_id bigserial not null
        references coach(person_id),
    money int not null
);