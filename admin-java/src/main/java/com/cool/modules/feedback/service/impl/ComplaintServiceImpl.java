package com.cool.modules.feedback.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.mapper.ComplaintMapper;
import com.cool.modules.feedback.service.ComplaintService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

/**
 * 投诉信息服务实现类
 */
@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl extends BaseServiceImpl<ComplaintMapper, ComplaintEntity> implements
    ComplaintService {

    @Override
    public Object page(JSONObject requestParams, Page<ComplaintEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
            COMPLAINT_ENTITY.ALL_COLUMNS,
            USER_INFO_ENTITY.as("userInfo").NICK_NAME.as("userNickName"),
            USER_INFO_ENTITY.as("handlerInfo").NICK_NAME.as("handlerNickName")
        )
        .from(COMPLAINT_ENTITY)
        .leftJoin(USER_INFO_ENTITY).as("userInfo")
        .on(USER_INFO_ENTITY.as("userInfo").ID.eq(COMPLAINT_ENTITY.USER_ID))
        .leftJoin(USER_INFO_ENTITY).as("handlerInfo")
        .on(USER_INFO_ENTITY.as("handlerInfo").ID.eq(COMPLAINT_ENTITY.HANDLER_ID));
        return super.page(requestParams, page, queryWrapper);
    }

    @Override
    public Long add(ComplaintEntity entity) {
        entity.setHandlerId(CoolSecurityUtil.getCurrentUserId());
        return super.add(entity);
    }
}