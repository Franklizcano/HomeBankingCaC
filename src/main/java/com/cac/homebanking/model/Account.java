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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    @Column(nullable = false, unique = true)
    private Long cbu;
    @Column(nullable = false, unique = true)
    private String alias;
    private BigDecimal balance;
    @Column(name = "user_id")
    private String userId;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Account(Long cbu, String alias, BigDecimal balance, String userId) {
        this.cbu = cbu;
        this.alias = alias;
        this.balance = balance;
        this.userId = userId;
    }
}
