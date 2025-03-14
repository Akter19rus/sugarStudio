--liquibase formatted sql

--changeset vlad:1

CREATE TABLE users_contact_info
(
    id       bigserial PRIMARY KEY,
    chat_id  bigint,
    nickname varchar(255),
    name     varchar(255),
    surname  varchar(255)
);
