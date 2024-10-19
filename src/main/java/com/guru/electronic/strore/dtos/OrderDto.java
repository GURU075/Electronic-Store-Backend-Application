package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.entities.OrderItem;
import com.guru.electronic.strore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private long orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private LocalDate orderedDate=LocalDate.now();

    private LocalDate deliveredDate;

//    private User user;

    private List<OrderItemDto> orderItems = new ArrayList<>();

}
