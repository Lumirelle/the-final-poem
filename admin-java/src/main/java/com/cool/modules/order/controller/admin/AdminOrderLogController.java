package com.cool.modules.order.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.order.entity.OrderLogEntity;
import com.cool.modules.order.service.OrderLogService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 订单日志管理
 */
@Tag(name = "订单日志管理", description = "管理订单日志")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminOrderLogController extends BaseController<OrderLogService, OrderLogEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

}
