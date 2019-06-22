package com.example.andi.webservicestestingpoc.io.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "addresses")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 8572956770683348071L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100)
    private String addressId;

    @Column(nullable = false, length = 15)
    private String city;

    @Column(nullable = false, length = 15)
    private String country;

    @Column(nullable = false, length = 100)
    private String streetName;

    @Column(nullable = false, length = 7)
    private String postalCode;

    @Column(nullable = false, length = 10)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

}
