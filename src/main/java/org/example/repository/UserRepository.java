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
            value = "call user_get_by_name(?1)"
    )
    User findByName(String name);

    @Query(
            nativeQuery = true,
            value = "call user_get_by_id(?1)"
    )
    User findById(long id);

    @Query(
            nativeQuery = true,
            value = "call user_get_list_by_name_containing(?1)"
    )
    List<User> findByNameContaining(String name);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "call user_delete(?1)"
    )
    void deleteById(long id);
}
