package com.cac.homebanking.controller;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.dto.UserDto;
import com.cac.homebanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID userId) throws NotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID userId, @RequestBody UserDto userDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(userId, userDTO));
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(userId));
    }
}