package com.nseit.dominos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Integer orderId;
    private Integer orderUserId;
}
