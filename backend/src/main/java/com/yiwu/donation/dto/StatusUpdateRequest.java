package com.yiwu.donation.dto;

import com.yiwu.donation.model.DonationStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {
    @NotNull private DonationStatus status;
    private String operator;
    private String description;
    private String recyclerName;
    private String institutionName;
    private String flowDirection;

    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRecyclerName() { return recyclerName; }
    public void setRecyclerName(String recyclerName) { this.recyclerName = recyclerName; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public String getFlowDirection() { return flowDirection; }
    public void setFlowDirection(String flowDirection) { this.flowDirection = flowDirection; }
}
