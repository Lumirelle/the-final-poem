package com.cool.modules.order.controller.admin;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 订单信息管理
 */
@Tag(name = "订单信息管理", description = "管理订单信息")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminOrderInfoController extends BaseController<OrderInfoService, OrderInfoEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
            createOp()
                .keyWordLikeFields(ORDER_INFO_ENTITY.ORDER_NUMBER)
                .fieldEq(ORDER_INFO_ENTITY.USER_ID, ORDER_INFO_ENTITY.STATUS, ORDER_INFO_ENTITY.PAY_TYPE)
        );
    }

}
