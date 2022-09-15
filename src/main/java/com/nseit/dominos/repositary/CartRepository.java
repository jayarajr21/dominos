package com.nseit.dominos.repositary;

import com.nseit.dominos.model.Cart;
import com.nseit.dominos.model.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByOrderUser(OrderUser orderUser);

}
