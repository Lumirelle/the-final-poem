package com.cool.modules.order.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

/**
 * 订单信息
 */
@Tag(name = "订单信息", description = "订单信息")
@CoolRestController(api = {"page", "info"})
public class AppOrderInfoController extends BaseController<OrderInfoService, OrderInfoEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        // 设置分页查询条件
        setPageOption(createOp()
                .keyWordLikeFields(
                        ORDER_INFO_ENTITY.ORDER_NUMBER,
                        ORDER_INFO_ENTITY.REMARK
                )
                .fieldEq(
                        ORDER_INFO_ENTITY.STATUS,
                        ORDER_INFO_ENTITY.USER_ID,
                        ORDER_INFO_ENTITY.MEAL_ID,
                        ORDER_INFO_ENTITY.PAY_TYPE
                )
                .queryWrapper(QueryWrapper.create().orderBy(ORDER_INFO_ENTITY.CREATE_TIME.desc()))
        );
    }
} 