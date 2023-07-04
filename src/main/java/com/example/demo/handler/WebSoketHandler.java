package com.example.demo.handler;

//Rest API 통신 -> 클라이언트가 먼저 요청하고 서버가 응답해주는 방식이지만
//polling , Long Polling , Streaming 방식
//1.polling 방식은 주기적으로 서버에 계속 요청을 보냄 (새로운데이터가있는지), 클라이언트가 많아질수록 서버에부담이됨
//2.Long Polling 방식은 polling과 같지만 요청을 잡아놓고 새로운 데이터가 생기면 그때 응답 -> 연결끊음
//3.Streaming 방식은 응답을 보내주고 나서도 연결을 안 끊음 -> 새로운데이터가 생길때마다 계속응답


//WebSocket 방식은
//http -> ws
//"ws://localhost:8080"
//양방형 통신이 가능 , 서버에서 클라이언트로 데이터를 보내는게 가능

//웹소켓은 접속방식
//1. 클라이언트 -> 서버로 tcp/ip(http) 통신 요청
//2. 서버 -> 클라이언트 통신수락
//3. 클라이언트 -> 서버 웹소켓 handshake
//4. 서버 -> 클라이언트 handshake 수락 하게되면 기능이 작동하게됨

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component //스테틱의개념(어디서든 사용가능하게 빈등록해주는것)
@Slf4j

//임플리먼트와 익스텐즈 차이
//부모 클래스에서 이미 정의 , 구현까지 해놨으면 익스텐즈
//정의만 해놓고 구현은 직접해야할때는 임플리먼트
public class WebSoketHandler extends TextWebSocketHandler {

    //웹소켓 통신은 서버랑 클라이언트가 1:n으로 연결됨
    //사용자들의 정보가 담겨있는 웹소켓세션
    private static List<WebSocketSession> list = new ArrayList<>();


    @Override//메세지 객체 안 -> 내용 헤더 글자수
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String payload = message.getPayload();
        log.info("payload:" + payload);

        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }
    }

    @Override // 클라이언트가 처음 접속했을때 호출되서 메서드
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
    }

    @Override //클라이언트 접속 해제했을떄 호출되는 메서드
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        list.remove(session);
    }
}
