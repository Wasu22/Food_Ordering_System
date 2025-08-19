package com.cdac.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.custom_exception.ApiException;
import com.cdac.dao.UserDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.AuthResp;
import com.cdac.dto.SignInDTO;
import com.cdac.dto.SignupReqDTO;
import com.cdac.entities.User;
import com.cdac.security.JwtUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserSignInSignUpController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
  
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupReqDTO dto) {
        if (userDao.existsByEmail(dto.getEmail()))
            throw new ApiException("Email already registered");

        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userDao.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User registration successful"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody @Valid SignInDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        String token = jwtUtils.generateJwtToken(auth);
        User user = userDao.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 4: Return message, token, and customerId
            return ResponseEntity.ok(new AuthResp("Login successful", token, user.getId()));

       // return ResponseEntity.ok(new AuthResp("Login successful", token));
    }
    
  
}

