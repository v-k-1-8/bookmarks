package com.progskipper.bookmarks.services;

import com.progskipper.bookmarks.entity.User;
import com.progskipper.bookmarks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void SaveUser(User user){
        System.out.print("hello");
        System.out.print(user);
        userRepository.save(user);
    }

    public List<User> GetAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> GetUser(Long id){
        return userRepository.findById(id);
    }

    public void DeleteUser(Long id){
       userRepository.deleteById(id);
    }
}
