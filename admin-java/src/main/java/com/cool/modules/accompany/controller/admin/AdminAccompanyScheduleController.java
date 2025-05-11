package com.cool.modules.accompany.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.accompany.entity.AccompanyScheduleEntity;
import com.cool.modules.accompany.service.AccompanyScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.accompany.entity.table.AccompanyScheduleEntityTableDef.ACCOMPANY_SCHEDULE_ENTITY;

/**
 * 排班信息
 */
@Tag(name = "排班管理", description = "排班信息管理")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminAccompanyScheduleController extends BaseController<AccompanyScheduleService, AccompanyScheduleEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().fieldEq(
                        ACCOMPANY_SCHEDULE_ENTITY.STATUS,
                        ACCOMPANY_SCHEDULE_ENTITY.TIME_SLOTS
                )
                .keyWordLikeFields(ACCOMPANY_SCHEDULE_ENTITY.STAFF_NAME));
    }

}
