show databases;

create database wms_api;

use wms_api;

create table branch
(
    branch_id varchar(100) not null primary key,
    branch_code varchar(100) not null,
    branch_name varchar(100) not null,
    address varchar(100) not null,
    phone_number varchar(100) not null
) Engine InnoDB;

create table products
(
    product_id varchar(100) not null primary key,
    product_price_id varchar(100) not null,
    product_code varchar(100) not null,
    product_name varchar(100) not null,
    price bigint not null ,
    branch_id varchar(100) not null,
    foreign key fk_branch_id(branch_id) references branch(branch_id)
) ENGINE InnoDB;
drop table products;