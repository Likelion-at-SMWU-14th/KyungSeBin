package com.likelion.seminar_hw.service;

import com.likelion.seminar_hw.dto.UserResponse;
import com.likelion.seminar_hw.dto.UserSaveRequest;
import com.likelion.seminar_hw.dto.UserUpdateRequest;
import com.likelion.seminar_hw.entity.User;
import com.likelion.seminar_hw.global.exception.DuplicateEmailException;
import com.likelion.seminar_hw.global.exception.UserNotFoundException;
import com.likelion.seminar_hw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 기존 세미나 코드: 사용자 생성
    @Transactional
    public void saveUser(UserSaveRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .age(request.getAge())
                .build();

        userRepository.save(user);
    }

    // 과제: 사용자 조회
    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserResponse.from(user);
    }

    // 과제: 사용자 수정
    @Transactional
    public UserResponse updateUser(
            Long userId,
            UserUpdateRequest request
    ) {

        // 1. 수정할 사용자가 존재하는지 검사
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 2. 다른 사용자가 사용 중인 이메일인지 검사
        boolean duplicateEmail =
                userRepository.existsByEmailAndIdNot(
                        request.getEmail(),
                        userId
                );

        if (duplicateEmail) {
            throw new DuplicateEmailException();
        }

        // 3. 모든 검사가 끝난 후 사용자 정보 수정
        user.update(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getAge()
        );

        return UserResponse.from(user);
    }
}