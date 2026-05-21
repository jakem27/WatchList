drop database if exists watchlist;
create database watchlist;
use watchlist;

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
		on delete cascade
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
		on delete cascade
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

create table user_service(
	user_id int,
	streaming_service varchar(20),
	constraint pk_user_service
		primary key (user_id, streaming_service),
	constraint fk_user_service_user
		foreign key (user_id)
		references user(id)
);

create index idx_folder_parent_id on folder(parent_id);

select * from folder;
select * from user;
select * from movie_folder;
select * from movie;
select * from friendship;
