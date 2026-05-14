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

create table movie( 
	id int primary key auto_increment,
	title text,
	year int,
	runtime int,
	director text,
	genre text
);

create table movie_folder(
	movie_id int,
	folder_id int,
	watched bit,
	constraint pk_movie_folder
		primary key (movie_id, folder_id),
	constraint fk_movie_folder_movie_id
		foreign key (movie_id)
		references movie(id),
	constraint fk_movie_folder_folder_id
		foreign key (folder_id)
		references folder(id)
);

delimiter //
create procedure set_known_good_state()
begin
	SET FOREIGN_KEY_CHECKS = 0;

	delete from movie_folder;
	delete from folder;
	alter table folder auto_increment = 1;
	delete from user;
	alter table user auto_increment = 1;
	delete from movie;
	alter table movie auto_increment = 1;
	
    SET FOREIGN_KEY_CHECKS = 1;

	insert into user(id, username, password) values
	(1, "user1", "password"),
	(2, "user2", "123");
	
	insert into movie(id, title, year, runtime, director, genre) values
	(1, "movie1", 2001, 90, "director1", "action"),
	(2, "movie2", 2018, 115, "director2", "comedy");
	
	insert into folder(id, name, is_public, user_id, parent_id) values
	(1, "root", 0, 1, NULL),
	(2, "f1", 0, 1, 1),
	(3, "f2", 0, 1, 1),
	(4, "other", 0, 2, NULL);
	
	insert into movie_folder(movie_id, folder_id, watched) values
	(1, 1, 0),
	(2, 1, 0);
	
end //

delimiter ;