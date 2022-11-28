create table if not exists coach
(
    person_id bigserial primary key not null
        references person(id),
    rating double precision not null default 0,
    achievements varchar(255) default 'Пока нет достижений',
    info varchar(255) default 'Пока нет информации',
    money int not null
        check (money >= 0) default 0
);