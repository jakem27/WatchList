drop database if exists watchlist_test;
create database watchlist_test;
use watchlist_test;

create table user( 
	id int primary key auto_increment,
	username text,
	password text,
	favorite_movie text null,
	favorite_actor text null
);

create table folder(
	id int primary key auto_increment,
	name text,
	is_public bit,
	user_id int,
	parent_id int null,
	constraint fk_folder_user_id
		foreign key (user_id)
		references user(id),
	constraint fk_folder_parent_id
		foreign key (parent_id)
		references folder(id)
);



delimiter //
create procedure set_known_good_state()
begin
	delete from folder;
	delete from user;
	alter table user auto_increment = 1;
	alter table folder auto_increment = 1;

	insert into user(id, username, password) values
	(1, "user1", "password"),
	(2, "user2", "123");
	
	insert into folder(id, name, is_public, user_id, parent_id) values
	(1, "f1", 0, 1, NULL),
	(2, "f1-1", 0, 1, 1);
	
end //

delimiter ;