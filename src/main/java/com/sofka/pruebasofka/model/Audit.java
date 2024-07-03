package com.sofka.pruebasofka.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(collection = "audits")
public class Audit implements Serializable {
    @Id
    private String id;
    private String username;
    private String action;
    private LocalDateTime localDateTime;
}
