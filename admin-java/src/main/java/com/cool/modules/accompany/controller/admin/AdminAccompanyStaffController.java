package com.cool.modules.accompany.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.accompany.entity.AccompanyReviewEntity;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef;
import com.cool.modules.accompany.service.AccompanyReviewService;
import com.cool.modules.accompany.service.AccompanyStaffService;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.entity.table.UserInfoEntityTableDef;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Date;

import static com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY;

/**
 * 陪诊员信息
 */
@Tag(name = "陪诊员管理", description = "陪诊员信息管理")
@CoolRestController(api = {"add", "delete", "update", "info", "page", "doreview"})
@RequiredArgsConstructor
public class AdminAccompanyStaffController extends BaseController<AccompanyStaffService, AccompanyStaffEntity> {

    private final AccompanyReviewService reviewService;

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(
                createOp()
                        .fieldEq(
                                ACCOMPANY_STAFF_ENTITY.ID,
                                ACCOMPANY_STAFF_ENTITY.STATUS,
                                ACCOMPANY_STAFF_ENTITY.GENDER,
                                ACCOMPANY_STAFF_ENTITY.LEVEL
                        )
                        .keyWordLikeFields(
                                ACCOMPANY_STAFF_ENTITY.NAME,
                                ACCOMPANY_STAFF_ENTITY.PHONE
                        )
                        .queryWrapper(QueryWrapper.create()
                                .select(
                                        AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY.ALL_COLUMNS,
                                        UserInfoEntityTableDef.USER_INFO_ENTITY.NICK_NAME.as("nickName")
                                )
                                .from(AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY)
                                .leftJoin(UserInfoEntityTableDef.USER_INFO_ENTITY)
                                .on(AccompanyStaffEntity::getUserId, UserInfoEntity::getId))
        );
    }

    @Operation(summary = "审核", description = "审核陪诊员资质，修改陪诊员级别")
    @PostMapping("/doreview")
    public R review(@RequestAttribute() JSONObject requestParams) {
        String body = requestParams.getStr("body");
        if (StrUtil.isBlank(body)) {
            return R.error("参数错误");
        }

        // 保存审核记录
        reviewService.add(requestParams, JSONUtil.toBean(body, AccompanyReviewEntity.class));

        // 更新陪诊员信息
        Long id = requestParams.getLong("staffId");
        JSONObject info = JSONUtil.parseObj(JSONUtil.toJsonStr(service.getById(id)));
        info.set("id", id);
        info.set("level", requestParams.getStr("level"));
        info.set("updateTime", new Date());
        service.update(requestParams, JSONUtil.toBean(info, currentEntityClass()));
        return R.ok();
    }

}
