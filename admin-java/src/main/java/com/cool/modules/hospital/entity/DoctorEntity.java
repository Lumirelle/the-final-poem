package com.cool.modules.hospital.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import org.dromara.autotable.annotation.Index;
import com.tangzc.mybatisflex.autotable.annotation.UniIndex;
import lombok.Getter;
import lombok.Setter;

/**
 * 医生信息
 */
@Getter
@Setter
@Table(value = "hospital_doctor", comment = "医生信息")
public class DoctorEntity extends BaseEntity<DoctorEntity> {

    @ColumnDefine(comment = "姓名")
    private String name;

    @UniIndex
    @ColumnDefine(comment = "工号")
    private String jobCode;

    @ColumnDefine(comment = "职称")
    private String title;

    @Index
    @ColumnDefine(comment = "科室ID（关联科室）")
    private Long departmentId;

    @ColumnDefine(comment = "专长")
    private String specialty;

    @ColumnDefine(comment = "状态 0-启用 1-停用", defaultValue = "0")
    private Integer status;
}
