package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import org.dromara.autotable.annotation.Index;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单详情
 */
@Getter
@Setter
@Table(value = "order_detail", comment = "订单详情")
public class OrderDetailEntity extends BaseEntity<OrderDetailEntity> {

    @ColumnDefine(comment = "商品ID")
    private Long goodsId;

    @ColumnDefine(comment = "数量")
    private Integer quantity;

    @ColumnDefine(comment = "单价")
    private Double price;

    @ColumnDefine(comment = "总价")
    private Double totalPrice;

    @ColumnDefine(comment = "优惠金额")
    private Double discountAmount;

    @ColumnDefine(comment = "实付金额")
    private Double actualAmount;

    @ColumnDefine(comment = "物流单号")
    private String logisticsNumber;

    @ColumnDefine(comment = "售后状态 0-无售后 1-申请中 2-处理完成", defaultValue = "0")
    private Integer afterSaleStatus;

    @Index
    @ColumnDefine(comment = "订单ID")
    private Long orderId;
}