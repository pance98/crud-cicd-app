package com.example.demo.service.impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InvalidUserNameException;
import com.example.demo.repostitory.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User create(String username, String password, Role role)
    {
        String encryptedPasword = this.passwordEncoder.encode(password);
        User user = new User(username, encryptedPasword, role);

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(InvalidUserNameException::new);
        UserDetails userDetails =
                new org.springframework.security.core
                        .userdetails
                        .User(user.getUsername(),
                        user.getPassword(),
                        Stream.of(new SimpleGrantedAuthority(user.getRole().toString()))
                                .collect(Collectors.toList()));
        return userDetails;
    }
}
