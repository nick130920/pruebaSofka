package com.sofka.pruebasofka.service;

import com.sofka.pruebasofka.model.Audit;
import com.sofka.pruebasofka.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public Mono<Audit> logEvent(String username, String action) {
        Audit audit = new Audit();
        audit.setUsername(username);
        audit.setAction(action);
        audit.setLocalDateTime(LocalDateTime.now());
        return auditRepository.save(audit);
    }
}
