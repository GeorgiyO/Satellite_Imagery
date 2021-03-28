package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dchernichkin 15.11.2020
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from user where name = ?1"
    )
    User findByName(String name);

    @Query(
            nativeQuery = true,
            value = "select * from user where id = ?1"
    )
    User findById(long id);

    @Query(
            nativeQuery = true,
            value = "select * from user where name like %?1%"
    )
    List<User> findByNameContaining(String name);

    @Query(
            nativeQuery = true,
            value = "delete from user where id = ?1"
    )
    void deleteById(long id);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into user (name, password) values (:#{#_user.name}, :#{#_user.password})"
    )
    void saveUser(@Param("_user")User user);

}
