package com.nseit.dominos.service;

import com.nseit.dominos.exception.ResourceAlreadyExistException;
import com.nseit.dominos.exception.ResourceNotFoundException;
import com.nseit.dominos.model.Dish;
import com.nseit.dominos.repositary.DishRepository;
import com.nseit.dominos.request.DishRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public Dish addDish(DishRequest dishRequest) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishRequest, dish);

        boolean isBookExists = dishRepository.findByDishName(dishRequest.getDishName()).isPresent();
        if (isBookExists)
            throw new ResourceAlreadyExistException("Dish already exists.");

        return dishRepository.save(dish);
    }

    public Dish updateDish(DishRequest dishRequest) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishRequest, dish);
        if (dishRequest.getId() == null)
            throw new ResourceNotFoundException("No dish with id "
                    + dishRequest.getId());
        return dish;
    }

    public void deleteDish(Integer dishId) {
        dishRepository.findById(dishId).orElseThrow(() ->
                new ResourceNotFoundException("No dish with id "
                        + dishId));
        dishRepository.deleteById(dishId);
    }

    public List<Dish> viewAllDishes() {
        return dishRepository.findAll();
    }

    public Dish findDishById(Integer dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find book with id " + dishId));
    }

}
