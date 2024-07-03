package com.sofka.pruebasofka.listener;

import com.sofka.pruebasofka.model.Audit;
import com.sofka.pruebasofka.service.AuditService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RabbitMQListener {

    private final AuditService auditService;

    public RabbitMQListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @RabbitListener(queues = "auditQueue")
    public Mono<Void> receiveMessage(@Payload Audit audit) {
        return auditService.logEvent(audit.getUsername(), audit.getAction()).then();
    }
}
