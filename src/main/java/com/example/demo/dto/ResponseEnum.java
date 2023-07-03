package com.example.demo.dto;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SELECT_SUCCESS(200, "조회에 성공했습니다.");

    private int code;
    private String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
