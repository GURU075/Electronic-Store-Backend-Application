package com.guru.electronic.strore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(name="user_name" , length=50)
    private String name;

    @Column(name="user_email",unique = true)
    private String email;

    @Column(name ="user_password", length=10)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name="user_image_name")
    private String imageName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders=new ArrayList<>();

}
