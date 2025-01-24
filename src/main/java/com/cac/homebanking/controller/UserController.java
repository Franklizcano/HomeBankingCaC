package com.cac.homebanking.controller;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.DTO.UserDTO;
import com.cac.homebanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(userId, userDTO));
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(userId));
    }
}