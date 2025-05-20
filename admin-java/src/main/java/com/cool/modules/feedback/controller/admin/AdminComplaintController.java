package com.cool.modules.feedback.controller.admin;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;

import java.util.List;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.cool.core.util.CoolSecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.service.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * 投诉信息管理
 */
@Tag(name = "投诉信息管理", description = "管理投诉信息")
@CoolRestController(api = {"update", "info", "page"})
public class AdminComplaintController extends BaseController<ComplaintService, ComplaintEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
            .fieldEq(
                COMPLAINT_ENTITY.ORDER_ID,
                COMPLAINT_ENTITY.TYPE,
                COMPLAINT_ENTITY.STATUS,
                COMPLAINT_ENTITY.COMPLAINT_USER_ID,
                COMPLAINT_ENTITY.HANDLER_ID
            ));
    }

    @Override
    @Operation(summary = "新增", description = "新增信息，对应后端的实体类")
    @PostMapping("/add")
    protected R add(@RequestAttribute() JSONObject requestParams) {
        ComplaintEntity entity = requestParams.toBean(currentEntityClass());
        entity.setHandlerId(CoolSecurityUtil.getCurrentUserId());
        return R.ok(Dict.create().set("id", service.add(requestParams, entity)));
    }

    @Operation(summary = "统计", description = "统计投诉用户总量")
    @GetMapping("/countUser")
    public R<Long> count() {
        return R.ok(service.count());
    }

    @Operation(summary = "统计", description = "统计本年度每月的投诉数量")
    @GetMapping("/countThisYear")
    public R<List<Long>> countThisYear() {
        return R.ok(service.countThisYear());
    }

}
