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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_cart", referencedColumnName = "id")
    private OrderUser orderUser;

    @OneToMany
    @JoinColumn(name="dish_cart", referencedColumnName = "id")
    private Set<Dish> dishes;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartDish> cartDishes;

}
