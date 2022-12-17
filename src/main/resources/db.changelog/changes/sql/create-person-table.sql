create table if not exists person
(
    id bigserial primary key not null,
    password varchar(255) not null,
    name varchar(255) not null,
    image_id bigserial
        references image(id),
    phone_number varchar(255) not null unique,
    email varchar(255) not null unique,
    birthday timestamp not null,
    sex boolean not null,
    role_id bigserial not null
        references role(id)
);