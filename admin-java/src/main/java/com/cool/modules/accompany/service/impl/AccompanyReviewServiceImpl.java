package com.cool.modules.accompany.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.accompany.entity.AccompanyReviewEntity;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.entity.table.AccompanyReviewEntityTableDef;
import com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef;
import com.cool.modules.accompany.mapper.AccompanyReviewMapper;
import com.cool.modules.accompany.service.AccompanyReviewService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

@Service
public class AccompanyReviewServiceImpl extends BaseServiceImpl<AccompanyReviewMapper, AccompanyReviewEntity>
        implements AccompanyReviewService {

    @Override
    public Object page(JSONObject requestParams, Page<AccompanyReviewEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        AccompanyReviewEntityTableDef.ACCOMPANY_REVIEW_ENTITY.ALL_COLUMNS,
                        AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY.NAME.as("staffName")
                )
                .from(AccompanyReviewEntityTableDef.ACCOMPANY_REVIEW_ENTITY)
                .leftJoin(AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY)
                .on(AccompanyReviewEntity::getStaffId, AccompanyStaffEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }
}
