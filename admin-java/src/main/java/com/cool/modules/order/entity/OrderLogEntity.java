package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import org.dromara.autotable.annotation.Index;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单日志
 */
@Getter
@Setter
@Table(value = "order_log", comment = "订单日志")
public class OrderLogEntity extends BaseEntity<OrderLogEntity> {

    @ColumnDefine(comment = "操作类型 0-创建 1-支付 2-发货 3-退款 4-备注更新", defaultValue = "0")
    private Integer operationType;

    @ColumnDefine(comment = "操作内容")
    private String operationContent;

    @ColumnDefine(comment = "操作人员ID")
    private Long operatorId;

    @Index
    @ColumnDefine(comment = "操作时间")
    private Date operationTime;

    @ColumnDefine(comment = "订单ID")
    private Long orderId;
}