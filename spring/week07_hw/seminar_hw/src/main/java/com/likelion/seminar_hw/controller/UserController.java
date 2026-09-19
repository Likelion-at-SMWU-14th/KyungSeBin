package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.UserResponse;
import com.likelion.seminar_hw.dto.UserSaveRequest;
import com.likelion.seminar_hw.dto.UserUpdateRequest;
import com.likelion.seminar_hw.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 생성
    @PostMapping
    public void saveUser(
            @Valid @RequestBody UserSaveRequest request
    ) {
        userService.saveUser(request);
    }

    // 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("userId") Long userId
    ) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    // 사용자 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserResponse response =
                userService.updateUser(userId, request);

        return ResponseEntity.ok(response);
    }
}