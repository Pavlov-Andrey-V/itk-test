--liquibase formatted sql

--changeset Developer:1.0-create

create table wallet_balances (
    id uuid primary key,
    balance bigint
);

--changeset Developer:1.0-insert

insert into wallet_balances values ('13b44463-22a8-4569-b05b-6707894160e2', 1000000), ('bb78c379-a2a9-4983-86c0-b87e54e2cfd1', 100);