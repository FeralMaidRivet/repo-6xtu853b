package com.yiwu.donation.dto;

import java.math.BigDecimal;
import java.util.Map;

public class StatsSummary {
    private long totalDonations;
    private BigDecimal totalWeightKg;
    private BigDecimal recycledWeightKg;
    private BigDecimal publicWelfareWeightKg;
    private long certifiedCount;
    private long institutionCount;
    private long userCount;
    private long collectorCount;
    private Map<String, Long> statusCounts;
    private Map<String, BigDecimal> categoryCounts;

    public StatsSummary() {}

    public StatsSummary(long totalDonations, BigDecimal totalWeightKg, BigDecimal recycledWeightKg,
                        BigDecimal publicWelfareWeightKg, long certifiedCount, long institutionCount,
                        Map<String, Long> statusCounts) {
        this.totalDonations = totalDonations;
        this.totalWeightKg = totalWeightKg;
        this.recycledWeightKg = recycledWeightKg;
        this.publicWelfareWeightKg = publicWelfareWeightKg;
        this.certifiedCount = certifiedCount;
        this.institutionCount = institutionCount;
        this.statusCounts = statusCounts;
    }

    public long getTotalDonations() { return totalDonations; }
    public void setTotalDonations(long totalDonations) { this.totalDonations = totalDonations; }
    public BigDecimal getTotalWeightKg() { return totalWeightKg; }
    public void setTotalWeightKg(BigDecimal totalWeightKg) { this.totalWeightKg = totalWeightKg; }
    public BigDecimal getRecycledWeightKg() { return recycledWeightKg; }
    public void setRecycledWeightKg(BigDecimal recycledWeightKg) { this.recycledWeightKg = recycledWeightKg; }
    public BigDecimal getPublicWelfareWeightKg() { return publicWelfareWeightKg; }
    public void setPublicWelfareWeightKg(BigDecimal publicWelfareWeightKg) { this.publicWelfareWeightKg = publicWelfareWeightKg; }
    public long getCertifiedCount() { return certifiedCount; }
    public void setCertifiedCount(long certifiedCount) { this.certifiedCount = certifiedCount; }
    public long getInstitutionCount() { return institutionCount; }
    public void setInstitutionCount(long institutionCount) { this.institutionCount = institutionCount; }
    public long getUserCount() { return userCount; }
    public void setUserCount(long userCount) { this.userCount = userCount; }
    public long getCollectorCount() { return collectorCount; }
    public void setCollectorCount(long collectorCount) { this.collectorCount = collectorCount; }
    public Map<String, Long> getStatusCounts() { return statusCounts; }
    public void setStatusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; }
    public Map<String, BigDecimal> getCategoryCounts() { return categoryCounts; }
    public void setCategoryCounts(Map<String, BigDecimal> categoryCounts) { this.categoryCounts = categoryCounts; }
}
