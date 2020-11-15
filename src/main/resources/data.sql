drop database Satellite_Imagery;

create database Satellite_Imagery;

use Satellite_Imagery;

create table user (
	id bigint primary key auto_increment,
	login varchar(255) not null unique,
	password varchar(255) not null
);

create table role (
	id bigint primary key auto_increment,
	name varchar(255) not null unique
);

create table user_role (
	user_id bigint,
	role_id bigint,
	foreign key (user_id) references user(id),
	foreign key (role_id) references role(id)
);

create table country (
	id bigint primary key auto_increment,
	description varchar(255) not null,
	name varchar(255) not null
);

create table region (
	id bigint primary key auto_increment,
	description varchar(255) not null,
	name varchar(255) not null,
	country_id bigint,
	foreign key (country_id) references country(id)
);

create table city (
	id bigint primary key auto_increment,
	description varchar(255) not null,
	name varchar(255) not null,
	region_id bigint,
	foreign key (region_id) references region(id)
);

create table attraction (
	id bigint primary key auto_increment,
	description varchar(255) not null,
	name varchar(255) not null,
	city_id bigint,
	foreign key (city_id) references city(id)
);

create table image (
	location_type varchar(255),
	location_id bigint,
	data longblob not null,
	unique (location_id, location_type)
);
