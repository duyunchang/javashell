package com.tysx.vod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 *  1.@EnableWebSocketMessageBroker注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。
	2.registerStompEndpoints方法表示注册STOMP协议的节点，并指定映射的URL。
	3.stompEndpointRegistry.addEndpoint("/endpointSang").withSockJS();这一行代码用来注册STOMP协议节点，同时指定使用SockJS协议。
	4.configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
 * */
@Configuration  
public class WebSocketConfig {  
    @Bean  
    public ServerEndpointExporter serverEndpointExporter (){  
        return new ServerEndpointExporter();  
    }  
} 