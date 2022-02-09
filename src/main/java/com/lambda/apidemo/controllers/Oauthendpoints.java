package com.lambda.apidemo.controllers;

import com.lambda.apidemo.models.User;
import com.lambda.apidemo.models.UserMinimum;
import com.lambda.apidemo.models.UserRoles;
import com.lambda.apidemo.repositories.RoleRepository;
import com.lambda.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class Oauthendpoints {
    @Autowired
    private RoleRepository rolerepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/createnewuser", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addSelf(HttpServletRequest httpServletRequest,
                                     @Valid
                                     @RequestBody UserMinimum newminuser) throws URISyntaxException {
        if (newminuser.getUsername().isBlank() || newminuser.getPassword().isBlank()) {
            throw new EntityNotFoundException("Username and / or password cannot be blank");
        }

        if (userrepos.findByUsername(newminuser.getUsername()) != null) {
            throw new EntityExistsException("Username already exists");
        }

        User newuser = new User();
        Set<UserRoles> roles = new HashSet();
        roles.add(new UserRoles(newuser, rolerepos.findByName("USER")));

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setRoles(roles);
        newuser = userrepos.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        // return the access token
        // To get the access token, surf to the endpoint /login (which is always on the server where this is running)
        // just as if a client had done this.
        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type",
                "password");
        map.add("scope",
                "read write trust");
        map.add("username",
                newminuser.getUsername());
        map.add("password",
                newminuser.getPassword());

        var request = new HttpEntity<MultiValueMap<String, String>>(map,
                headers);

        String theToken = restTemplate.postForObject(requestURI,
                request,
                String.class);

        return new ResponseEntity<>(theToken,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logoutSelf(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            // find the token
            String tokenValue = authHeader.replace("Bearer", "").trim();
            // and remove it!
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
