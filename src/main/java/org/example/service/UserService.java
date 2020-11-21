package org.example.service;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author dchernichkin 15.11.2020
 */

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean saveUser(User newUser) {
        User user = userRepository.findByName(newUser.getUsername());
        if (user != null) {
            return false;
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        List<Role> roleList = roleRepository.findAll();
        for (Role role:roleList) {
            if(role.getName().equals("ROLE_USER")) {
                newUser.setRoleList(Arrays.asList(role));
                userRepository.save(newUser);
                return true;
            }
        }
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        newUser.setRoleList(Arrays.asList(role));
        userRepository.save(newUser);
        return true;
    }

    public List<User> findByNameContaining(String name) {
        return userRepository.findByNameContaining(name);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
