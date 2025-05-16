package com.cool.modules.order.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.order.entity.OrderDetailEntity;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderDetailService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.order.entity.table.OrderDetailEntityTableDef.ORDER_DETAIL_ENTITY;
import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

/**
 * 订单详情
 */
@Tag(name = "订单详情", description = "订单详情")
@CoolRestController(api = {"page", "info"})
public class AppOrderDetailController extends BaseController<OrderDetailService, OrderDetailEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        // 设置分页查询条件
        setPageOption(createOp()
                .fieldEq(
                        ORDER_DETAIL_ENTITY.ORDER_ID,
                        ORDER_DETAIL_ENTITY.AFTER_SALE_STATUS
                )
                .queryWrapper(QueryWrapper.create()
                        .select(
                                ORDER_DETAIL_ENTITY.ALL_COLUMNS,
                                ORDER_INFO_ENTITY.ORDER_NUMBER,
                                ORDER_INFO_ENTITY.STATUS.as("orderStatus")
                        )
                        .from(ORDER_DETAIL_ENTITY)
                        .leftJoin(ORDER_INFO_ENTITY)
                        .on(OrderDetailEntity::getOrderId, OrderInfoEntity::getId)
                        .orderBy(ORDER_DETAIL_ENTITY.CREATE_TIME.desc())
                )
        );

        // 设置详情查询条件
        setInfoOption(createOp()
                .queryWrapper(QueryWrapper.create()
                        .select(
                                ORDER_DETAIL_ENTITY.ALL_COLUMNS,
                                ORDER_INFO_ENTITY.ORDER_NUMBER,
                                ORDER_INFO_ENTITY.STATUS.as("orderStatus")
                        )
                        .from(ORDER_DETAIL_ENTITY)
                        .leftJoin(ORDER_INFO_ENTITY)
                        .on(OrderDetailEntity::getOrderId, OrderInfoEntity::getId)
                )
        );
    }
} 