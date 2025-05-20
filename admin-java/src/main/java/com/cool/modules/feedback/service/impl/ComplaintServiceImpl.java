package com.cool.modules.feedback.service.impl;

import cn.hutool.core.date.DateUtil;
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

import static com.cool.modules.base.entity.sys.table.BaseSysUserEntityTableDef.BASE_SYS_USER_ENTITY;
import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

import java.util.ArrayList;
import java.util.List;

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
                USER_INFO_ENTITY.NICK_NAME.as("userNickName"),
                BASE_SYS_USER_ENTITY.NICK_NAME.as("handlerNickName")
            )
            .from(COMPLAINT_ENTITY)
            .leftJoin(USER_INFO_ENTITY).on(USER_INFO_ENTITY.ID.eq(COMPLAINT_ENTITY.COMPLAINT_USER_ID))
            .leftJoin(BASE_SYS_USER_ENTITY).on(BASE_SYS_USER_ENTITY.ID.eq(COMPLAINT_ENTITY.HANDLER_ID));
        return mapper.paginateWithRelations(page, queryWrapper);
    }

    @Override
    public Object info(Long id) {
        QueryWrapper queryWrapper = QueryWrapper
            .create()
            .select(
                COMPLAINT_ENTITY.ALL_COLUMNS,
                USER_INFO_ENTITY.NICK_NAME.as("userNickName"),
                BASE_SYS_USER_ENTITY.NICK_NAME.as("handlerNickName")
            )
            .from(COMPLAINT_ENTITY)
            .leftJoin(USER_INFO_ENTITY).on(USER_INFO_ENTITY.ID.eq(COMPLAINT_ENTITY.COMPLAINT_USER_ID))
            .leftJoin(BASE_SYS_USER_ENTITY).on(BASE_SYS_USER_ENTITY.ID.eq(COMPLAINT_ENTITY.HANDLER_ID))
            .where(COMPLAINT_ENTITY.ID.eq(id));
        return mapper.selectOneWithRelationsByQuery(queryWrapper);
    }

    @Override
    public List<Long> countThisYear() {
        List<Long> list = new ArrayList<>();
        int currentMonth = DateUtil.month(DateUtil.date());
        // 统计每个月的投诉数量
        for (int i = 0; i < 12; i++) {
            list.add(count(QueryWrapper.create()
                .from(COMPLAINT_ENTITY)
                .where(COMPLAINT_ENTITY.CREATE_TIME.ge(DateUtil.offsetMonth(DateUtil.date(), -currentMonth + i))
                    .and(COMPLAINT_ENTITY.CREATE_TIME.lt(DateUtil.offsetMonth(DateUtil.date(), -currentMonth + i + 1))))
                .select(COMPLAINT_ENTITY.ID)));
        }
        return list;
    }
}
