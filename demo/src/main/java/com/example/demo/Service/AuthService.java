package com.example.demo.Service;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    //register service function
    public AuthResponse RegisterUser(RegisterRequest registerRequest){
        UserModel userData = modelMapper.map(registerRequest, UserModel.class);
        if(userRepo.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("User Email Already Exist!");
        }
        userData.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepo.save(userData);
        Map<String, Object> regClaims = new HashMap<>();
        regClaims.put("name", userData.getName());
        String token = jwtService.GenerateToken(regClaims, userData.getEmail());
        return new AuthResponse(token);
    }

    //login service function
    public AuthResponse LoginUser(LoginRequest loginRequest){
        UserModel userData = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new RuntimeException("user not Found with Email: " + loginRequest.getEmail())
        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        Map<String, Object> logClaims = new HashMap<>();
        logClaims.put("name", userData.getName());
        logClaims.put("id", userData.getId());
        String token = jwtService.GenerateToken(logClaims, userData.getEmail());
        return new AuthResponse(token);
    }
}
