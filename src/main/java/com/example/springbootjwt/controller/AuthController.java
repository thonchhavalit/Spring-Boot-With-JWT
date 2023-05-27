package com.example.springbootjwt.controller;


import com.example.springbootjwt.model.request.LoginRequest;
import com.example.springbootjwt.model.response.LoginResponse;
import com.example.springbootjwt.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationProvider authenticationProvider;
    public AuthController(TokenService tokenService, AuthenticationProvider authenticationProvider){
        this.tokenService = tokenService;
        this.authenticationProvider=authenticationProvider;
    }
    @PostMapping("/user-login")
    public LoginResponse login (@RequestBody LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request .getPasscode(),
                request.getUsername()
        );
        authentication = authenticationProvider.authenticate(authentication);

        LoginResponse response = new LoginResponse();
        String token = tokenService.generateToken(authentication);
        response.setToken(token);
        response.setTokenType("Bearer");
        return response;

    }
}




//    @PostMapping("/token")
//    public String getToken(Authentication authentication){
//        String token = tokenService.generateToken(authentication);
//        return token;
//    }
//
//}
