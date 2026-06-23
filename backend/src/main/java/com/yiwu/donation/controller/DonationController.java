package com.yiwu.donation.controller;

import com.yiwu.donation.dto.DonationRequest;
import com.yiwu.donation.dto.StatusUpdateRequest;
import com.yiwu.donation.model.Donation;
import com.yiwu.donation.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true, "system", "衣物捐赠系统");
    }

    @GetMapping("/donations")
    public List<Donation> list() {
        return donationService.listDonations();
    }

    @PostMapping("/donations")
    public Donation create(@Valid @RequestBody DonationRequest request) {
        return donationService.create(request);
    }

    @GetMapping("/donations/{id}")
    public Donation detail(@PathVariable Long id) {
        return donationService.getDonation(id);
    }

    @GetMapping("/donations/cert/{certNo}")
    public Donation detailByCert(@PathVariable String certNo) {
        return donationService.getDonationByCertNo(certNo);
    }

    @GetMapping("/donations/search")
    public List<Donation> search(@RequestParam String keyword) {
        return donationService.searchDonations(keyword);
    }

    @PatchMapping("/donations/{id}/status")
    public Donation updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return donationService.updateStatus(id, request);
    }
}
