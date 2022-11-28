create table if not exists eat_calendar
(
    id bigserial primary key not null,
    info varchar(255),
    date timestamp not null
        check (date >= now()),
    person_id bigserial not null
        references person(id),
    coach_id bigserial not null
        references coach(person_id),
    unique (date, coach_id)
);

create index on eat_calendar using hash (person_id);
create index on eat_calendar using hash (coach_id);