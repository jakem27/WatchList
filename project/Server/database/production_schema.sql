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

