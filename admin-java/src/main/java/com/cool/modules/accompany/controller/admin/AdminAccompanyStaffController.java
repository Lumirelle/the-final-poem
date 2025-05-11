package com.cool.modules.accompany.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.service.AccompanyStaffService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY;

/**
 * 陪诊员信息
 */
@Tag(name = "陪诊员管理", description = "陪诊员信息管理")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminAccompanyStaffController extends BaseController<AccompanyStaffService, AccompanyStaffEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
                createOp()
                        .keyWordLikeFields(
                                ACCOMPANY_STAFF_ENTITY.NAME,
                                ACCOMPANY_STAFF_ENTITY.PHONE
                        )
                        .fieldEq(ACCOMPANY_STAFF_ENTITY.STATUS)
        );
    }
}
