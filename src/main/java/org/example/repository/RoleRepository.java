package org.example.repository;

import org.example.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(
            nativeQuery = true,
            value = "select * from roles"
    )
    List<Role> findAll();

}
