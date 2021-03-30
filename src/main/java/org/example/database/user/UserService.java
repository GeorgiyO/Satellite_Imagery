package org.example.database.user;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author dchernichkin 15.11.2020
 */

public class UserService implements UserStorage {

    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserMapper mapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return jdbc.query(
                    "call user_get_by_name(?)",
                    mapper,
                    username
            ).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new UsernameNotFoundException("cant find username " + username);
        }
    }

    @Override
    public void add(User user) {

        // check if user exist
        try {
            loadUserByUsername(user.getName());
            throw new IllegalStateException("User already exists");
        } catch (UsernameNotFoundException ignored) {};

        user.setPassword(encoder.encode(user.getPassword()));
        jdbc.update(
                "call user_add(?, ?, ?)",
                user.getName(),
                user.getPassword(),
                "ROLE_USER"
        );
    }

    @Override
    public List<User> findByNameContaining(String nameFragment) {
        return jdbc.query(
                "call user_get_list_by_name_containing(?)",
                mapper,
                nameFragment
        );
    }

    @Override
    public void deleteById(long id) {
        jdbc.update(
                "call user_delete(?)",
                id
        );
    }

    @Override
    public void changeRole(long id, String role) {
        jdbc.update(
                "call role_update_user_role(?, ?)",
                id,
                role
        );
    }

}
