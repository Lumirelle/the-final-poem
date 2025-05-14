package com.cool.modules.feedback.controller.admin;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;

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
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminComplaintController extends BaseController<ComplaintService, ComplaintEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
                .keyWordLikeFields(COMPLAINT_ENTITY.ORDER_NO)
                .fieldEq(COMPLAINT_ENTITY.TYPE, 
                        COMPLAINT_ENTITY.STATUS,
                        COMPLAINT_ENTITY.USER_ID,
                        COMPLAINT_ENTITY.HANDLER_ID));
    }

    @Operation(summary = "处理投诉")
    @PostMapping("/handle")
    public R handle(@RequestAttribute JSONObject requestParams) {
        Long id = requestParams.getLong("id");
        Long handlerId = requestParams.getLong("handlerId");
        String handleResult = requestParams.getStr("handleResult");
        String remark = requestParams.getStr("remark");
        return R.ok(service.handleComplaint(id, handlerId, handleResult, remark));
    }
}