package com.example.demo.config;

import com.example.demo.handler.WebSoketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor //의존성주입
@Configuration //빈등록  (설정파일)
@EnableWebSocket //웹소켓 활성화
public class WebSoketConfig implements WebSocketConfigurer {
    private final WebSoketHandler webSoketHandler;

    //구현해ㅔ놓은 웹소켓 핸들러를 웹소켓 서버에 적용
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSoketHandler, "/ws/chat").setAllowedOrigins("*");
        //setAllowedOrigins : cors제한 풀어주는 메서드
    }
}
