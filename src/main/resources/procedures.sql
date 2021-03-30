/* create storing procedures: */

delimiter ;

drop procedure image_get;
drop procedure image_add;
drop procedure image_delete;
drop procedure image_update;
drop procedure location_add;
drop procedure location_update;
drop procedure location_delete;
drop procedure attraction_get_by_id;
drop procedure attraction_get_by_name;
drop procedure attraction_get_list;
drop procedure attraction_add;
drop procedure attraction_update;
drop procedure attraction_delete;
drop procedure attraction_delete_by_parent_id;
drop procedure city_get_by_id;
drop procedure city_get_by_name;
drop procedure city_get_list;
drop procedure city_add;
drop procedure city_update;
drop procedure city_delete;
drop procedure city_delete_by_parent_id;
drop procedure region_get_by_id;
drop procedure region_get_by_name;
drop procedure region_get_list;
drop procedure region_add;
drop procedure region_update;
drop procedure region_delete;
drop procedure region_delete_by_parent_id;
drop procedure country_get_by_id;
drop procedure country_get_by_name;
drop procedure country_get_list;
drop procedure country_add;
drop procedure country_update;
drop procedure country_delete;
drop procedure user_get_by_name;
drop procedure user_add;
drop procedure user_get_list_by_name_containing;
drop procedure user_delete;
drop procedure role_update_user_role;

delimiter $$

create procedure image_get(in i_location_id bigint)
select * from image where location_id = i_location_id;
$$

create procedure image_add(in i_location_id bigint, in i_data longblob)
insert into image (location_id, data) values (i_location_id, i_data);
$$

create procedure image_delete(in i_location_id bigint)
delete from image where location_id = i_location_id;
$$

create procedure image_update(in i_location_id bigint, in i_data longblob)
begin
    call image_delete(i_location_id);
    call image_add(i_location_id, i_data);
end $$

create procedure location_add(in i_name varchar(255), in i_description text, in i_data longblob, out inserted_id bigint)
begin
    insert into location (name, description) values (i_name, i_description);
    set inserted_id = last_insert_id();
    call image_add(inserted_id, i_data);
end $$

create procedure location_update(in i_name varchar(255), in i_description text, in i_data longblob, in i_id bigint)
begin
    update location set name = i_name, description = i_description where id = i_id;
    call image_update(i_id, i_data);
end $$

create procedure location_delete(in i_id bigint)
begin
    call image_delete(i_id);
    delete from location where id = i_id;
end $$


create procedure attraction_get_by_id(in i_id bigint)
select attraction.id, location_id, city_id, name, description
from attraction, location
where attraction.id = i_id and attraction.location_id = location.id;
$$

create procedure attraction_get_by_name(in i_name varchar(255))
select attraction.id, location_id, city_id, name, description
from attraction, location
where name = i_name and attraction.location_id = location.id;
$$

create procedure attraction_get_list(in i_city_id bigint)
select attraction.id, location_id, city_id, name, description
from attraction, location
where city_id = i_city_id and attraction.location_id = location.id;
$$

create procedure attraction_add(in i_name varchar(255), in i_description text, in i_data longblob, in i_city_id bigint)
begin
    start transaction;
    call location_add(i_name, i_description, i_data, @location_id);
    insert into attraction (location_id, city_id) values (@location_id, i_city_id);
    commit;
end $$

create procedure attraction_update(in i_name varchar(255), in i_description text,
in i_data longblob, in i_location_id bigint, in i_city_id bigint, in i_id bigint)
begin
    start transaction;
    call location_update(i_name, i_description, i_data, i_location_id);
    update attraction set city_id = i_city_id where id = i_id;
    commit;
end $$

create procedure attraction_delete(in i_id bigint)
begin
    start transaction;
    select location_id into @location_id from attraction where id = i_id;
    delete from attraction where id = i_id;
    call location_delete(@location_id);
    commit;
end $$

create procedure attraction_delete_by_parent_id(in i_id bigint)
begin
    declare temp_id bigint;
    declare done int default 0;
    declare cur cursor for select id from attraction where city_id = i_id;
    declare continue handler for not found set done = 1;
    open cur;
    start transaction;
    read_loop: loop
        fetch cur into temp_id;
        if (done) then
            leave read_loop;
        end if;
        call attraction_delete(temp_id);
    end loop;
    commit;
end $$


create procedure city_get_by_id(in i_id bigint)
select city.id, location_id, region_id, name, description
from city, location
where city.id = i_id and city.location_id = location.id;
$$

create procedure city_get_by_name(in i_name varchar(255))
select city.id, location_id, region_id, name, description
from city, location
where name = i_name and city.location_id = location.id;
$$

create procedure city_get_list(in i_region_id bigint)
select city.id, location_id, region_id, name, description
from city, location
where region_id = i_region_id and city.location_id = location.id;
$$

create procedure city_add(in i_name varchar(255), in i_description text, in i_data longblob, in i_region_id bigint)
begin
    start transaction;
    call location_add(i_name, i_description, i_data, @location_id);
    insert into city (location_id, region_id) values (@location_id, i_region_id);
    commit;
end $$

