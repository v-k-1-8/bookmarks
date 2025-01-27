package com.progskipper.bookmarks.controller;

import com.progskipper.bookmarks.entity.User;
import com.progskipper.bookmarks.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        try {
            userService.SaveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception: ",e);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> GetAllUsers() {
        try{
            return new ResponseEntity<>(userService.GetAllUsers(),HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> GetUser(@PathVariable Long id){
        try{
            Optional<User> user = userService.GetUser(id);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable Long id,@RequestBody User user){
        try{
            Optional<User> userOptional = userService.GetUser(id);
            if (userOptional.isPresent()) {
                User existingUser = updateUser(user, userOptional.get());
                userService.SaveUser(existingUser);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            }return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private static User updateUser(User user, User existingUser) {
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getDob() != null) {
            existingUser.setDob(user.getDob());
        }
        if (user.getAddress() != null) {
            existingUser.setAddress(user.getAddress());
        }
        return existingUser;
    }

    @DeleteMapping("/user/{id}")
    public void DeleteUser(@PathVariable Long id) {
        userService.DeleteUser(id);
    }


}
