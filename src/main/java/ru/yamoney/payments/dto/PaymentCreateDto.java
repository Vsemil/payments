package ru.yamoney.payments.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaymentCreateDto {
    private Long id;
    @NotNull
    private Long senderUserId;
    @NotNull
    private Long recipientUserId;
    @NotNull
    private Double amount;
}
