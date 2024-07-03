package com.sofka.pruebasofka.controller;

import com.sofka.pruebasofka.model.Audit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RabbitTemplate rabbitTemplate;

    public AuthController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestParam String username) {
        sendAuditEvent(username, "LOGIN");
        return Mono.just("Login successful");
    }

    @PostMapping("/logout")
    public Mono<String> logout(@RequestParam String username) {
        sendAuditEvent(username, "LOGOUT");
        return Mono.just("Logout successful");
    }

    private void sendAuditEvent(String username, String action) {
        Audit audit = new Audit();
        audit.setUsername(username);
        audit.setAction(action);
        audit.setLocalDateTime(LocalDateTime.now());
        rabbitTemplate.convertAndSend("auditExchange", "auditRoutingKey", audit);
    }
}
