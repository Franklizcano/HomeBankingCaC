package com.cac.homebanking.mappers;

import com.cac.homebanking.models.DTO.UserDTO;
import com.cac.homebanking.models.UserBank;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDTO userEntityToDTO(UserBank user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setAccounts(user.getAccounts().stream().map(AccountMapper::accountEntityToDTO).toList());
        return dto;
    }

    public UserBank userDTOToEntity(UserDTO dto) {
        UserBank user = new UserBank();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAccounts(dto.getAccounts().stream().map(AccountMapper::accountDTOToEntity).toList());
        return user;
    }
}
