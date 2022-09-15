package com.nseit.dominos.controller;

import com.nseit.dominos.model.Dish;
import com.nseit.dominos.request.DishRequest;
import com.nseit.dominos.response.APIResponse;
import com.nseit.dominos.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DishService dishService;

    @PostMapping("/category")
    public ResponseEntity<APIResponse> addCategory(@RequestBody DishRequest dish) {
        APIResponse apiResponse = new APIResponse();
        Dish cat = dishService.addDish(dish);
        if (cat == null) {
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(cat);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}