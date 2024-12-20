package com.cac.homebanking.services;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mappers.UserMapper;
import com.cac.homebanking.models.DTO.UserDTO;
import com.cac.homebanking.models.UserBank;
import com.cac.homebanking.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userEntityToDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) throws NotFoundException {
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
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getAccounts().isEmpty()) {
            userDTO.setAccounts(Collections.emptyList());
        }
        return UserMapper.userEntityToDTO(userRepository.save(UserMapper.userDTOToEntity(userDTO)));
    }

    public UserDTO update(Long id, UserDTO userDTO) throws NotFoundException {
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

    public String delete(Long id) throws NotFoundException {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "The user has been deleted.";
        } else {
            throw new NotFoundException("User not found with the id " + id);
        }
    }

    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    // TODO: Generar la asociación de una primer cuenta al crear un User
    // Agregar una cuenta al usuario
    /*public UserDto addAccountToUser(AccountDto account, Long id){
        // primero: buscar el usuario por id
        // segundo: añadir la cuenta a la lista del usuario encontrado
        // tercero: devolver el usuario con la cuenta agregada
        return null;
    }*/
}
