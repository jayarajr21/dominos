package com.nseit.dominos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor

@NoArgsConstructor
@Table(name = "users")
public class OrderUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String userName;

    private String password;

    private Long phoneNumber;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;


    @OneToMany(mappedBy = "orderUser", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "orderUser",cascade = CascadeType.ALL)
    private Set<Dish> dishes;

    @OneToMany(mappedBy = "orderUser", cascade= CascadeType.ALL)
    private Set<OrderHistory> orderHistories;

    @ManyToMany
    @JsonIgnore
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public OrderUser(String userName,String password){
        this.userName=userName;
        this.password=password;

    }

    public void setPhoneNumber() {
    }
}
