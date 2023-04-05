CREATE TABLE drink
(
    id     uuid    not null,
    title  varchar not null,
    price  INTEGER not null,
    amount INTEGER not null,
    constraint pk_drink primary key (id)
);