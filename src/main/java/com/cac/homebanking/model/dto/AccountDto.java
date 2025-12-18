package com.cac.homebanking.model.dto;

import com.cac.homebanking.model.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class AccountDto {
    private String id;
    private Long cbu;
    private String alias;
    private BigDecimal balance;
    private String userId;
    private Currency currency;
}