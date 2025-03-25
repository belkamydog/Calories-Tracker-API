package com.body.calories.controller;


import com.body.calories.models.User;
import com.body.calories.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private  final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @Operation (summary = "Create new user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    @Operation (summary = "Get user by id")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }
}
