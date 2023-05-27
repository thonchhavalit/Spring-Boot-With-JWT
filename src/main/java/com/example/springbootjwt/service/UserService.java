package com.example.springbootjwt.service;

import com.example.springbootjwt.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findUserByName(String name);
}
