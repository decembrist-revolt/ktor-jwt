
create table usr (
    id bigserial primary key,
    username varchar(64),
    password varchar(64),
    role varchar(64)
);

insert into usr(username, password, role) values('user', 'user', 'ROLE_USER'), ('admin', 'admin', 'ROLE_ADMIN')
