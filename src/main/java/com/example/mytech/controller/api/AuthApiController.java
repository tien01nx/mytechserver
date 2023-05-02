package com.example.mytech.controller.api;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.LoginRep;
import com.example.mytech.model.request.RegisterRep;
import com.example.mytech.model.response.ApiResponse;
import com.example.mytech.model.response.JwtResponse;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.security.JwtTokenUtil;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    private UserService userService ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRep rep, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        rep.getEmail(),
                        rep.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        // Add token to cookie to login
        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
        cookie.setMaxAge(Contant.MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse( jwt,
                userDetails.getUser().getId(),
                userDetails.getUser().getName(),
                userDetails.getUser().getEmail(),
                roles,userDetails.getUser().getImage()); // Thêm vào đây

        return ResponseEntity.ok(jwtResponse);

    }
    @PostMapping("/register")
    public ResponseEntity<?> Register(@Validated @RequestBody RegisterRep rep) {

        User result =  userService.registerUser(rep);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
