package com.project.myfirstproject.service;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.repository.Clientrentryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

//import static jdk.internal.classfile.impl.DirectCodeBuilder.build;

@Component
public class ClientDetailsServiceImp implements UserDetailsService {

    @Autowired
    private Clientrentryrepo clientrentryrepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        System.out.println("Login username = " + username);

        ClientEntry user = clientrentryrepo.findByClientName(username);

        System.out.println("User from DB = " + user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(user.getClientName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

}