package com.yiwu.donation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String certificateNo;

    @Column(nullable = false, length = 50)
    private String donorName;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 300)
    private String address;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightKg;

    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DonationStatus status;

    @Column(length = 50)
    private String recyclerName;

    @Column(length = 100)
    private String institutionName;

    @Column(length = 100)
    private String flowDirection;

    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "donation_tracking_records", joinColumns = @JoinColumn(name = "donation_id"))
    @OrderColumn(name = "record_order")
    private List<TrackingRecord> trackingRecords = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCertificateNo() { return certificateNo; }
    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }
    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public String getRecyclerName() { return recyclerName; }
    public void setRecyclerName(String recyclerName) { this.recyclerName = recyclerName; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public String getFlowDirection() { return flowDirection; }
    public void setFlowDirection(String flowDirection) { this.flowDirection = flowDirection; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<TrackingRecord> getTrackingRecords() { return trackingRecords; }
    public void setTrackingRecords(List<TrackingRecord> trackingRecords) { this.trackingRecords = trackingRecords; }
}
