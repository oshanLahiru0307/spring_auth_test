package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    //get all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //get user by id
    @GetMapping("/getById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    //delete user by id
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
