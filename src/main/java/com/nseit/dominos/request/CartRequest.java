package com.nseit.dominos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Integer orderUserId;
    private Integer orderId;
    private Integer count;
    private Integer dishId;

    public Integer getDishId() {
        return dishId;
    }

    public Integer getOrderUserId() {
        return orderUserId;
    }
}