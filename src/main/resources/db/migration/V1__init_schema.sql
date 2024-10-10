create table users
(
    id       serial
        primary key,
    login    varchar(128) not null
        unique,
    password varchar(128) not null
);

create table locations
(
    id        serial
        primary key,
    name      varchar(128),
    user_id   integer
        references users,
    latitude  numeric,
    longitude numeric
);

create table sessions
(
    id         varchar(36) not null
        primary key,
    user_id    integer
        references users,
    expires_at timestamp
);