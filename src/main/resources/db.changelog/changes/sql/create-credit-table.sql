create table if not exists credit
(
    id bigserial primary key not null,
    payed_months int not null,
    common_count_months int not null,
    next_pay date not null,
    money int not null,
    person_id bigserial not null
        references person(id),
    coach_id bigserial not null
        references coach(id)
);