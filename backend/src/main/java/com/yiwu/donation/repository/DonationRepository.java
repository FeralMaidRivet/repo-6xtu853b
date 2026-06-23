package com.yiwu.donation.repository;

import com.yiwu.donation.model.Donation;
import com.yiwu.donation.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByStatusInOrderByAppointmentTimeAsc(List<DonationStatus> statuses);
    List<Donation> findByDonorNameContainingOrPhoneContaining(String name, String phone);
    Optional<Donation> findByCertificateNo(String certificateNo);
    long countByStatus(DonationStatus status);
}
