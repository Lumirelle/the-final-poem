package com.cool.modules.patient.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import com.tangzc.mybatisflex.autotable.annotation.UniIndex;
import lombok.Getter;
import lombok.Setter;
import org.dromara.autotable.annotation.Index;

import java.util.Date;

/**
 * 患者档案
 */
@Getter
@Setter
@Table(value = "patient_profile", comment = "患者档案")
public class PatientProfileEntity extends BaseEntity<PatientProfileEntity> {

    @ColumnDefine(comment = "姓名")
    private String name;

    @ColumnDefine(comment = "性别 0-未知 1-男 2-女", defaultValue = "0")
    private Integer gender;

    @ColumnDefine(comment = "生日")
    private Date birthday;

    @ColumnDefine(comment = "电话")
    private String phone;

    @ColumnDefine(comment = "地址")
    private String address;

    @ColumnDefine(comment = "类型 1-门诊 2-住院 3-急诊", defaultValue = "1")
    private Integer type;

    @UniIndex
    @ColumnDefine(comment = "病历号")
    private String medicalRecordNumber;

    @ColumnDefine(comment = "病史")
    private String medicalHistory;

    @ColumnDefine(comment = "过敏史")
    private String allergyHistory;

    @ColumnDefine(comment = "备注")
    private String remark;

    @ColumnDefine(comment = "身高(cm)")
    private Integer height;

    @ColumnDefine(comment = "体重(kg)")
    private Integer weight;

    @ColumnDefine(comment = "收缩压(mmHg)")
    private Integer systolicPressure;

    @ColumnDefine(comment = "舒张压(mmHg)")
    private Integer diastolicPressure;

    @Index
    @ColumnDefine(comment = "关联用户ID")
    private Long userId;

    @Column(ignore = true)
    private String nickName;

}
