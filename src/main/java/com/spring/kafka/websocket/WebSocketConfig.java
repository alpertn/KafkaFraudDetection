package com.spring.kafka.websocket;

import com.spring.kafka.websocket.CustomHandshakeInterceptor;
import com.spring.kafka.websocket.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{


    private final CustomHandshakeInterceptor handshake;
    private final RequestInterceptor requestInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){

        config.enableSimpleBroker("/topic"); // burdan mesaji alma client dinliyor burayi
        config.setApplicationDestinationPrefixes("/app");


    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry reqister){

        reqister.addEndpoint("/ws") // burdan baglanma sunucu dinliyor burayi
                .addInterceptors(handshake) // bundan hanshakeye gidiyor
                .setAllowedOriginPatterns("*")  // herkesin baglanabilmesi iicin
                .withSockJS();  // tarayici destegi

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration reqister){

        reqister.interceptors(requestInterceptor);
    }






}
