package com.progskipper.bookmarks.controller;

import com.progskipper.bookmarks.entity.User;
import com.progskipper.bookmarks.services.BookmarkService;
import com.progskipper.bookmarks.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Log4j2
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


    @Operation(
            summary = "Create a new user",
            description = "Add a new user to the system by providing their details"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "PartialUpdate",
                            value = "{ \"name\": \"varun\", \"dob\": \"1995-06-15T00:00:00.000Z\", \"email\": \"varun@example.com\", \"address\": \"Roorkee\" }"
                    )
            )
    )
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


    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users in the system"
    )
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

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a specific user using their unique ID as a path parameter"
    )
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

    @Operation(
            summary = "Update user by ID",
            description = "Updates the details of an existing user identified by their unique ID."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "PartialUpdate",
                            value = "{ \"name\": \"varun\", \"dob\": \"1995-06-15T00:00:00.000Z\", \"email\": \"varun@example.com\", \"address\": \"Roorkee\" }"
                    )
            )
    )
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

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a specific user identified by their unique ID."
    )
    @Transactional
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> DeleteUser(@PathVariable Long id) {
        try{
            bookmarkService.DeleteBookmarksByUserIdService(id);
            userService.DeleteUserService(id);
            return new ResponseEntity<>("User Deleted",HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
