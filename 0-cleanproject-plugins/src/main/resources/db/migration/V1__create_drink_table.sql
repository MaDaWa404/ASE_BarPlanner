CREATE TABLE person
(
    id            uuid    not null,
    username      varchar not null,
    password_hash varchar not null,
    lastname      varchar not null,
    firstname     varchar not null,
    constraint pk_user primary key (id)
);
CREATE TABLE bar
(
    id            uuid    not null,
    title         varchar not null,
    administrator uuid    not null,
    zip           INTEGER not null,
    city          varchar not null,
    street        varchar not null,
    number        INTEGER not null,
    constraint pk_bar primary key (id),
    constraint fk_user foreign key (administrator) references person
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
CREATE TABLE purchase
(
    id     uuid not null,
    person uuid not null,
    drink  uuid not null,
    constraint pk_purchase primary key (id),
    constraint fk_person foreign key (person) references person,
    constraint fk_drink foreign key (drink) references drink

)