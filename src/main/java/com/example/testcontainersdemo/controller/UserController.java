package com.example.testcontainersdemo.controller;

import com.example.testcontainersdemo.dto.UserDto;
import com.example.testcontainersdemo.entity.User;
import com.example.testcontainersdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }
}
