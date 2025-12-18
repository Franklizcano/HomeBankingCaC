package com.cac.homebanking.mapper;

import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.dto.AccountDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public AccountDto accountEntityToDTO(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setCbu(account.getCbu());
        dto.setAlias(account.getAlias());
        dto.setCurrency(account.getCurrency());
        dto.setUserId(account.getUserId());
        return dto;
    }

    public Account accountDTOToEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setBalance(dto.getBalance());
        account.setCbu(dto.getCbu());
        account.setAlias(dto.getAlias());
        account.setCurrency(dto.getCurrency());
        account.setUserId(dto.getUserId());
        return account;
    }
}
