/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.rest.resources;

import com.cgtech.users.repositories.entities.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Victor
 */
public class UserResource extends ResourceSupport {
    
    private Long userId; 
    
    @Email
    private String email;
    
    @Size(min = 1)
    private String name;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public User toUser(){
        User user = new User();
        user.setId(this.getUserId());
        user.setEmail(this.getEmail());
        user.setName(this.getName());
        return user;
    }
    
    
}
