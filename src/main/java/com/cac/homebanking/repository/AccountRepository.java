package com.cac.homebanking.repository;

import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Integer countByUserIdAndCurrency(UUID userId, Currency currency);
}