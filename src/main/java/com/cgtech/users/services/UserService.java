/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.services;

import com.cgtech.users.repositories.entities.User;
import java.util.Optional;



/**
 *
 * @author Victor
 */

public interface UserService {
    public User addOrUpdate(User u);
    public User findUserById(Long id);
    public Optional<User> findUserByEmail(String email);
    public User updateUser(User u);
    public User createUser(User u);
}
