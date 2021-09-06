
create table message (
    id bigserial primary key,
    data varchar(1024)
);

insert into message(data) values ('jopa1'), ('jopa2'), ('jopa3');