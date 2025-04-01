--liquibase formatted sql

--changeset vlad:1
CREATE TABLE users_contact_info
(
    id       bigserial PRIMARY KEY,
    chat_id  bigint unique,
    nickname varchar(255),
    name     varchar(255),
    surname  varchar(255),
    is_admin boolean default false
);
create index users_contact_info_chat_id on users_contact_info (chat_id);

--changeset vlad:2
CREATE TABLE images
(
    id         bigserial PRIMARY KEY,
    name       varchar(255),
    file_path  varchar(255),
    file_size  BIGINT,
    media_type varchar(255),
    file_id    varchar(255)
);

--changeset vlad:3
CREATE TABLE user_review
(
    id          bigserial PRIMARY KEY,
    text        TEXT,
    user_chat_id bigint references "users_contact_info" (chat_id)
);
create index user_review_user_chat_id on user_review (user_chat_id);