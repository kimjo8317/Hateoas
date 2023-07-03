package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    int code;
    String message;
    Object content;

    public ResponseDTO(ResponseEnum en, Object content) {
        this.code = en.getCode();
        this.message = en.getMessage();
        this.content = content;
    }

}
