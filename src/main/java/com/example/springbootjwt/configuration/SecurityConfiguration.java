package com.example.springbootjwt.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user = User.builder()
//                .username("valit")
//                .password("{noop}12345")
//                .authorities("read","write").build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    private final UserDetailsService userDetailsService;
    public SecurityConfiguration(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
   return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/home", "/user-login").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    //1. keypair
    @Bean
    public KeyPair keyPair(){
        try {
            var keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
            return keyGenerator.generateKeyPair();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    //2. RSA key
    //3. Encoder
    @Bean
    public JwtEncoder jwtEncoder(){
        //create instance of jwk
        JWK jwk = new RSAKey.Builder( (RSAPublicKey) keyPair().getPublic()).privateKey(keyPair().getPrivate()).build();
        //provide that instance to the jwkSource
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    //4. Decoder
    @Bean
    public JwtDecoder jwtDecoder() throws Exception{
        return  NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair().getPublic()).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration)
        throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
