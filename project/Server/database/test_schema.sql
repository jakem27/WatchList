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



delimiter //
create procedure set_known_good_state()
begin
	delete from user;
	alter table user auto_increment = 1;

	insert into user(id, username, password) values
	(1, "user1", "password"),
	(2, "user2", "123");
	
end //

delimiter ;