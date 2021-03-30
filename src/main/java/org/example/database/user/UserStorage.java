package org.example.database.user;

import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserStorage extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
    void add(User user);
    List<User> findByNameContaining(String name);
    void deleteById(long id);
    void changeRole(long id, String role);

}
