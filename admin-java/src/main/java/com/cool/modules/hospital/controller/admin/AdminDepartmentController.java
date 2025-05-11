package com.cool.modules.hospital.controller.admin;

import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.hospital.entity.DepartmentEntity;
import com.cool.modules.hospital.service.DepartmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import cn.hutool.json.JSONObject;
import static com.cool.modules.hospital.entity.table.DepartmentEntityTableDef.DEPARTMENT_ENTITY;

/**
 * 科室信息
 */
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminDepartmentController extends BaseController<DepartmentService, DepartmentEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        setPageOption(createOp()
                .keyWordLikeFields(DEPARTMENT_ENTITY.NAME)
                .fieldEq(DEPARTMENT_ENTITY.CODE, DEPARTMENT_ENTITY.STATUS));
    }
}