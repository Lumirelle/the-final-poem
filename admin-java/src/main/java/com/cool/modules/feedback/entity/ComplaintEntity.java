package com.cool.modules.feedback.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import org.dromara.autotable.annotation.Index;
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
    @ColumnDefine(comment = "单号")
    private String orderNo;

    @ColumnDefine(comment = "类型 0=服务态度 1=价格问题 2=服务质量 3=其他", defaultValue = "0")
    private Integer type;

    @ColumnDefine(comment = "状态 0=待处理 1=处理中 2=已解决 3=已关闭", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "内容")
    private String content;

    @ColumnDefine(comment = "用户ID")
    private Long userId;

    @ColumnDefine(comment = "处理人ID")
    private Long handlerId;

    @ColumnDefine(comment = "处理结果")
    private String handleResult;

    @ColumnDefine(comment = "备注")
    private String remark;

    @Index
    @ColumnDefine(comment = "创建时间")
    private java.util.Date createTime;

    @ColumnDefine(comment = "处理时间")
    private java.util.Date handleTime;

    @Column(ignore = true)
    private String nickName;

    @Column(ignore = true)
    private String handlerName;
}