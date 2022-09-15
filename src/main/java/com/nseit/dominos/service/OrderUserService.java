package com.nseit.dominos.service;

import com.nseit.dominos.exception.ResourceAlreadyExistException;
import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.model.Role;
import com.nseit.dominos.repositary.OrderUserRepository;
import com.nseit.dominos.repositary.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderUserService {
    @Autowired
    private OrderUserRepository orderUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public OrderUser registerAsCustomer(OrderUser orderUser) {
        OrderUser user = orderUserRepository.findByUserName(orderUser.getUserName());
        if (user != null) {
            throw new ResourceAlreadyExistException("User Already Exists");
        }
        Role role = roleRepository.findByName(Role.USER);
        orderUser.setRoles(Set.of(role));
        orderUser.setPassword(bCryptPasswordEncoder.encode(orderUser.getPassword()));
        return orderUserRepository.save(orderUser);
    }

    public OrderUser loginAsCustomer(OrderUser orderUser) {
        OrderUser user = orderUserRepository.findByUserName(orderUser.getUserName());
        if (user != null && bCryptPasswordEncoder.matches(orderUser.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

}