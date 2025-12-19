package com.cac.homebanking.service;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.UserMapper;
import com.cac.homebanking.model.UserBank;
import com.cac.homebanking.model.dto.UserDto;
import com.cac.homebanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userEntityToDTO)
                .toList();
    }

    public UserDto getUserById(String id) throws NotFoundException {
        Optional<UserBank> user;
        try {
            user = userRepository.findById(id);
        } catch (Exception e) {
            throw new BusinessException("Ha ocurrido un error de tipo: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user.isEmpty()) {
            throw new NotFoundException("User not found with id " + id);
        }

        return UserMapper.userEntityToDTO(user.get());
    }

    @Transactional
    public UserDto createUser(UserDto userDTO) {
        if (userDTO.getAccounts().isEmpty()) {
            userDTO.setAccounts(Collections.emptyList());
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return UserMapper.userEntityToDTO(userRepository.save(UserMapper.userDTOToEntity(userDTO)));
    }

    public UserDto update(String id, UserDto userDTO) throws NotFoundException {
        Optional<UserBank> userCreated = userRepository.findById(id);

        if  (userCreated.isPresent()) {
            UserBank entity = userCreated.get();
            UserBank accountUpdated = UserMapper.userDTOToEntity(userDTO);
            accountUpdated.setId(entity.getId());
            UserBank saved = userRepository.save(accountUpdated);
            return UserMapper.userEntityToDTO(saved);
        } else {
            throw new NotFoundException("User not found with the id " + id);
        }
    }

    public String delete(String id) throws NotFoundException {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "The user has been deleted.";
        } else {
            throw new NotFoundException("User not found with the id " + id);
        }
    }

    public Boolean existsById(String id) {
        return userRepository.existsById(id);
    }
}
