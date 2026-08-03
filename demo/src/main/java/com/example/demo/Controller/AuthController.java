package com.example.demo.Controller;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> UserLogin(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.LoginUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> UserRegister(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.RegisterUser(registerRequest));
    }
}
