package com.example.springbootjwt.service.serviceImp;

import com.example.springbootjwt.model.User;
import com.example.springbootjwt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User authenticationUser = userRepository.findUserByName(username).stream().findFirst().orElse(null);
        if (authenticationUser==null){
            System.out.println("Authentication USER : "+authenticationUser);
            throw new UsernameNotFoundException("AuthenticationUser doesn't exist");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(authenticationUser.getUsername())
                .password(authenticationUser.getPasscode())
                .authorities(authorities)
                .build();
        return userDetails;
    }
}
