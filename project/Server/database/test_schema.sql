drop database if exists watchlist_test;
create database watchlist_test;
use watchlist_test;

create table user( 
	id int primary key auto_increment,
	username text,
	password text,
	favorite_movie text null,
	favorite_actor text null,
	favorite_genre text null,
	admin_status text
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
	genre text,
	description text,
	poster_url text
);

create table movie_folder(
	movie_id int,
	folder_id int,
	watched bit,
	liked bit null,
	constraint pk_movie_folder
		primary key (movie_id, folder_id),
	constraint fk_movie_folder_movie_id
		foreign key (movie_id)
		references movie(id),
	constraint fk_movie_folder_folder_id
		foreign key (folder_id)
		references folder(id)
);

create table friendship( 
	user1_id int,
	user2_id int,
	pending bit,
	constraint pk_friendship
		primary key (user1_id, user2_id),
	constraint fk_friendship_user1
		foreign key (user1_id)
		references user(id),
	constraint fk_friendship_user2
		foreign key (user2_id)
		references user(id)
);

delimiter //
create procedure set_known_good_state()
begin
	SET FOREIGN_KEY_CHECKS = 0;

	delete from movie_folder;
	delete from friendship;
	delete from folder;
	alter table folder auto_increment = 1;
	delete from user;
	alter table user auto_increment = 1;
	delete from movie;
	alter table movie auto_increment = 1;
	
    SET FOREIGN_KEY_CHECKS = 1;

	insert into user(id, username, password, admin_status) values
	(1, "user1", "password", "NOT_ADMIN"),
	(2, "user2", "123", "NOT_ADMIN"),
	(3, "user3", "asdf", "NOT_ADMIN");
	
	insert into movie(id, title, year, runtime, director, genre, description, poster_url ) values
	(1, "movie1", 2001, 90, "director1", "action", "action movie", ""),
	(2, "movie2", 2002, 95, "director2", "comedy", "comedy movie", ""),
	(3, "movie3", 2003, 100, "director3", "romance", "romance movie", ""),
	(4, "movie4", 2004, 105, "director4", "sci-fi", "sci-fi movie", ""),
	(5, "movie5", 2005, 110, "director5", "historical", "historical movie", ""),
	(6, "movie6", 2006, 115, "director6", "superhero", "superhero movie", "");
	
	insert into folder(id, name, is_public, user_id, parent_id) values
	(1, "My WatchList", 0, 1, NULL),
	(2, "f1", 0, 1, 1),
	(3, "f2", 0, 1, 1),
	(4, "other", 1, 2, NULL),
	(5, "empty", 0, 2, NULL);
	
	insert into movie_folder(movie_id, folder_id, watched, liked) values
	(1, 1, 1, NULL),
	(2, 1, 1, NULL),
	(3, 2, 0, NULL),
	(4, 4, 0, NULL),
	(5, 3, 0, NULL),
	(6, 3, 0, NULL);
	
	insert into friendship(user1_id, user2_id, pending) values
	(1, 2, 1),
	(2, 3, 0);
	
end //

delimiter ;