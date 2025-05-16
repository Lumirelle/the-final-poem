package com.cool.modules.feedback.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.service.ComplaintService;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.base.entity.sys.BaseSysUserEntity;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;
import static com.cool.modules.base.entity.sys.table.BaseSysUserEntityTableDef.BASE_SYS_USER_ENTITY;

/**
 * 投诉信息
 */
@Tag(name = "投诉信息", description = "投诉信息")
@CoolRestController(api = {"page", "info", "save"})
public class AppComplaintController extends BaseController<ComplaintService, ComplaintEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        // 设置分页查询条件
        setPageOption(createOp()
                .keyWordLikeFields(
                        COMPLAINT_ENTITY.CONTENT,
                        COMPLAINT_ENTITY.HANDLE_RESULT,
                        COMPLAINT_ENTITY.REMARK
                )
                .fieldEq(
                        COMPLAINT_ENTITY.TYPE,
                        COMPLAINT_ENTITY.STATUS,
                        COMPLAINT_ENTITY.USER_ID,
                        COMPLAINT_ENTITY.ORDER_ID
                )
                .queryWrapper(QueryWrapper.create()
                        .select(
                                COMPLAINT_ENTITY.ALL_COLUMNS,
                                USER_INFO_ENTITY.NICK_NAME.as("userNickName"),
                                BASE_SYS_USER_ENTITY.NICK_NAME.as("handlerNickName")
                        )
                        .from(COMPLAINT_ENTITY)
                        .leftJoin(USER_INFO_ENTITY)
                        .on(ComplaintEntity::getUserId, UserInfoEntity::getId)
                        .leftJoin(BASE_SYS_USER_ENTITY)
                        .on(ComplaintEntity::getHandlerId, BaseSysUserEntity::getId)
                        .orderBy(COMPLAINT_ENTITY.CREATE_TIME.desc())
                )
        );

        // 设置详情查询条件
        setInfoOption(createOp()
                .queryWrapper(QueryWrapper.create()
                        .select(
                                COMPLAINT_ENTITY.ALL_COLUMNS,
                                USER_INFO_ENTITY.NICK_NAME.as("userNickName"),
                                BASE_SYS_USER_ENTITY.NICK_NAME.as("handlerNickName")
                        )
                        .from(COMPLAINT_ENTITY)
                        .leftJoin(USER_INFO_ENTITY)
                        .on(ComplaintEntity::getUserId, UserInfoEntity::getId)
                        .leftJoin(BASE_SYS_USER_ENTITY)
                        .on(ComplaintEntity::getHandlerId, BaseSysUserEntity::getId)
                )
        );
    }
}