package com.likelion.seminar.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter

public class UserSaveRequest {

    @NotBlank(message="이름은 필수입니다")
    @Size(max=20, message="이름은 20자 이하로 입력해야 합니다")
    private String name;

    @NotBlank(message="이메일은 필수입니다")
    @Email(message="올바른 이메일 형식이어야 합니다")
    private String email;

    @NotBlank(message="비밀번호는 필수입니다")
    @Size(min=8,max=20,message="비밀번호는 8자 이상 20자 이하로 입력해야 합니다")
    private String password;

    @NotNull(message="나이는 필수입니다")
    @Min(value=0,message="나이는 0 이상이어야 합니다")
    private Integer age;

}