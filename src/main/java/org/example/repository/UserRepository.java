package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dchernichkin 15.11.2020
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

}
