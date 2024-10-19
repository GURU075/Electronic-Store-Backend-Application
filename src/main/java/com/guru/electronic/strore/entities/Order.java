package com.guru.electronic.strore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;

    //pending dispactched deliverd
    private String orderStatus;

    private String paymentStatus;

    private long orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private LocalDate orderedDate;

    private LocalDate deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
