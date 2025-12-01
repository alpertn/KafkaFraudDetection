package com.spring.kafka.websocket;

import com.spring.kafka.data.repository.RequestDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomHandshakeInterceptor implements HandshakeInterceptor{

    private final RequestDataRepository repository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverhttprequest, ServerHttpResponse serverhttpresponse, WebSocketHandler webSocketHandler, Map<String,Object> attrs){

        if (serverhttprequest instanceof ServletServerHttpRequest atanmisRequest){ // eger request ServletServerHttpRequest turunde isee

            var servletRequest = atanmisRequest.getServletRequest(); // islemek icin aliyoruz

            String ip = servletRequest.getRemoteAddr();
            String userAgent = servletRequest.getHeader("User-Agent");

            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){

                ip = "localhost";
                return true;

            }


            if (repository.existsByIpAndBlacklistTrue(ip)) {

                if (repository.findReasonByIp(ip) != null){


                    throw new RuntimeException("Blackliste alindiniz.");
                }

            }

            attrs.put("IP" , ip);
            attrs.put("User-Agent" , userAgent);
            return true;

        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

}
