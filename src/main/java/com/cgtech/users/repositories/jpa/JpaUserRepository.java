/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.repositories.jpa;

import com.cgtech.users.repositories.UserRepository;
import com.cgtech.users.repositories.entities.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Victor
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class JpaUserRepository implements UserRepository{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public User addUser(User u) {
        em.persist(u);
        return u;
    }

    @Override
    public User updateUser(User u) {
       User existingUser = em.find(User.class, u.getId());
       existingUser.setEmail(u.getEmail());
       existingUser.setName(u.getName());
       return em.merge(existingUser);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> query = em.createQuery("Select u from User u where email= :email",User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if(users.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(users.get(0));
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
       return Optional.ofNullable(em.find(User.class, id));
    }
    
}
