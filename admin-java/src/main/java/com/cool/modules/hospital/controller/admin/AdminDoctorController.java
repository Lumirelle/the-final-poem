package com.cool.modules.hospital.controller.admin;

import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.hospital.entity.DoctorEntity;
import com.cool.modules.hospital.service.DoctorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import cn.hutool.json.JSONObject;
import static com.cool.modules.hospital.entity.table.DoctorEntityTableDef.DOCTOR_ENTITY;

/**
 * 医生信息
 */
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminDoctorController extends BaseController<DoctorService, DoctorEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
                .keyWordLikeFields(DOCTOR_ENTITY.NAME)
                .fieldEq(DOCTOR_ENTITY.JOB_CODE, DOCTOR_ENTITY.STATUS));
    }
}