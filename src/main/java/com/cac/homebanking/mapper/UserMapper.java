package com.cac.homebanking.mapper;

import com.cac.homebanking.model.dto.UserDto;
import com.cac.homebanking.model.UserBank;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto userEntityToDTO(UserBank user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setAccounts(user.getAccounts().stream().map(AccountMapper::accountEntityToDTO).toList());
        return dto;
    }

    public UserBank userDTOToEntity(UserDto dto) {
        UserBank user = new UserBank();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAccounts(dto.getAccounts().stream().map(AccountMapper::accountDTOToEntity).toList());
        return user;
    }
}
