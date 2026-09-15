package com.likelion.seminar.service;

import com.likelion.seminar.dto.UserSaveRequest;
import com.likelion.seminar.entity.User;
import com.likelion.seminar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserSaveRequest request) {
        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .age(request.getAge())
                        .build()
        );
    }

}
