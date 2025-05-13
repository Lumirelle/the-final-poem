package com.cool.modules.order.entity;

import com.cool.core.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.tangzc.mybatisflex.autotable.annotation.ColumnDefine;
import com.tangzc.mybatisflex.autotable.annotation.UniIndex;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单信息
 */
@Getter
@Setter
@Table(value = "order_info", comment = "订单信息")
public class OrderInfoEntity extends BaseEntity<OrderInfoEntity> {

    @UniIndex
    @ColumnDefine(comment = "编号")
    private String orderNumber;

    @ColumnDefine(comment = "状态 0-待支付 1-已支付 2-配送中 3-已完成 4-已取消 5-退款中", defaultValue = "0")
    private Integer status;

    @ColumnDefine(comment = "总金额")
    private Double totalAmount;

    @ColumnDefine(comment = "用户ID")
    private Long userId;

    @ColumnDefine(comment = "支付方式 0-微信 1-支付宝 2-银行卡", defaultValue = "0")
    private Integer payType;

    @ColumnDefine(comment = "收货地址")
    private String address;

    @ColumnDefine(comment = "备注")
    private String remark;
}