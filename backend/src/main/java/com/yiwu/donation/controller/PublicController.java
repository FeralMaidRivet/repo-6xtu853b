package com.yiwu.donation.controller;

import com.yiwu.donation.dto.StatsSummary;
import com.yiwu.donation.model.Donation;
import com.yiwu.donation.model.Institution;
import com.yiwu.donation.service.DonationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    private final DonationService donationService;

    public PublicController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/stats")
    public StatsSummary stats() {
        return donationService.stats();
    }

    @GetMapping("/flow")
    public List<Donation> flow() {
        return donationService.publicFlow();
    }

    @GetMapping("/institutions")
    public List<Institution> institutions() {
        return donationService.listInstitutions();
    }
}
