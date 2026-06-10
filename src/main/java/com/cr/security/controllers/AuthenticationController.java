package com.cr.security.controllers;

import com.cr.security.dto.AuthenticationDTO;
import com.cr.security.dto.RegisterDTO;
import com.cr.security.models.User;
import com.cr.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var token = new UsernamePasswordAuthenticationToken(
                        data.login(),
                        data.password());

        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if(userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String password = passwordEncoder.encode(data.password());
        User user = new User(data.login(), password, data.role());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
