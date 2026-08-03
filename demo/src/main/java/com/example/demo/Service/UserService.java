package com.example.demo.Service;

import com.example.demo.DTO.UserResponse;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    //get all users
    public List<UserResponse> getAllUsers(){
        List<UserModel> users = userRepo.findAll();
        return modelMapper.map(users, new TypeToken<List<UserResponse>>(){}.getType());
    }

    //get user by id
    public UserResponse getUserById(Integer userId){
        UserModel user = userRepo.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not found with id: " + userId)
        );
        return modelMapper.map(user, UserResponse.class);
    }

    //delete user by id
    public String deleteUser(Integer userId){
        if(!userRepo.existsById(userId)){
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepo.deleteById(userId);
        return "User deleted Successfully with id: " + userId;
    }
}
