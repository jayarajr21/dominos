package com.nseit.dominos.controller;

import com.nseit.dominos.model.*;
import com.nseit.dominos.request.CartRequest;
import com.nseit.dominos.response.APIResponse;
import com.nseit.dominos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private APIResponse apiResponse;

    @Secured({Role.ROLE_USER})
    @PostMapping("/add")
    public ResponseEntity<APIResponse> addToCart(@RequestBody CartRequest cartRequest) {
        List<CartDish> cartDishes = cartService.addToCart(cartRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartDishes);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Secured({Role.ROLE_USER, Role.ROLE_ADMIN})
    @GetMapping("/orderUser/{OrderUserId}")
    public ResponseEntity<APIResponse> showCartOfOrderUserById(@PathVariable Integer OrderUserId) {
        List<CartDish> cartDishes = cartService.showCartOfOrderUserById(OrderUserId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartDishes);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Secured({Role.ROLE_USER})
    @DeleteMapping("/{cartDishId}")
    public ResponseEntity<APIResponse> removeDishFromCart(@PathVariable Integer cartDishId) {
        List<CartDish> cartDishes = cartService.removeDishFromCart(cartDishId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cartDishes);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
