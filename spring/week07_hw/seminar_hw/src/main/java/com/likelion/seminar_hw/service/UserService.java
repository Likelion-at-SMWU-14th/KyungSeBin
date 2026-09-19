package com.likelion.seminar_hw.service;

import com.likelion.seminar_hw.dto.UserSaveRequest;
import com.likelion.seminar_hw.entity.User;
import com.likelion.seminar_hw.global.exception.DuplicateEmailException;
import com.likelion.seminar_hw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserSaveRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException();
        }
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
