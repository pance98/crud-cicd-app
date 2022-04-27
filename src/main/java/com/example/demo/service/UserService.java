package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface UserService {


    User create(String username, String password, Role role);

}