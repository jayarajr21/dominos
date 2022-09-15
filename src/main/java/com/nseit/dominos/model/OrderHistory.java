package com.nseit.dominos.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderHistory")

public class OrderHistory {
    @Id
    @GeneratedValue
    private Integer id;
    private String itemName;
    private Date date;
    private Time time;

    @ManyToOne
    @JoinColumn(name="dish_history", referencedColumnName = "id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "user_history", referencedColumnName = "id")
    private OrderUser orderUser;
}
