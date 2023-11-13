package com.gopang.security;

import com.gopang.entity.CustumUserDetails;
import com.gopang.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@Data
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo){this.userRepository = userRepo;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustumUserDetails custumUser = userRepository.findByEmail(email);
        if(custumUser != null){
            System.out.println("-------------------------------------");
            System.out.println("username"+ custumUser.getEmail());
            System.out.println("password"+ custumUser.getPassword());
            return custumUser;
        }
        throw new UsernameNotFoundException( "user '" + email +"'not found");
    }
}
