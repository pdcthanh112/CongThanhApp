package com.congthanh.authservice.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String user;

    private String action;

    private Instant timestamp;

    private String details;
}
