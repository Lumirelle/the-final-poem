package com.cool.modules.feedback.controller.app;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.service.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * APP投诉信息接口
 */
@Tag(name = "APP投诉信息接口", description = "APP投诉信息接口")
@CoolRestController(api = {"add", "list", "page", "info"})
public class AppComplaintController extends BaseController<ComplaintService, ComplaintEntity> {
    
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
                .fieldEq(
                  COMPLAINT_ENTITY.ORDER_ID, 
                  COMPLAINT_ENTITY.TYPE, 
                  COMPLAINT_ENTITY.STATUS,
                  COMPLAINT_ENTITY.USER_ID));
    }
    
}