package com.yiwu.donation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class TrackingRecord {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DonationStatus status;

    @Column(length = 50)
    private String operator;

    @Column(length = 500)
    private String description;

    private LocalDateTime createdAt;

    public TrackingRecord() {}

    public TrackingRecord(DonationStatus status, String operator, String description, LocalDateTime createdAt) {
        this.status = status;
        this.operator = operator;
        this.description = description;
        this.createdAt = createdAt;
    }

    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
