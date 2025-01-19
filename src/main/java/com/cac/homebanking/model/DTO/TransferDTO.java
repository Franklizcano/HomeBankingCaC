package com.cac.homebanking.model.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TransferDTO {
    private Long id;
    private Long originId;
    private Long targetId;
    private ZonedDateTime date;
    private BigDecimal amount;

    public TransferDTO(Long originId, Long targetId, ZonedDateTime date, BigDecimal amount) {
        this.originId = originId;
        this.targetId = targetId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferDTO that = (TransferDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getOriginId(),
            that.getOriginId()) && Objects.equals(getTargetId(), that.getTargetId())
            && Objects.equals(getDate(), that.getDate()) && Objects.equals(
            getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOriginId(), getTargetId(), getDate(), getAmount());
    }
}
