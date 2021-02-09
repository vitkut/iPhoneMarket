create table basket (user_id int not null, product_id int not null, primary key (user_id, product_id));

create table roles (id int not null auto_increment, name varchar(255) not null, primary key (id));

create table hibernate_sequence (next_val bigint);

insert into hibernate_sequence values (1);

create table products (id int not null auto_increment, charact varchar(255) not null, name varchar(255) not null, price float not null, primary key (id));

create table user_roles (user_id int not null, role_id int not null, primary key (user_id, role_id));

create table users (id int not null auto_increment, balance float not null, name varchar(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id));
