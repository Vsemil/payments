package ru.yamoney.payments.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private Long id;
    private UserDto senderUser;
    private UserDto recipientUser;
    private BigDecimal amount;
}
