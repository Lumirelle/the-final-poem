package com.cool.modules.hospital.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.hospital.entity.DepartmentEntity;
import com.cool.modules.hospital.entity.DoctorEntity;
import com.cool.modules.hospital.entity.HospitalInfoEntity;
import com.cool.modules.hospital.entity.table.DepartmentEntityTableDef;
import com.cool.modules.hospital.entity.table.DoctorEntityTableDef;
import com.cool.modules.hospital.entity.table.HospitalInfoEntityTableDef;
import com.cool.modules.hospital.mapper.DoctorMapper;
import com.cool.modules.hospital.service.DoctorService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl extends BaseServiceImpl<DoctorMapper, DoctorEntity> implements DoctorService {

    @Override
    public Object page(JSONObject requestParams, Page<DoctorEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        DoctorEntityTableDef.DOCTOR_ENTITY.ALL_COLUMNS,
                        DepartmentEntityTableDef.DEPARTMENT_ENTITY.NAME.as("departmentName"),
                        HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY.NAME.as("hospitalName")
                )
                .from(DoctorEntityTableDef.DOCTOR_ENTITY)
                .leftJoin(DepartmentEntityTableDef.DEPARTMENT_ENTITY)
                .on(DoctorEntity::getDepartmentId, DepartmentEntity::getId)
                .leftJoin(HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY)
                .on(DoctorEntity::getHospitalId, HospitalInfoEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }
}
