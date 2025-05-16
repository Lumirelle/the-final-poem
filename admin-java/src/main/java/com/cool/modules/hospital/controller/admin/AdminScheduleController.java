package com.cool.modules.hospital.controller.admin;

import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.hospital.entity.ScheduleEntity;
import com.cool.modules.hospital.service.ScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import cn.hutool.json.JSONObject;
import com.cool.core.request.R;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 排班信息
 */
@CoolRestController(api = {"add", "delete", "update", "info"})
public class AdminScheduleController extends BaseController<ScheduleService, ScheduleEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

}