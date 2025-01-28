package com.progskipper.bookmarks.services;

import com.progskipper.bookmarks.entity.User;
import com.progskipper.bookmarks.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    ///  Handles all Services related to Users

    // Interface with ORM functionalities
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save User
    public void SaveUserService(User user){
        userRepository.save(user);
    }

    // Get All Users
    public List<User> GetAllUsersService(){
        return userRepository.findAll();
    }

    // Get User by id
    public Optional<User> GetUserService(Long id){
        return userRepository.findById(id);
    }

    // Delete User by id
    public void DeleteUserService(Long id){
       userRepository.deleteById(id);
    }
}
