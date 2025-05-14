package com.cool.modules.feedback.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import com.tangzc.mybatisflex.autotable.annotation.UniIndex;
import lombok.Getter;
import lombok.Setter;

/**
 * 投诉信息
 */
@Getter
@Setter
@Table(value = "feedback_complaint", comment = "投诉信息")
public class ComplaintEntity extends BaseEntity<ComplaintEntity> {

    @UniIndex
    @ColumnDefine(comment = "订单 ID")
    private String orderId;

    @ColumnDefine(comment = "类型 0=服务态度 1=价格问题 2=服务质量 3=其他", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "状态 0=待处理 1=处理中 2=已解决 3=未解决 4=已关闭", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "内容")
    private String content;

    @ColumnDefine(comment = "用户ID")
    private Long userId;

    @Column(ignore = true)
    private String userNickName;

    @ColumnDefine(comment = "处理人ID")
    private Long handlerId;

    @Column(ignore = true)
    private String handlerNickName;

    @ColumnDefine(comment = "处理结果")
    private String handleResult;

    @ColumnDefine(comment = "备注")
    private String remark;

}