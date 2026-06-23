package com.yiwu.donation.controller;

import com.yiwu.donation.dto.StatusUpdateRequest;
import com.yiwu.donation.model.Donation;
import com.yiwu.donation.model.DonationStatus;
import com.yiwu.donation.service.DonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final DonationService donationService;

    public StaffController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/tasks")
    public List<Donation> tasks() {
        return donationService.listStaffTasks();
    }

    @PatchMapping("/tasks/{id}/accept")
    public Donation accept(@PathVariable Long id,
                           @RequestParam(defaultValue = "回收员") String recyclerName) {
        StatusUpdateRequest request = new StatusUpdateRequest();
        request.setStatus(DonationStatus.ACCEPTED);
        request.setOperator(recyclerName);
        request.setRecyclerName(recyclerName);
        request.setDescription("回收人员已接单，准备按预约时间上门收取");
        return donationService.updateStatus(id, request);
    }

    @PatchMapping("/tasks/{id}/pickup")
    public Donation pickup(@PathVariable Long id,
                           @RequestParam(defaultValue = "回收员") String recyclerName) {
        StatusUpdateRequest request = new StatusUpdateRequest();
        request.setStatus(DonationStatus.PICKED_UP);
        request.setOperator(recyclerName);
        request.setRecyclerName(recyclerName);
        request.setDescription("衣物已完成上门收取，等待进入分拣");
        return donationService.updateStatus(id, request);
    }

    @PatchMapping("/tasks/{id}/handoff")
    public Donation handoff(@PathVariable Long id,
                            @RequestParam(defaultValue = "义乌市爱心公益中心") String institutionName) {
        StatusUpdateRequest request = new StatusUpdateRequest();
        request.setStatus(DonationStatus.HANDED_OVER);
        request.setOperator("物流交接员");
        request.setInstitutionName(institutionName);
        request.setFlowDirection("公益发放");
        request.setDescription("已完成分类打包和物流交接");
        return donationService.updateStatus(id, request);
    }
}
