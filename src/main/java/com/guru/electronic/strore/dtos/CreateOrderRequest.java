package com.guru.electronic.strore.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    private String cartId;

    private int userId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private String billingAddress;

    private String billingPhone;

    private String billingName;
}
