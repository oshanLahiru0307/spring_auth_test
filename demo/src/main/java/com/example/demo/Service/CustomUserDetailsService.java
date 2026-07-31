package com.example.demo.Service;

import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserModel existingUser = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with Email: "+ email));

        UserDetails user = User.builder()
                .username(existingUser.getEmail())
                .password(existingUser.getPassword())
                .build();

        return user;
    }

}
