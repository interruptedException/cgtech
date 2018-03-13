/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.services.basic;

import com.cgtech.users.repositories.UserRepository;
import com.cgtech.users.repositories.entities.User;
import com.cgtech.users.services.UserService;
import com.cgtech.users.services.exceptions.UserNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Victor
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BasicUserService implements UserService{

    @Autowired
    UserRepository userRepo;
    
    @Override
    public User addOrUpdate(User u) {
        Optional<User> user = userRepo.findUserByEmail(u.getEmail());
        if(!user.isPresent()){
            return userRepo.addUser(u);
        }else{
            u.setId(user.get().getId());
            return userRepo.updateUser(u);
        }
    }

    @Override
    public User findUserById(Long id) {
       Optional<User> user = userRepo.findUserById(id);
       return user.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
       Optional<User> user = userRepo.findUserByEmail(email);
       return user;
    }

    @Override
    public User updateUser(User u) {
       return userRepo.updateUser(u);
    }

    @Override
    public User createUser(User u) {
        return userRepo.addUser(u);
    }
    
   
}
