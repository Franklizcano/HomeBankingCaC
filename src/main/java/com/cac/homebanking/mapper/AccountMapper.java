package com.cac.homebanking.mapper;

import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public AccountDTO accountEntityToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setNumber(account.getNumber());
        dto.setCurrency(account.getCurrency());
        dto.setUserId(account.getUserId());
        return dto;
    }

    public Account accountDTOToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setBalance(dto.getBalance());
        account.setNumber(dto.getNumber());
        account.setCurrency(dto.getCurrency());
        account.setUserId(dto.getUserId());
        return account;
    }
}
