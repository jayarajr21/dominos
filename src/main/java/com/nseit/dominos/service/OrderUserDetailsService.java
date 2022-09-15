package com.nseit.dominos.service;

import com.nseit.dominos.exception.ResourceNotFoundException;
import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.model.Role;
import com.nseit.dominos.repositary.OrderUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
    public class OrderUserDetailsService implements UserDetailsService {

        @Autowired
        private OrderUserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            OrderUser orderUser = userRepository.findByUserName(userName);
            if (orderUser != null) {
                return new User(orderUser.getUserName(), orderUser.getPassword(), buildSimpleGrantedAuthorities(orderUser.getRoles()));
            } else {
                throw new ResourceNotFoundException("User not found with username: " + userName);
            }
        }

        private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            return authorities;
        }
    }

