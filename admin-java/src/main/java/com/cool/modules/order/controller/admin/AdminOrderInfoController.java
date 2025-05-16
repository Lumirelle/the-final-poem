package com.cool.modules.order.controller.admin;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

import org.springframework.web.bind.annotation.GetMapping;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.query.QueryWrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 订单信息管理
 */
@Tag(name = "订单信息管理", description = "管理订单信息")
@CoolRestController(api = {"add", "update", "info", "page", "countPayOrder"})
public class AdminOrderInfoController extends BaseController<OrderInfoService, OrderInfoEntity> {

    // 如果传递了 payOrderOnly 参数，则仅分页展示支付的订单
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {

        boolean payOrderOnly = requestParams.getBool("payOrderOnly", false);

        setPageOption(
            createOp()
                .keyWordLikeFields(
                    ORDER_INFO_ENTITY.ORDER_NUMBER
                )
                // NOTE: UserId 是保留字段，请求时默认传递发起请求用户的 ID，因此本实体类中需要用 payUserId
                .fieldEq(
                    ORDER_INFO_ENTITY.PAY_USER_ID,
                    ORDER_INFO_ENTITY.STATUS,
                    ORDER_INFO_ENTITY.PAY_TYPE,
                    ORDER_INFO_ENTITY.ID
                )
                .queryWrapper(QueryWrapper.create()
                    .where(ORDER_INFO_ENTITY.STATUS.in(1, 2, 3).when(payOrderOnly))
                )
        );
    }

    @Operation(summary = "统计", description = "统计付款订单总数")
    @GetMapping("/countPayOrder")
    public R<Long> countPayOrder() {
        return R.ok(service.countPayOrder());
    }

}
