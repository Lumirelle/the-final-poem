package com.cool.modules.accompany.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;
import org.dromara.autotable.annotation.Index;

import java.util.Date;

/**
 * 排班信息
 */
@Getter
@Setter
@Table(value = "accompany_schedule", comment = "排班信息")
public class AccompanyScheduleEntity extends BaseEntity<AccompanyScheduleEntity> {

    @ColumnDefine(comment = "日期")
    private Date scheduleDate;

    @ColumnDefine(comment = "时段 [0,1,2] JSON数组", type = "json")
    private String timeSlots;

    @Index
    @ColumnDefine(comment = "陪诊员ID")
    private Long staffId;

    @ColumnDefine(comment = "陪诊员姓名")
    private String staffName;

    @ColumnDefine(comment = "陪诊员电话")
    private String staffPhone;

    @ColumnDefine(comment = "陪诊员级别 0-初级 1-中级 2-高级")
    private Integer staffLevel;

    @ColumnDefine(comment = "状态 0-可预约 1-已约满 2-停诊", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "操作人ID")
    private Long operatorId;
}
