package com.cool.modules.patient.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.patient.entity.PatientProfileEntity;
import com.cool.modules.patient.service.PatientProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.patient.entity.table.PatientProfileEntityTableDef.PATIENT_PROFILE_ENTITY;

/**
 * 患者档案管理
 */
@Tag(name = "患者档案管理", description = "管理患者档案信息")
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminPatientProfileController extends BaseController<PatientProfileService, PatientProfileEntity> {
    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
            .fieldEq(
                    PATIENT_PROFILE_ENTITY.ID,
                    PATIENT_PROFILE_ENTITY.GENDER,
                    PATIENT_PROFILE_ENTITY.TYPE
            )
            .keyWordLikeFields(
                    PATIENT_PROFILE_ENTITY.NAME,
                    PATIENT_PROFILE_ENTITY.MEDICAL_RECORD_NUMBER
            ));
    }
}
