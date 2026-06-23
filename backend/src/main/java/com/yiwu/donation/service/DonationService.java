package com.yiwu.donation.service;

import com.yiwu.donation.dto.DonationRequest;
import com.yiwu.donation.dto.StatsSummary;
import com.yiwu.donation.dto.StatusUpdateRequest;
import com.yiwu.donation.model.*;
import com.yiwu.donation.repository.DonationRepository;
import com.yiwu.donation.repository.InstitutionRepository;
import com.yiwu.donation.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private static final DateTimeFormatter CERT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public DonationService(DonationRepository donationRepository,
                           InstitutionRepository institutionRepository,
                           UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (institutionRepository.count() == 0) {
            institutionRepository.saveAll(List.of(
                new Institution(null, "义乌市爱心公益中心", "公益机构", "0579-88886666", "浙江义乌", "儿童冬衣、校服、运动鞋"),
                new Institution(null, "绿色再生资源合作社", "再生机构", "0579-88990011", "浙江金华", "破损衣物、纺织品、旧被褥"),
                new Institution(null, "大山校园互助站", "受助单位", "0579-88661234", "贵州黔南", "校服、书包、保暖衣物")
            ));
        }
        if (donationRepository.count() == 0) {
            Donation d1 = create(seed("王小明", "13800000001", "义乌市稠城街道公益路18号",
                    "秋冬外套", new BigDecimal("12.5"), LocalDateTime.now().plusDays(1), "衣物已清洗打包"));
            updateStatus(d1.getId(), statusReq(DonationStatus.ACCEPTED, "回收员李师傅", "已确认上门时间", "李师傅", null, null));
            updateStatus(d1.getId(), statusReq(DonationStatus.SORTED, "分拣员小周", "按可捐赠和再生分拣完成", null, "义乌市爱心公益中心", "公益发放"));

            Donation d2 = create(seed("陈芳", "13900000002", "义乌市北苑街道保联小区9幢",
                    "旧被褥床品", new BigDecimal("18.0"), LocalDateTime.now().plusDays(2), "适合再生处理"));
            updateStatus(d2.getId(), statusReq(DonationStatus.ACCEPTED, "赵师傅", "已接单", "赵师傅", null, null));
            updateStatus(d2.getId(), statusReq(DonationStatus.PICKED_UP, "赵师傅", "已上门收取", "赵师傅", null, null));
            updateStatus(d2.getId(), statusReq(DonationStatus.RECYCLED, "绿色仓库", "破损织物进入纤维再生流程", "赵师傅", "绿色再生资源合作社", "再生利用"));
        }
    }

    public List<Donation> listDonations() {
        return donationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<Donation> listStaffTasks() {
        return donationRepository.findByStatusInOrderByAppointmentTimeAsc(
                Arrays.asList(DonationStatus.SUBMITTED, DonationStatus.ACCEPTED, DonationStatus.PICKED_UP));
    }

    public Donation getDonation(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("捐赠记录不存在"));
    }

    public Donation getDonationByCertNo(String certNo) {
        return donationRepository.findByCertificateNo(certNo)
                .orElseThrow(() -> new NoSuchElementException("证书编号不存在"));
    }

    public List<Donation> searchDonations(String keyword) {
        return donationRepository.findByDonorNameContainingOrPhoneContaining(keyword, keyword);
    }

    @Transactional
    public Donation create(DonationRequest request) {
        Donation donation = new Donation();
        donation.setCertificateNo("YW-GY-" + LocalDateTime.now().format(CERT_FORMAT) + "-"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        donation.setDonorName(request.getDonorName());
        donation.setPhone(request.getPhone());
        donation.setAddress(request.getAddress());
        donation.setCategory(request.getCategory());
        donation.setWeightKg(request.getWeightKg());
        donation.setAppointmentTime(request.getAppointmentTime());
        donation.setNote(request.getNote());
        donation.setStatus(DonationStatus.SUBMITTED);
        donation.setFlowDirection("待分拣");
        donation.setCreatedAt(LocalDateTime.now());
        donation.getTrackingRecords().add(new TrackingRecord(
                DonationStatus.SUBMITTED, request.getDonorName(),
                "捐赠预约已提交，等待回收人员接单", LocalDateTime.now()));
        return donationRepository.save(donation);
    }

    @Transactional
    public Donation updateStatus(Long id, StatusUpdateRequest request) {
        Donation donation = getDonation(id);
        donation.setStatus(request.getStatus());
        if (StringUtils.hasText(request.getRecyclerName())) {
            donation.setRecyclerName(request.getRecyclerName());
        }
        if (StringUtils.hasText(request.getInstitutionName())) {
            donation.setInstitutionName(request.getInstitutionName());
        }
        if (StringUtils.hasText(request.getFlowDirection())) {
            donation.setFlowDirection(request.getFlowDirection());
        }
        String operator = StringUtils.hasText(request.getOperator()) ? request.getOperator() : "系统管理员";
        String description = StringUtils.hasText(request.getDescription())
                ? request.getDescription() : request.getStatus().getDescription();
        donation.getTrackingRecords().add(new TrackingRecord(request.getStatus(), operator, description, LocalDateTime.now()));
        return donationRepository.save(donation);
    }

    @Transactional
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    public List<Institution> listInstitutions() {
        return institutionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    public Institution addInstitution(Institution institution) {
        institution.setId(null);
        institution.setCreatedAt(LocalDateTime.now());
        if (institution.getEnabled() == null) institution.setEnabled(true);
        return institutionRepository.save(institution);
    }

    @Transactional
    public Institution updateInstitution(Long id, Institution updates) {
        Institution inst = institutionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("机构不存在"));
        if (updates.getName() != null) inst.setName(updates.getName());
        if (updates.getType() != null) inst.setType(updates.getType());
        if (updates.getContact() != null) inst.setContact(updates.getContact());
        if (updates.getRegion() != null) inst.setRegion(updates.getRegion());
        if (updates.getDemand() != null) inst.setDemand(updates.getDemand());
        if (updates.getAddress() != null) inst.setAddress(updates.getAddress());
        if (updates.getEnabled() != null) inst.setEnabled(updates.getEnabled());
        return institutionRepository.save(inst);
    }

    @Transactional
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

    public StatsSummary stats() {
        List<Donation> donations = donationRepository.findAll();
        BigDecimal totalWeight = donations.stream().map(Donation::getWeightKg).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal recycledWeight = donations.stream()
                .filter(d -> d.getStatus() == DonationStatus.RECYCLED)
                .map(Donation::getWeightKg).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal welfareWeight = totalWeight.subtract(recycledWeight);
        long certCount = donations.stream()
                .filter(d -> d.getStatus().ordinal() >= DonationStatus.DELIVERED.ordinal()).count();

        Map<String, Long> statusCounts = new LinkedHashMap<>();
        for (DonationStatus status : DonationStatus.values()) {
            long count = donations.stream().filter(d -> d.getStatus() == status).count();
            statusCounts.put(status.getLabel(), count);
        }

        Map<String, BigDecimal> categoryCounts = donations.stream()
                .collect(Collectors.groupingBy(Donation::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Donation::getWeightKg, BigDecimal::add)));

        StatsSummary summary = new StatsSummary(donations.size(), totalWeight, recycledWeight,
                welfareWeight, certCount, institutionRepository.count(), statusCounts);
        summary.setUserCount(userRepository.count());
        summary.setCollectorCount(userRepository.countByRole(UserRole.COLLECTOR));
        summary.setCategoryCounts(categoryCounts);
        return summary;
    }

    public List<Donation> publicFlow() {
        return donationRepository.findAll().stream()
                .filter(d -> StringUtils.hasText(d.getFlowDirection()))
                .sorted(Comparator.comparing(Donation::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    private DonationRequest seed(String donorName, String phone, String address, String category,
                                 BigDecimal weightKg, LocalDateTime appointmentTime, String note) {
        DonationRequest r = new DonationRequest();
        r.setDonorName(donorName); r.setPhone(phone); r.setAddress(address);
        r.setCategory(category); r.setWeightKg(weightKg);
        r.setAppointmentTime(appointmentTime); r.setNote(note);
        return r;
    }

    private StatusUpdateRequest statusReq(DonationStatus status, String operator, String description,
                                          String recyclerName, String institutionName, String flowDirection) {
        StatusUpdateRequest r = new StatusUpdateRequest();
        r.setStatus(status); r.setOperator(operator); r.setDescription(description);
        r.setRecyclerName(recyclerName); r.setInstitutionName(institutionName); r.setFlowDirection(flowDirection);
        return r;
    }
}
