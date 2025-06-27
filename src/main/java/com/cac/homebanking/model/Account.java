package com.cac.homebanking.model;

import com.cac.homebanking.model.audit.AuditAccountListener;
import com.cac.homebanking.model.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners({AuditingEntityListener.class, AuditAccountListener.class})
@Table(name = "accounts")
public class Account extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private BigDecimal balance;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Account(Long number, BigDecimal balance, Long userId, Currency currency) {
        this.number = number;
        this.balance = balance;
        this.userId = userId;
        this.currency = currency;
    }
}