package com.cool.modules.accompany.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.modules.accompany.entity.AccompanyReviewEntity;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.service.AccompanyReviewService;
import com.cool.modules.accompany.service.AccompanyStaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Date;

import static com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY;

/**
 * 陪诊员信息
 */
@Tag(name = "陪诊员管理", description = "陪诊员信息管理")
@CoolRestController(api = {"add", "delete", "update", "info", "page", "doreview"})
@AllArgsConstructor
public class AdminAccompanyStaffController extends BaseController<AccompanyStaffService, AccompanyStaffEntity> {

    private AccompanyReviewService reviewService;

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
