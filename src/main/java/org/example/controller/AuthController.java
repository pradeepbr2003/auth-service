package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthRequest;
import org.example.dto.Student;
import org.example.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private List<Student> students;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to auth-service application";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return students;
    }

    @PreAuthorize("hasAnyAuthority({'ROLE_ADMIN','ROLE_USER'})")
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        Optional<Student> optStudent = students.stream().filter(s -> (s.getId() == id)).findAny();
        if (optStudent.isPresent()) {
            return optStudent.get();
        }
        return null;
    }

    @PostMapping("/validate/{token}")
    public boolean validateToken(@RequestParam String token) {
        UserDetails userDetails = jwtService.fetchUserDetails(token);
        if (userDetails != null) {
            return jwtService.validateToken(token, userDetails);
        }
        return false;
    }

    @PostMapping("/authenticate")
    public String authenticateAndGenerateToken(@RequestBody AuthRequest authRequest) {
        log.info("Invoking authenticateAndGenerateToken method");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Invalid user request! %s \n", e.getLocalizedMessage()));
        }
        return jwtService.generateToken(authRequest.getUsername());
    }
}
