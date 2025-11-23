package com.cac.homebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @UuidGenerator
    private UUID id;
    private Long cbu;
    private String alias;
    private BigDecimal balance;
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Account(Long cbu, String alias, BigDecimal balance, UUID userId) {
        this.cbu = cbu;
        this.alias = alias;
        this.balance = balance;
        this.userId = userId;
    }
}
