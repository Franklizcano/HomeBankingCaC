package com.cac.homebanking.models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private List<AccountDTO> accounts = new ArrayList<>();
}