create procedure city_update(in i_name varchar(255), in i_description text,
                                   in i_data longblob, in i_location_id bigint, in i_region_id bigint, in i_id bigint)
begin
    start transaction;
    call location_update(i_name, i_description, i_data, i_location_id);
    update city set region_id = i_region_id where id = i_id;
    commit;
end $$

create procedure city_delete(in i_id bigint)
begin
    start transaction;
    call attraction_delete_by_parent_id(i_id);
    select location_id into @location_id from city where id = i_id;
    delete from city where id = i_id;
    call location_delete(@location_id);
    commit;
end $$

create procedure city_delete_by_parent_id(in i_id bigint)
begin
    declare temp_id bigint;
    declare done int default 0;
    declare cur cursor for select id from city where region_id = i_id;
    declare continue handler for not found set done = 1;
    open cur;
    start transaction;
    read_loop: loop
        fetch cur into temp_id;
        if (done) then
            leave read_loop;
        end if;
        call city_delete(temp_id);
    end loop;
    commit;
end $$


create procedure region_get_by_id(in i_id bigint)
select region.id, location_id, country_id, name, description
from region, location
where region.id = i_id and region.location_id = location.id;
$$

create procedure region_get_by_name(in i_name varchar(255))
select region.id, location_id, country_id, name, description
from region, location
where name = i_name and region.location_id = location.id;
$$

create procedure region_get_list(in i_country_id bigint)
select region.id, location_id, country_id, name, description
from region, location
where country_id = i_country_id and region.location_id = location.id;
$$

create procedure region_add(in i_name varchar(255), in i_description text, in i_data longblob, in i_country_id bigint)
begin
    start transaction;
    call location_add(i_name, i_description, i_data, @location_id);
    insert into region (location_id, country_id) values (@location_id, i_country_id);
    commit;
end $$

create procedure region_update(in i_name varchar(255), in i_description text,
                             in i_data longblob, in i_location_id bigint, in i_country_id bigint, in i_id bigint)
begin
    start transaction;
    call location_update(i_name, i_description, i_data, i_location_id);
    update region set country_id = i_country_id where id = i_id;
    commit;
end $$

create procedure region_delete(in i_id bigint)
begin
    start transaction;
    call city_delete_by_parent_id(i_id);
    select location_id into @location_id from region where id = i_id;
    delete from region where id = i_id;
    call location_delete(@location_id);
    commit;
end $$

create procedure region_delete_by_parent_id(in i_id bigint)
begin
    declare temp_id bigint;
    declare done int default 0;
    declare cur cursor for select id from region where country_id = i_id;
    declare continue handler for not found set done = 1;
    open cur;
    start transaction;
    read_loop: loop
        fetch cur into temp_id;
        if (done) then
            leave read_loop;
        end if;
        call region_delete(temp_id);
    end loop;
    commit;
end $$


create procedure country_get_by_id(in i_id bigint)
select country.id, location_id, name, description
from country, location
where country.id = i_id and country.location_id = location.id;
$$

create procedure country_get_by_name(in i_name varchar(255))
select country.id, location_id, name, description
from country, location
where name = i_name and country.location_id = location.id;
$$

create procedure country_get_list()
select country.id, location_id, name, description
from country, location
where country.location_id = location.id;
$$

create procedure country_add(in i_name varchar(255), in i_description text, in i_data longblob)
begin
    start transaction;
    call location_add(i_name, i_description, i_data, @location_id);
    insert into country (location_id) values (@location_id);
    commit;
end $$

create procedure country_update(in i_name varchar(255), in i_description text,
                               in i_data longblob, in i_location_id bigint)
begin
    start transaction;
    call location_update(i_name, i_description, i_data, i_location_id);
    commit;
end $$

create procedure country_delete(in i_id bigint)
begin
    start transaction;
    call region_delete_by_parent_id(i_id);
    select location_id into @location_id from country where id = i_id;
    delete from country where id = i_id;
    call location_delete(@location_id);
    commit;
end $$


create procedure user_get_by_name(in i_name varchar(255))
select user.id as id, user.name as name, password, role.name as role from user, role
where user.name = i_name and role.id in (
    select role_id from user_role where user_id = user.id
);
$$

create procedure user_add(in i_name varchar(255), in i_password varchar(255), in i_role varchar(255))
begin
    start transaction;
    insert into user (name, password) values (i_name, i_password);
    set @user_id = last_insert_id();
    select id into @role_id from role where name = i_role;
    insert into user_role (user_id, role_id) values (@user_id, @role_id);
    commit;
end $$

create procedure user_get_list_by_name_containing(in i_name_fragment varchar(255))
select user.id as id, user.name as name, password, role.name as role from user, role
where user.name like concat('%', i_name_fragment, '%') and role.id in (
    select role_id from user_role where user_id = user.id
);
$$

create procedure user_delete(in i_id bigint)
begin
    start transaction;
    delete from user_role where user_id = i_id;
    delete from user where id = i_id;
    commit;
end $$

create procedure role_update_user_role(in i_user_id bigint, in i_role_name varchar(255))
update user_role set role_id = (select id from role where name = i_role_name) where user_id = i_user_id;
$$

delimiter ;
