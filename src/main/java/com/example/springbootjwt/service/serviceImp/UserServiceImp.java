package com.example.springbootjwt.service.serviceImp;

import com.example.springbootjwt.model.User;
import com.example.springbootjwt.repository.UserRepository;
import com.example.springbootjwt.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
        private final UserRepository userRepository;
        public UserServiceImp(UserRepository userRepository){
            this.userRepository = userRepository;
        }


    @Override
    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
}
