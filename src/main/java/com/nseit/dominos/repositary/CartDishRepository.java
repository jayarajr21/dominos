package com.nseit.dominos.repositary;

import com.nseit.dominos.model.Cart;
import com.nseit.dominos.model.CartDish;
import com.nseit.dominos.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDishRepository extends JpaRepository<CartDish, Integer> {

    Optional<List<CartDish>> findByDishAndCart(Dish dish, Cart cart);

    Optional<List<CartDish>> findByCart(Cart cart);

}
