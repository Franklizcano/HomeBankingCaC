package com.cac.homebanking.model.DTO;

import com.cac.homebanking.model.TransferStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransferDTO {
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private Long originId;
    private Long targetId;
    private ZonedDateTime date = ZonedDateTime.now();
    private BigDecimal amount;
    private TransferStatus status;

    public TransferDTO(Long originId, Long targetId, BigDecimal amount, TransferStatus status) {
        this.originId = originId;
        this.targetId = targetId;
        this.amount = amount;
        this.status = status;
    }
}
