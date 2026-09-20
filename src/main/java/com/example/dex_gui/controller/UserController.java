package com.example.dex_gui.controller;

import com.example.dex_gui.dto.ApiResponse;
import com.example.dex_gui.entity.Quartet;
import com.example.dex_gui.entity.User;
import com.example.dex_gui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {

        User foundedUser = userService.getUser(id);

        ApiResponse<User> response =
                new ApiResponse<>("User fetched successfully", foundedUser);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {

        User createdUser = userService.createUser(user);

        ApiResponse<User> response =
                new ApiResponse<>("User created successfully", createdUser);

        return ResponseEntity.status(201).body(response);
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        List<User> allUsers = userService.getAllUsers();

        ApiResponse<List<User>> response =
                new ApiResponse<>("Users fetched successfully", allUsers);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<User>> searchUser(
            @RequestParam String email) {

        User user = userService.getUserByEmail(email);

        ApiResponse<User> response =
                new ApiResponse<>("User found successfully", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/authorizeQuartet")
    public ResponseEntity<ApiResponse<String>> grantQuartetAccess(
            @PathVariable Long userId,
            @RequestBody Quartet request) {

        userService.grantQuartetAccess(
                userId,
                request.getCompound(),
                request.getStudy(),
                request.getDepartment(),
                request.getPartner()
        );

        ApiResponse<String> response =
                new ApiResponse<>(
                        "Quartet access granted successfully",
                        null
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/quartets")
    public ResponseEntity<ApiResponse<List<Quartet>>> getUserQuartets(
            @PathVariable Long userId) {

        List<Quartet> quartets =
                userService.getUserQuartets(userId);

        ApiResponse<List<Quartet>> response =
                new ApiResponse<>(
                        "Quartets fetched successfully",
                        quartets
                );

        return ResponseEntity.ok(response);
    }
}
