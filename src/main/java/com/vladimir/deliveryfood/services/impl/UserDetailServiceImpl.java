package com.vladimir.deliveryfood.services.impl;

import com.vladimir.deliveryfood.entity.User;
import com.vladimir.deliveryfood.repository.UserRepository;
import com.vladimir.deliveryfood.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(username).orElseThrow(() ->
                     new UsernameNotFoundException("User не найден"));

        return SecurityUser.fromUser(user);
    }
}
