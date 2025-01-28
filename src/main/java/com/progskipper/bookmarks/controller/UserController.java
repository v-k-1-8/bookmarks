package com.progskipper.bookmarks.controller;

import com.progskipper.bookmarks.entity.User;
import com.progskipper.bookmarks.services.BookmarkService;
import com.progskipper.bookmarks.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
public class UserController {
    ///  Handles all functionalities related to Users

    // Class contains all User Services
    private final UserService userService;
    // Class contains all Bookmark Services
    private final BookmarkService bookmarkService;
    public UserController(UserService userService, BookmarkService bookmarkService) {
        this.userService = userService;
        this.bookmarkService = bookmarkService;
    }

    // To Create New User
    @PostMapping("/create-user")
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        try {
            userService.SaveUserService(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception: ",e);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    // To Get All Existing Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> GetAllUsers() {
        try{
            return new ResponseEntity<>(userService.GetAllUsersService(),HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To Get User by id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> GetUser(@PathVariable Long id){
        try{
            Optional<User> user = userService.GetUserService(id);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // To Update User by id
    @PutMapping("/user/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable Long id,@RequestBody User user){
        try{
            Optional<User> userOptional = userService.GetUserService(id);
            if (userOptional.isPresent()) {
                User existingUser = UpdateUserParameters(user, userOptional.get());
                userService.SaveUserService(existingUser);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            }return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Function Updating Parameters of User
    private static User UpdateUserParameters(User user, User existingUser) {
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

    // To Delete User and its corresponding Bookmarks  by id
    @Transactional
    @DeleteMapping("/user/{id}")
    public void DeleteUser(@PathVariable Long id) {
        bookmarkService.DeleteBookmarksByUserIdService(id);
        userService.DeleteUserService(id);
    }


}
