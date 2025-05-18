package com.cool.modules.order.controller.app;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

/**
 * 订单信息
 */
@Tag(name = "订单信息", description = "订单信息")
@CoolRestController(api = {"page", "info", "update", "add"})
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
                ORDER_INFO_ENTITY.PAY_USER_ID,
                ORDER_INFO_ENTITY.MEAL_ID,
                ORDER_INFO_ENTITY.PAY_TYPE
            )
        );
    }

    @Override
    @PostMapping("/update")
    protected R update(@RequestBody OrderInfoEntity orderInfoEntity, @RequestAttribute() JSONObject requestParams) {
        Long id = orderInfoEntity.getId();
        JSONObject info = JSONUtil.parseObj(JSONUtil.toJsonStr(service.getById(id)));
        requestParams.forEach(info::set);
        info.set("updateTime", new Date());
        info.set("payTime", new Date());
        service.update(requestParams, JSONUtil.toBean(info, currentEntityClass()));
        return R.ok();
    }
} 
