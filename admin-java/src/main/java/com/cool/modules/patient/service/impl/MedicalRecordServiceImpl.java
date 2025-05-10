package com.cool.modules.patient.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.patient.entity.MedicalRecordEntity;
import com.cool.modules.patient.entity.PatientProfileEntity;
import com.cool.modules.patient.mapper.MedicalRecordMapper;
import com.cool.modules.patient.service.MedicalRecordService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import static com.cool.modules.patient.entity.table.MedicalRecordEntityTableDef.MEDICAL_RECORD_ENTITY;
import static com.cool.modules.patient.entity.table.PatientProfileEntityTableDef.PATIENT_PROFILE_ENTITY;

/**
 * 就诊记录Service实现类
 */
@Service
public class MedicalRecordServiceImpl extends BaseServiceImpl<MedicalRecordMapper, MedicalRecordEntity> implements MedicalRecordService {

    // EXAMPLE：多表联查
    @Override
    public Object page(JSONObject requestParams, Page<MedicalRecordEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        MEDICAL_RECORD_ENTITY.ALL_COLUMNS,
                        PATIENT_PROFILE_ENTITY.NAME.as("patientName")
                )
                .from(MEDICAL_RECORD_ENTITY)
                .leftJoin(PATIENT_PROFILE_ENTITY)
                .on(MedicalRecordEntity::getPatientId, PatientProfileEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }

}
