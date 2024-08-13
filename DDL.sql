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


create table transactions
(
    bill_id bigint not null auto_increment,
    receipt_number bigint not null,
    trans_date datetime default current_timestamp ,
    transaction_type enum ('EAT_IN', 'TAKE_AWAY', 'ONLINE' ) not null ,
    product_id varchar(100) not null,
    branch_id varchar(100) not null,
    quantity bigint not null,
    total_sales bigint not null ,
    primary key(bill_id),
    foreign key fk_product_id(product_id) references products(product_id),
    foreign key fk_branch_id(branch_id) references branch(branch_id)
) ENGINE InnoDB;
drop table transactions;
show tables;
desc transactions;