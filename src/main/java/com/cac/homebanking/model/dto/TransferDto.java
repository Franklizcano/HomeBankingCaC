package com.cac.homebanking.model.dto;

import com.cac.homebanking.model.IdentifierType;
import com.cac.homebanking.model.TransferStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransferDto {
    private UUID id;
    private UUID originId;
    private UUID targetId;
    private IdentifierType identifierType;
    private ZonedDateTime date = ZonedDateTime.now();
    private BigDecimal amount;
    private TransferStatus status;

    public TransferDto(UUID originId, UUID targetId, IdentifierType identifierType, BigDecimal amount, TransferStatus status) {
        this.originId = originId;
        this.targetId = targetId;
        this.identifierType = identifierType;
        this.amount = amount;
        this.status = status;
    }
}
