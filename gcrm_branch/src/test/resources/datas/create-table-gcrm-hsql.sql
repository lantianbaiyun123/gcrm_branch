drop table  g_industry  if exists;
create table g_industry
(
   id   int GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
   name varchar(128),
   primary key (id)
);