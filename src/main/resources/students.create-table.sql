CREATE TABLE management_system.Students(
   email varchar(50) NOT NULL UNIQUE,
   name varchar(50) NOT NULL,
   password varchar(50) NOT NULL,
   PRIMARY KEY(email)
   );