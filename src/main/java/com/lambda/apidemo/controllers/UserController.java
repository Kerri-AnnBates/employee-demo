package com.lambda.apidemo.controllers;

import com.lambda.apidemo.models.User;
import com.lambda.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userrepos;

    @GetMapping(value = "/getuserinfo", produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        User u = userrepos.findByUsername(authentication.getName());

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping(value = "/getusername", produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserName(Authentication authentication) {
        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }
}