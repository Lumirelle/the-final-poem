package com.cool.modules.feedback.controller.admin;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.service.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 投诉信息管理
 */
@Tag(name = "投诉信息管理", description = "管理投诉信息")
@CoolRestController(api = {"add", "update", "info", "page"})
public class AdminComplaintController extends BaseController<ComplaintService, ComplaintEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
                .fieldEq(
                  COMPLAINT_ENTITY.ORDER_ID,
                  COMPLAINT_ENTITY.TYPE,
                  COMPLAINT_ENTITY.STATUS,
                  COMPLAINT_ENTITY.USER_ID,
                  COMPLAINT_ENTITY.HANDLER_ID
                  ));
    }
}