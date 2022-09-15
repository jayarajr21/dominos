package com.nseit.dominos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

    public static final String USER = "CUSTOMER";
    public static final String ROLE_USER = "ROLE_CUSTOMER";

    public static final String ADMIN = "ADMIN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;

    private LocalDateTime localDateTime;


    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<OrderUser> orderUsers;

    public Role(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime.now();
    }
}
