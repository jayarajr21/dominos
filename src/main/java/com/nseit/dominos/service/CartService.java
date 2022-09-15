package com.nseit.dominos.service;

import com.nseit.dominos.exception.ResourceNotFoundException;
import com.nseit.dominos.model.Cart;
import com.nseit.dominos.model.CartDish;
import com.nseit.dominos.model.Dish;
import com.nseit.dominos.model.OrderUser;
import com.nseit.dominos.repositary.CartDishRepository;
import com.nseit.dominos.repositary.CartRepository;
import com.nseit.dominos.repositary.DishRepository;
import com.nseit.dominos.repositary.OrderUserRepository;
import com.nseit.dominos.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderUserRepository orderUserRepository;
    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CartDishRepository cartDishRepository;

    public List<CartDish> addToCart(CartRequest cartRequest) {
        List<CartDish> cartBooks = new ArrayList<>();

        OrderUser orderUser = orderUserRepository.findById(cartRequest.getOrderUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id "
                        + cartRequest.getOrderUserId()));

        Dish dish = dishRepository.findById(cartRequest.getDishId())
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id "
                        + cartRequest.getDishId()));

        Cart cart = cartRepository.findByOrderUser(orderUser)
                .orElseThrow(() -> new ResourceNotFoundException("No books in the cart for the user"));

        if (cartDishRepository.findByDishAndCart(dish, cart).isPresent()) {
            cartBooks = cartDishRepository.findByDishAndCart(dish, cart).get();
            boolean isExist = false;
            for (CartDish cartDish : dish.getCartDishes())
                if (cartDish.getDish().equals(dish)) {
                    cartDish.setCount(cartRequest.getCount());
                    cartDishRepository.save(cartDish);
                    isExist = true;
                }
            if (!isExist) {
                cartBooks.add(addDishToCart(dish, cart, cartRequest.getCount()));
            }
        } else {
            cartBooks.add(addDishToCart(dish, cart, cartRequest.getCount()));
        }

        return cartDishRepository.findByCart((Cart) orderUser.getCarts())
                .orElseThrow(() -> new ResourceNotFoundException("No dishes in the cart for the user"));
    }

    private CartDish addDishToCart(Dish dish, Cart cart, int count) {
        CartDish cartDish = new CartDish();
        cartDish.setDish(dish);
        cartDish.setCart(cart);
        cartDish.setCount(count);
        cartDishRepository.save(cartDish);
        return cartDish;
    }

    public List<CartDish> showCartOfOrderUserById(Integer userId) {
        OrderUser orderUser = orderUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id "
                        + userId));

        return cartDishRepository.findByCart((Cart) orderUser.getCarts())
                .orElseThrow(() -> new ResourceNotFoundException("No dishes in the cart for the user"));
    }

    public List<CartDish> removeDishFromCart(Integer cartDishId) {
        CartDish cartDish = cartDishRepository.findById(cartDishId)
                .orElseThrow(() -> new ResourceNotFoundException("No dishes in the cart for the user"));

        cartDishRepository.deleteById(cartDishId);

        return cartDishRepository.findByCart(cartDish.getCart())
                .orElseThrow(() -> new ResourceNotFoundException("No dishes in the cart for the user"));

    }

}
