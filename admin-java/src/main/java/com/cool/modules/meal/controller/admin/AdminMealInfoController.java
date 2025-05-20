package com.cool.modules.meal.controller.admin;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.meal.entity.MealInfoEntity;
import com.cool.modules.meal.service.MealInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestAttribute;

import static com.cool.modules.meal.entity.table.MealInfoEntityTableDef.MEAL_INFO_ENTITY;

/**
 * 服务套餐管理
 */
@CoolRestController(api = {"add", "delete", "update", "info", "page"})
public class AdminMealInfoController extends BaseController<MealInfoService, MealInfoEntity> {

    @Override
    protected void init(HttpServletRequest request, @RequestAttribute JSONObject requestParams) {
        setPageOption(createOp()
            .keyWordLikeFields(
                MEAL_INFO_ENTITY.NAME
            )
            .fieldEq(
                MEAL_INFO_ENTITY.ID,
                MEAL_INFO_ENTITY.STATUS,
                MEAL_INFO_ENTITY.CATEGORY_ID,
                MEAL_INFO_ENTITY.HOSPITAL_ID,
                MEAL_INFO_ENTITY.DEPARTMENT_ID,
                MEAL_INFO_ENTITY.DOCTOR_ID,
                MEAL_INFO_ENTITY.STAFF_ID
            )
        );
    }
}
