/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.rest.resources.asm;

import com.cgtech.users.repositories.entities.User;
import com.cgtech.users.rest.AccountController;
import com.cgtech.users.rest.resources.UserResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 *
 * @author Victor
 */
public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAsm() {
        super(AccountController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User u) {
       UserResource userRes = new UserResource();
       userRes.setUserId(u.getId());
       userRes.setEmail(u.getEmail());
       userRes.setName(u.getName());
       Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AccountController.class).getAccount(userRes.getUserId())).withSelfRel();
       userRes.add(link);
       return userRes;
    }
    
}
