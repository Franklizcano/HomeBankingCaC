package com.cac.homebanking.repository;

import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Integer countByUserIdAndCurrency(Long userId, Currency currency);
}