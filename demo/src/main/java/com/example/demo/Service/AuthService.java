package com.example.demo.Service;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    //register service function
    public AuthResponse RegisterUser(RegisterRequest registerRequest){
        UserModel userData = modelMapper.map(registerRequest, UserModel.class);
        if(userRepo.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("User Email Already Exist!");
        }
        userData.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepo.save(userData);
        String email = userData.getEmail();
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        String token = jwtService.GenerateToken(claims, email);
        return new AuthResponse(token);
    }
}
