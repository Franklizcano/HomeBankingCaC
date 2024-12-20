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
        dto.setUserId(account.getUserId());
        return dto;
    }

    public Account accountDTOToEntity(AccountDTO dto) {
        Account user = new Account();
        user.setId(dto.getId());
        user.setBalance(dto.getBalance());
        user.setNumber(dto.getNumber());
        user.setUserId(dto.getUserId());
        return user;
    }
}
