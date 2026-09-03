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
    id           bigserial PRIMARY KEY,
    text         TEXT,
    user_chat_id bigint references "users_contact_info" (chat_id)
);
create index user_review_user_chat_id on user_review (user_chat_id);

--changeset vlad:4
CREATE TABLE price
(
    id           bigserial PRIMARY KEY,
    name_service VARCHAR(255),
    price        DECIMAL(19, 2)
);

--changeset vlad:5
CREATE TABLE record_visit
(
    id            bigserial PRIMARY KEY ,
    date_and_time TIMESTAMP,
    user_chat_id  BIGINT,
    CONSTRAINT fk_record_visit_user FOREIGN KEY (user_chat_id) REFERENCES "users_contact_info" (chat_id)
);

--changeset vlad:6
CREATE TABLE fixed_price
(
    id           bigserial PRIMARY KEY,
    service_name VARCHAR(255),
    fixed_price  DECIMAL(19, 2),
    record_visit BIGINT,
    CONSTRAINT fk_fixed_price_record_visit
        FOREIGN KEY (record_visit)
            REFERENCES record_visit (id)
);