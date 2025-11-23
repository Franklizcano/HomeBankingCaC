package com.cac.homebanking.model.dto;

import com.cac.homebanking.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class AccountDto {
    private UUID id;
    private Long cbu;
    private String alias;
    private BigDecimal balance;
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}