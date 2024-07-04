package com.sofka.pruebasofka.controller;

import com.sofka.pruebasofka.model.Audit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final RabbitTemplate rabbitTemplate;

    public AuthController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestParam String username) {
        sendAuditEvent(username, "LOGIN");
        return Mono.just(ResponseEntity.ok().body(Map.of("message", "Login successful", "username", username)));
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<Object>> logout(@RequestParam String username) {
        sendAuditEvent(username, "LOGOUT");
        return Mono.just(ResponseEntity.ok().build());
    }

    private void sendAuditEvent(String username, String action) {
        Audit audit = new Audit();
        audit.setUsername(username);
        audit.setAction(action);
        audit.setLocalDateTime(LocalDateTime.now());
        rabbitTemplate.convertAndSend("auditExchange", "auditRoutingKey", audit);
    }
}
