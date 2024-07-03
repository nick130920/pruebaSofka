package com.sofka.pruebasofka.repository;

import com.sofka.pruebasofka.model.Audit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends ReactiveMongoRepository<Audit, String> {
}
