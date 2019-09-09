package ru.yamoney.payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yamoney.payments.dto.AmountOfExpensesDto;
import ru.yamoney.payments.dto.PaymentCreateDto;
import ru.yamoney.payments.dto.PaymentDto;
import ru.yamoney.payments.entity.Payment;
import ru.yamoney.payments.mappers.PaymentMapper;
import ru.yamoney.payments.service.PaymentsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;
    private final PaymentMapper paymentMapper;

    @GetMapping("/allPayments")
    public List<PaymentDto> getAllPayments() {
        return paymentsService.findAll().stream()
                .map(paymentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/addPayment")
    public PaymentDto addPayment(@Validated final PaymentCreateDto paymentDto) {
        if (paymentDto.getSenderUserId().equals(paymentDto.getRecipientUserId())) {
            throw new IllegalArgumentException("Recipient and sender cannot be one user");
        }
        final Payment payment = paymentsService.addPayment(paymentDto);
        return paymentMapper.entityToDto(payment);
    }

    @GetMapping("/amountOfExpenses")
    public AmountOfExpensesDto getAmountOfExpenses(@RequestParam final Long userId) {
        final AmountOfExpensesDto amountOfExpenses = paymentsService.getAmountOfExpenses(userId);
        return amountOfExpenses == null ? new AmountOfExpensesDto(userId, new BigDecimal(0)) : amountOfExpenses;
    }


}
