package com.yiwu.donation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "institutions")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String contact;

    @Column(length = 100)
    private String region;

    @Column(length = 300)
    private String demand;

    @Column(length = 200)
    private String address;

    private Boolean enabled = true;

    private LocalDateTime createdAt;

    public Institution() {}

    public Institution(Long id, String name, String type, String contact, String region, String demand) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.contact = contact;
        this.region = region;
        this.demand = demand;
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getDemand() { return demand; }
    public void setDemand(String demand) { this.demand = demand; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
