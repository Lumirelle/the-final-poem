package com.cool.modules.patient.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.patient.entity.MedicalRecordEntity;
import com.cool.modules.patient.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.patient.entity.table.MedicalRecordEntityTableDef.MEDICAL_RECORD_ENTITY;

/**
 * 就诊记录管理
 */
@Tag(name = "就诊记录管理", description = "管理患者就诊记录信息")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminMedicalRecordController extends BaseController<MedicalRecordService, MedicalRecordEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
            .fieldEq(MEDICAL_RECORD_ENTITY.PATIENT_ID)
            .keyWordLikeFields(MEDICAL_RECORD_ENTITY.DOCTOR_NAME, MEDICAL_RECORD_ENTITY.HOSPITAL)
        );
    }
}
