package ru.yamoney.payments.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="payments")
@Data
public class Payment {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User senderUser;
    @ManyToOne
    @JoinColumn(name = "recipient_user_id", nullable = false)
    private User recipientUser;
    private BigDecimal amount;
}
