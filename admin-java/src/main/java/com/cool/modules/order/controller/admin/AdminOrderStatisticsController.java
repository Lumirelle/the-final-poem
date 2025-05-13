package com.cool.modules.order.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.order.entity.OrderStatisticsEntity;
import com.cool.modules.order.service.OrderStatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 订单统计管理
 */
@Tag(name = "订单统计管理", description = "管理订单统计")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminOrderStatisticsController extends
    BaseController<OrderStatisticsService, OrderStatisticsEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

}
