package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单统计
 */
@Getter
@Setter
@Table(value = "order_statistics", comment = "订单统计")
public class OrderStatisticsEntity extends BaseEntity<OrderStatisticsEntity> {

    @ColumnDefine(comment = "订单总数")
    private Integer totalOrders;

    @ColumnDefine(comment = "总金额")
    private Double totalAmount;

    @ColumnDefine(comment = "退款数")
    private Integer refundCount;

    @ColumnDefine(comment = "完成数")
    private Integer completedCount;

    @ColumnDefine(comment = "取消数")
    private Integer cancelledCount;

    @ColumnDefine(comment = "支付订单数")
    private Integer paidOrders;

    @ColumnDefine(comment = "配送订单数")
    private Integer deliveringOrders;

    @ColumnDefine(comment = "统计日期")
    private Date statisticsDate;
}