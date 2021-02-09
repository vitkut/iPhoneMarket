insert into roles(name) values ('ADMIN');

insert into roles(name) values ('USER');

insert into users(balance, name, password, username) values (0, 'adminName', 'admin', 'admin');

insert into users(balance, name, password, username) values (0, 'user1Name', 'user1', 'user1');

insert into user_roles values (1, 1);

insert into user_roles values (1, 2);

insert into user_roles values (2, 2);