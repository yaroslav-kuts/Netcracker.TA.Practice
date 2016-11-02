create database TaskManager;

use TaskManager;

create table lists (
list_id int not null auto_increment primary key,
type varchar(255),
size int
);

create table tasks (
task_id int not null auto_increment primary key,
title varchar(255),
time int,
start int,
end int,
rep int,
isActive boolean,
prev int,
list_id int,
foreign key (prev) references tasks (task_id),
foreign key (list_id) references lists (list_id)
);

insert into lists values (0, "non list", 0);


