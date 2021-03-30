delimiter ;

drop database Satellite_Imagery;

create database Satellite_Imagery;

use Satellite_Imagery;

create table location (
    id bigint primary key auto_increment,
    description text not null,
    name varchar(255) not null,
    unique(name)
);

create table image (
    location_id bigint,
    data longblob not null,
    foreign key (location_id) references location(id)
);

create table country (
    id bigint primary key auto_increment,
    location_id bigint,
    foreign key (location_id) references location(id)
);

create table region (
    id bigint primary key auto_increment,
    location_id bigint,
    country_id bigint,
    foreign key (location_id) references location(id),
    foreign key (country_id) references country(id)
);

create table city (
    id bigint primary key auto_increment,
    location_id bigint,
    region_id bigint,
    foreign key (location_id) references location(id),
    foreign key (region_id) references region(id)
);

create table attraction (
    id bigint primary key auto_increment,
    location_id bigint,
    city_id bigint,
    foreign key (location_id) references location(id),
    foreign key (city_id) references city(id)
);


create table user (
    id bigint primary key auto_increment,
    name varchar(255) not null,
    password varchar(255) not null
);

create table role (
    id bigint primary key auto_increment,
    name varchar(255) not null
);

create table user_role (
    user_id bigint,
    role_id bigint,
    foreign key (user_id) references user(id),
    foreign key (role_id) references role(id)
);

insert into user values (1, 'admin', '$2y$12$F65eQA5xBawRa2ZQf8L././PkxwS63lCBlqyeK1XnSRjHrbwOEK2C'); /* pass: admin */
insert into role values (1, 'ROLE_ADMIN');
insert into user_role values (1, 1);

insert into user values (2, 'moder', '$2y$12$30vbXCzD9axr9lK5QtseL.X0vaCuRLA91HXQ7ENscTRIV7ndYOOfC'); /* pass: moder */
insert into role values (2, 'ROLE_MODERATOR');
insert into user_role values (2, 2);

insert into role values(3, 'ROLE_USER');
