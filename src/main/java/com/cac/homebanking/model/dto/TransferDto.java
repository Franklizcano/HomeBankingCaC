package com.cac.homebanking.model.dto;

import com.cac.homebanking.model.IdentifierType;
import com.cac.homebanking.model.TransferStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class TransferDto {
    private String id;
    private String originId;
    private String targetId;
    private IdentifierType identifierType;
    private ZonedDateTime date = ZonedDateTime.now();
    private BigDecimal amount;
    private TransferStatus status;

    public TransferDto(String originId, String targetId, IdentifierType identifierType, BigDecimal amount, TransferStatus status) {
        this.originId = originId;
        this.targetId = targetId;
        this.identifierType = identifierType;
        this.amount = amount;
        this.status = status;
    }
}
