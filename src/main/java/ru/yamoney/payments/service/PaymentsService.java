package ru.yamoney.payments.service;

import ru.yamoney.payments.dto.AmountOfExpensesDto;
import ru.yamoney.payments.dto.PaymentCreateDto;
import ru.yamoney.payments.entity.Payment;

import java.util.List;

public interface PaymentsService {
    Payment addPayment(PaymentCreateDto paymentDto);

    List<Payment> findAll();

    AmountOfExpensesDto getAmountOfExpenses(Long userId);

    Payment save(Payment payment);
}
