package com.cool.modules.patient.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.patient.entity.PatientProfileEntity;
import com.cool.modules.patient.mapper.PatientProfileMapper;
import com.cool.modules.patient.service.PatientProfileService;
import com.cool.modules.user.entity.UserInfoEntity;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import static com.cool.modules.patient.entity.table.PatientProfileEntityTableDef.PATIENT_PROFILE_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

/**
 * 患者档案Service实现类
 */
@Service
public class PatientProfileServiceImpl extends BaseServiceImpl<PatientProfileMapper, PatientProfileEntity> implements PatientProfileService {

    // EXAMPLE：多表联查
    @Override
    public Object page(JSONObject requestParams, Page<PatientProfileEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        PATIENT_PROFILE_ENTITY.ALL_COLUMNS,
                        USER_INFO_ENTITY.NICK_NAME
                )
                .from(PATIENT_PROFILE_ENTITY)
                .leftJoin(USER_INFO_ENTITY)
                .on(PatientProfileEntity::getUserId, UserInfoEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }

}
