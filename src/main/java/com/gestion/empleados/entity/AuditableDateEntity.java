package com.gestion.empleados.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AuditableDateEntity {
    @Column(name="created_date")
    @CreatedDate
    private LocalDateTime createdDate;
    @Column(name="modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
