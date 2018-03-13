/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.repositories;

import com.cgtech.users.repositories.entities.User;
import java.util.Optional;

/**
 *
 * @author Victor
 */
public interface UserRepository {
    public User addUser(User u);
    public User updateUser(User u);
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(Long id);
    
}
