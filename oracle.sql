drop table params;
drop table attributes;
drop table objects;
drop table object_types;
drop table refs;

Create table object_types (
	object_type_id Number(20,0) NOT NULL,
	name Varchar2(100),
	description Varchar2(1000));

Create table objects (
	object_id Number(20,0) NOT NULL,
	parent_id NUMBER(20,0),
	object_type_id Number(20,0) NOT NULL,
    name Varchar2(100));
	
Create table attributes (
	attr_id Number(20,0) NOT NULL,
	object_type_id Number(20,0) NOT NULL , 
	name Varchar2(100)); 
	
Create table params (
	param_id Number(20,0) NOT NULL,
	object_id Number(20,0) NOT NULL ,
	attr_id Number(20,0) NOT NULL ,
	text_value Varchar2(1000),
	number_value Number(20,0),
	date_value Date);
	
Create table refs (
	ref_id Number(20,0) NOT NULL,
	object_id Number(20,0) NOT NULL,
	attr_id Number(20,0) NOT NULL);

Alter table object_types add primary key (object_type_id);
Alter table objects add primary key (object_id);
Alter table attributes add primary key (attr_id);
Alter table params add primary key (param_id);
Alter table refs add primary key (ref_id);

Alter table objects 
add foreign key (object_type_id) 
references object_types (object_type_id) on delete cascade;

Alter table attributes 
add foreign key (object_type_id) 
references object_types (object_type_id);

Alter table params 
add foreign key (object_id) 
references objects (object_id);

Alter table params 
add foreign key (attr_id) 
references attributes (attr_id);

Alter table refs
add foreign key (object_id)
references objects (object_id);

Alter table refs
add foreign key (attr_id)
references attributes (attr_id);

insert into object_types values (1, 'Task', 'Repeatable or nonrepeatable task');
insert into object_types values (2, 'ArrayTaskList', 'List of tasks');
insert into object_types values (3, 'LinkedTaskList', 'List of tasks');

insert into attributes values(1, 1, 'title');
insert into attributes values(2, 1, 'time');
insert into attributes values(3, 1, 'start');
insert into attributes values(4, 1, 'end');
insert into attributes values(5, 1, 'repeat');
insert into attributes values(6, 1, 'active');

insert into attributes values(7, 2, 'size');

insert into attributes values(8, 3, 'size');


