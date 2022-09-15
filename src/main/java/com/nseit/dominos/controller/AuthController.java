package com.nseit.dominos.controller;

import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.service.OrderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value={"http://localhost:3000/"})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private OrderUserService orderUserService;

    public AuthController(OrderUserService orderService) {
        this.orderUserService = orderService;
    }

    @PostMapping("/register")
    public ResponseEntity<OrderUser> register(@RequestBody OrderUser orderUser) {
        OrderUser registeredUser = orderUserService.registerAsCustomer(orderUser);
        if (registeredUser == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<OrderUser> login(@RequestBody OrderUser orderUser) {
        OrderUser loggedInUser = orderUserService.loginAsCustomer(orderUser);
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

}

