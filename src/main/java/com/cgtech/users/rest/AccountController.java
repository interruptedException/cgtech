/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.rest;

import com.cgtech.users.repositories.entities.User;
import com.cgtech.users.rest.exceptions.BadRequestException;
import com.cgtech.users.rest.resources.UserResource;
import com.cgtech.users.rest.resources.asm.UserResourceAsm;
import com.cgtech.users.services.UserService;
import com.cgtech.users.services.exceptions.UserNotFoundException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Victor
 */
@Controller
public class AccountController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value= "/accounts", method = RequestMethod.POST)
    public ResponseEntity<UserResource> addOrUpdate(@RequestBody @Valid UserResource userResource,Errors er){
        if(er.hasErrors()){
            throw new BadRequestException();
        }
        Optional<User> user = userService.findUserByEmail(userResource.getEmail());
        if(!user.isPresent()){
            User newUser = userService.createUser(userResource.toUser());
            return new ResponseEntity<>(new UserResourceAsm().toResource(newUser),HttpStatus.CREATED);
        }else{
            userResource.setUserId(user.get().getId());
            User updatedUser = userService.updateUser(userResource.toUser());
            return new ResponseEntity<>(new UserResourceAsm().toResource(updatedUser),HttpStatus.OK);
        }
    }
    
   @RequestMapping(value="/accounts/{id}")
   public ResponseEntity<UserResource> getAccount(@PathVariable Long id){
       try{
           User user = userService.findUserById(id);
           return new ResponseEntity<>(new UserResourceAsm().toResource(user), HttpStatus.OK);
       }catch(UserNotFoundException ex){
           throw new BadRequestException();
       }
   }
    
}
