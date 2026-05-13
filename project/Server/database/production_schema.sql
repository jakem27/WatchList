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
	runtime text,
	director text,
	genre text
);
