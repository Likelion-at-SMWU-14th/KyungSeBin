package com.likelion.seminar_hw.global.exception;

public class DuplicateEmailException extends RuntimeException {

    private static final String DEFAULT_MESSAGE="이미 사용 중인 이메일입니다.";

    public DuplicateEmailException(String message){
        super(message);
    }
    public DuplicateEmailException(){
        super(DEFAULT_MESSAGE);
    }
}
