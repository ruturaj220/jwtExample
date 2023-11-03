package com.ruturaj.jwtexample.controller;

import com.ruturaj.jwtexample.config.AuthenticationRequest;
import com.ruturaj.jwtexample.config.AuthenticationResponse;
import com.ruturaj.jwtexample.entity.User;
import com.ruturaj.jwtexample.security.JwtService;
import com.ruturaj.jwtexample.service.UserDetailsServiceImpl;
import com.ruturaj.jwtexample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    private final UserService userService;
    private final AuthenticationManager manager;

    public AuthenticationController(UserDetailsServiceImpl userDetailsService, JwtService jwtService, UserService userService, AuthenticationManager manager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.manager = manager;
    }

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtService.generateToken(userDetails);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
