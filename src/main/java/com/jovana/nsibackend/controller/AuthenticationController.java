package com.jovana.nsibackend.controller;

import com.google.common.collect.Sets;
import com.jovana.nsibackend.exception.NsiAppException;
import com.jovana.nsibackend.model.Role;
import com.jovana.nsibackend.model.RoleName;
import com.jovana.nsibackend.model.User;
import com.jovana.nsibackend.repository.RoleRepository;
import com.jovana.nsibackend.repository.UserRepository;
import com.jovana.nsibackend.resource.ApiResponse;
import com.jovana.nsibackend.resource.JwtAuthResponse;
import com.jovana.nsibackend.resource.LoginRequest;
import com.jovana.nsibackend.resource.RegisterRequest;
import com.jovana.nsibackend.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by jovana on 05.11.2018
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenHelper tokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenHelper.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email address is already in use!"), HttpStatus.BAD_REQUEST);
        }

        Role r = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new NsiAppException("User role not found!"));
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getName(), request.getUsername(), request.getEmail(), encodedPassword, Sets.newHashSet(r));
        User u = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(u.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully!"));
    }

}
