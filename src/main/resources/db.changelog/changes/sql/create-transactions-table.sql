create table if not exists transactions
(
    id bigserial primary key not null,
    date date not null,
    coach_id bigserial not null
        references coach(person_id),
    money int not null,
    person_id bigserial not null
        references person(id)
);