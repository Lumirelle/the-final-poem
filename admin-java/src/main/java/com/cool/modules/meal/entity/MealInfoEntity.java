package com.cool.modules.meal.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import lombok.Getter;
import lombok.Setter;
import org.dromara.autotable.annotation.Index;

/**
 * 服务套餐表
 */
@Getter
@Setter
@Table(value = "meal_info", comment = "套餐信息表")
public class MealInfoEntity extends BaseEntity<MealInfoEntity> {

    @Index(name = "idx_name")
    @ColumnDefine(comment = "名称")
    private String name;

    @ColumnDefine(comment = "价格")
    private Double price;

    @ColumnDefine(comment = "时长")
    private Integer duration;

    @ColumnDefine(comment = "状态 0-禁用 1-启用", defaultValue = "1")
    private Integer status;

    @ColumnDefine(comment = "分类ID")
    private Long categoryId;

    @Column(ignore = true)
    private String categoryName;

    @ColumnDefine(comment = "简介")
    private String intro;

    @ColumnDefine(comment = "封面图")
    private String cover;

    @ColumnDefine(comment = "服务次数")
    private Integer serviceCount;

    @ColumnDefine(comment = "服务范围", type = "json")
    private String serviceArea;
}
