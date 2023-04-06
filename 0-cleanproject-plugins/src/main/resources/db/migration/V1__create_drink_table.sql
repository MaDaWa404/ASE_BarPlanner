CREATE TABLE bar
(
    id            uuid    not null,
    title         varchar not null,
    administrator varchar not null,
    zip           INTEGER not null,
    city          varchar not null,
    street        varchar not null,
    number        INTEGER not null,
    constraint pk_bar primary key (id)
);
CREATE TABLE drink
(
    id     uuid    not null,
    title  varchar not null,
    price  INTEGER not null,
    amount INTEGER not null,
    bar    uuid    not null,
    constraint pk_drink primary key (id),
    constraint fk_bar foreign key (bar) references bar
);