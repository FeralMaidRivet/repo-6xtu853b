package com.yiwu.donation.controller;

import com.yiwu.donation.dto.StatsSummary;
import com.yiwu.donation.dto.StatusUpdateRequest;
import com.yiwu.donation.model.Donation;
import com.yiwu.donation.model.Institution;
import com.yiwu.donation.model.User;
import com.yiwu.donation.service.DonationService;
import com.yiwu.donation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final DonationService donationService;
    private final UserService userService;

    public AdminController(DonationService donationService, UserService userService) {
        this.donationService = donationService;
        this.userService = userService;
    }

    @GetMapping("/stats")
    public StatsSummary stats() {
        return donationService.stats();
    }

    @GetMapping("/donations")
    public List<Donation> donations() {
        return donationService.listDonations();
    }

    @PatchMapping("/donations/{id}/status")
    public Donation updateDonationStatus(@PathVariable Long id,
                                         @Valid @RequestBody StatusUpdateRequest request) {
        return donationService.updateStatus(id, request);
    }

    @DeleteMapping("/donations/{id}")
    public Map<String, Object> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return Map.of("ok", true);
    }

    @GetMapping("/institutions")
    public List<Institution> institutions() {
        return donationService.listInstitutions();
    }

    @PostMapping("/institutions")
    public Institution addInstitution(@Valid @RequestBody Institution institution) {
        return donationService.addInstitution(institution);
    }

    @PutMapping("/institutions/{id}")
    public Institution updateInstitution(@PathVariable Long id, @RequestBody Institution institution) {
        return donationService.updateInstitution(id, institution);
    }

    @DeleteMapping("/institutions/{id}")
    public Map<String, Object> deleteInstitution(@PathVariable Long id) {
        donationService.deleteInstitution(id);
        return Map.of("ok", true);
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.listUsers();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Map.of("ok", true);
    }

    @GetMapping("/flow")
    public List<Donation> flow() {
        return donationService.publicFlow();
    }
}
