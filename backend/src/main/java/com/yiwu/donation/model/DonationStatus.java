package com.yiwu.donation.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DonationStatus {
    SUBMITTED("已提交", "捐赠者提交了上门回收预约"),
    ACCEPTED("已接单", "回收人员已接单并准备上门"),
    PICKED_UP("已上门收取", "衣物已完成上门收取"),
    SORTED("已分拣", "衣物已分类打包进入分拣仓"),
    HANDED_OVER("已交接", "衣物已完成物流交接"),
    DELIVERED("已送达", "物资已送达合作机构"),
    RECYCLED("再生利用", "不可直接捐赠的衣物已进入再生处理"),
    CERTIFIED("已出证", "系统已生成捐赠证明");

    private final String label;
    private final String description;

    DonationStatus(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getName() { return name(); }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
}
