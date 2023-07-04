package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //rest컨트롤러는 데이터만 , 그냥컨트롤러는 뷰 리턴
public class ChatController {

    @GetMapping("/chat")
    public String chatPage() {
        System.out.println("1");
//        prefix: classpath:templates/ #뷰를 찾을때 야믈에서 설정값을 읽어옴
        return "chat";
    }
}
