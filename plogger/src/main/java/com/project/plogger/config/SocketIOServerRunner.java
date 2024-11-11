package com.project.plogger.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocketIOServerRunner implements CommandLineRunner { // SocketIOServer의 생명주기 관리
    
    private final SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
        System.out.println("server start!");
    }

    @PreDestroy
    public void stop() {
        server.stop();
        System.out.println("server stop!");
    }

}
