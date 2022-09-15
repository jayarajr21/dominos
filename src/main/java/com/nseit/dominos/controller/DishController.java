package com.nseit.dominos.controller;
import com.nseit.dominos.model.Dish;
import com.nseit.dominos.request.DishRequest;
import com.nseit.dominos.response.APIResponse;
import com.nseit.dominos.response.SuccessResponse;
import com.nseit.dominos.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value={"http://localhost:3000/"})
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private APIResponse apiResponse;

//    @Secured({Role.ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<APIResponse> addDish(@RequestBody DishRequest dishRequest) {
        Dish addedDish = dishService.addDish(dishRequest);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(addedDish);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

   // @Secured({Role.ROLE_ADMIN})
    @PutMapping
    public ResponseEntity<APIResponse> updateDish(@RequestBody DishRequest dishRequest) {
        Dish updatedDish = dishService.updateDish(dishRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(updatedDish);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

   // @Secured({Role.ROLE_ADMIN})
    @DeleteMapping("/{dishId}")
    public ResponseEntity<APIResponse> deleteDish(@PathVariable Integer dishId) {
        dishService.deleteDish(dishId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(new SuccessResponse());
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

  //  @Secured({Role.ROLE_ADMIN, Role.ROLE_USER})
    @GetMapping("/all")
    public ResponseEntity<APIResponse> viewAllDishes() {
        List<Dish> dishes = dishService.viewAllDishes();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(dishes);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

  //  @Secured({Role.ROLE_ADMIN, Role.ROLE_USER})
    @GetMapping("/{dishId}")
    public ResponseEntity<APIResponse> viewDishById(@PathVariable Integer dishId) {
        Dish dish = dishService.findDishById(dishId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(dish);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
