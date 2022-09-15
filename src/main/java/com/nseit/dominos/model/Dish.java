package com.nseit.dominos.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue
    private Integer id;
    private String dishName;
    private Integer price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "orderUser",referencedColumnName = "id")
    private OrderUser orderUser;

    @OneToMany(mappedBy = "dishes",cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private Set<OrderHistory> orderHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CartDish> cartDishes;

}
