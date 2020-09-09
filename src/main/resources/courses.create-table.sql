create table management_system.Course (
id int not null /*Unique course identifier*/,
name varchar(50) not null /*Provides the name of the course*/,
instructor varchar(50) not null /*Provides the name of the instructor*/,
primary key (id)
);