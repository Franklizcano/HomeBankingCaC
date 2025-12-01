package com.cac.homebanking.repository;

import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Integer countByUserIdAndCurrency(String userId, Currency currency);
    Optional<Account> findByCbu(Long cbu);
    Optional<Account> findByAlias(String alias);
}