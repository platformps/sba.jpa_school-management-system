create table management_system.Student (
email varchar(50) not null /*Student's current school email, unique student identifier*/,
name varchar(50) not null /*The full name of the student*/,
password varchar(50) not null /*Student's password used to log in*/,
primary key (email)
);