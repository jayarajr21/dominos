package com.nseit.dominos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequest {
    private Integer id;
    private String orderUserName;
    private String dishName;
    private Integer price;

}
