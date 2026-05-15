drop database if exists watchlist;
create database watchlist;
use watchlist;

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

create index idx_folder_parent_id on folder(parent_id);

select * from folder;
select * from user;
select * from movie_folder;
select * from movie;
