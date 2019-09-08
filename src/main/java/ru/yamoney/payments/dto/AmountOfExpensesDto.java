package ru.yamoney.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountOfExpensesDto {
    private Long userId;
    private BigDecimal amount;
}
