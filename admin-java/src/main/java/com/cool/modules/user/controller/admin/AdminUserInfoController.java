package com.cool.modules.user.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

@Tag(name = "用户信息", description = "用户信息")
@CoolRestController(api = {"add", "delete", "update", "page", "list", "info"})
public class AdminUserInfoController extends BaseController<UserInfoService, UserInfoEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp().fieldEq(
                        USER_INFO_ENTITY.ID,
                        USER_INFO_ENTITY.STATUS,
                        USER_INFO_ENTITY.GENDER,
                        USER_INFO_ENTITY.LOGIN_TYPE,
                        USER_INFO_ENTITY.ROLE
                )
                .keyWordLikeFields(
                        USER_INFO_ENTITY.NICK_NAME,
                        USER_INFO_ENTITY.PHONE
                ));
    }
}
