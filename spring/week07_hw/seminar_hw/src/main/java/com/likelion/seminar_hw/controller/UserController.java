package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.UserSaveRequest;
import com.likelion.seminar_hw.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void saveUser(@Valid @RequestBody UserSaveRequest request) {
        userService.saveUser(request);
    }

}
