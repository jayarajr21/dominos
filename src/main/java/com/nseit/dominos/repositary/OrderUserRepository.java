package com.nseit.dominos.repositary;

import com.nseit.dominos.model.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {
    OrderUser findByUserName(String userName);
}
