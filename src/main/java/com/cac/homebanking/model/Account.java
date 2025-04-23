package com.cac.homebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private BigDecimal balance;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public Account(Long number, BigDecimal balance, Long userId) {
        this.number = number;
        this.balance = balance;
        this.userId = userId;
    }
}
