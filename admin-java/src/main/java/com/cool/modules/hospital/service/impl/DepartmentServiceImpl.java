package com.cool.modules.hospital.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.hospital.entity.DepartmentEntity;
import com.cool.modules.hospital.entity.DoctorEntity;
import com.cool.modules.hospital.entity.HospitalInfoEntity;
import com.cool.modules.hospital.entity.table.DepartmentEntityTableDef;
import com.cool.modules.hospital.entity.table.DoctorEntityTableDef;
import com.cool.modules.hospital.entity.table.HospitalInfoEntityTableDef;
import com.cool.modules.hospital.mapper.DepartmentMapper;
import com.cool.modules.hospital.service.DepartmentService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

    @Override
    public Object page(JSONObject requestParams, Page<DepartmentEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        DepartmentEntityTableDef.DEPARTMENT_ENTITY.ALL_COLUMNS,
                        HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY.NAME.as("hospitalName"),
                        HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY.CODE.as("hospitalCode")
                )
                .from(DepartmentEntityTableDef.DEPARTMENT_ENTITY)
                .leftJoin(HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY)
                .on(DepartmentEntity::getHospitalId, HospitalInfoEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }
}
