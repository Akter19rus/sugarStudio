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

--changeset vlad:2

CREATE TABLE images
(
    id         BIGSERIAL PRIMARY KEY,
    file_path  VARCHAR(255),
    file_size  BIGINT,
    media_type VARCHAR(255)
);

--changeset vlad:3
ALTER TABLE images
    ADD COLUMN name VARCHAR(255)

--changeset vlad:4
ALTER TABLE images
    ADD COLUMN data BYTEA

--changeset vlad:5
ALTER TABLE images
    ADD COLUMN file_id VARCHAR(255)