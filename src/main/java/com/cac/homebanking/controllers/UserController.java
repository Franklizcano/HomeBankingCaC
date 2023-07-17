package com.cac.homebanking.controllers;

import com.cac.homebanking.models.DTO.UserDTO;
import com.cac.homebanking.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/users/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long user_id) {
        return ResponseEntity.ok().body(userService.getUserById(user_id));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PutMapping(value = "/users/{user_id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long user_id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(user_id, userDTO));
    }

    @DeleteMapping(value = "/users/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(user_id));
    }
}
