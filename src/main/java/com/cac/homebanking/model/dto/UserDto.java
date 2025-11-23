package com.cac.homebanking.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private List<AccountDto> accounts = new ArrayList<>();
}