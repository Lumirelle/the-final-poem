package com.cool.modules.hospital.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import org.dromara.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.UniIndex;
import lombok.Getter;
import lombok.Setter;

/**
 * 排班信息
 */
@Getter
@Setter
@Table(value = "hospital_schedule", comment = "排班信息")
public class ScheduleEntity extends BaseEntity<ScheduleEntity> {

    @Index
    @ColumnDefine(comment = "医生ID（关联医生）")
    private Long doctorId;

    @Index
    @ColumnDefine(comment = "科室ID（关联科室）")
    private Long departmentId;

    @UniIndex
    @ColumnDefine(comment = "排班日期")
    private String scheduleDate;

    @UniIndex
    @ColumnDefine(comment = "时段（示例：08:00-12:00）")
    private String timeSlot;

    @ColumnDefine(comment = "号源总数")
    private Integer totalCapacity;

    @ColumnDefine(comment = "已约数量")
    private Integer bookedCapacity;

    @ColumnDefine(comment = "状态 0-停诊 1-正常", defaultValue = "1")
    private Integer status;
}