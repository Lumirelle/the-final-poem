package com.cool.modules.meal.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.modules.meal.entity.MealInfoEntity;
import com.cool.modules.meal.service.MealInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import static com.cool.modules.meal.entity.table.MealInfoEntityTableDef.MEAL_INFO_ENTITY;

/**
 * 套餐信息
 */
@Tag(name = "套餐信息", description = "套餐信息")
@CoolRestController(api = {"page", "info"})
public class AppMealInfoController extends BaseController<MealInfoService, MealInfoEntity> {

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
        // 设置分页查询条件
        setPageOption(createOp()
                .keyWordLikeFields(
                        MEAL_INFO_ENTITY.NAME,
                        MEAL_INFO_ENTITY.INTRO
                )
                .fieldEq(
                        MEAL_INFO_ENTITY.STATUS,
                        MEAL_INFO_ENTITY.CATEGORY_ID,
                        MEAL_INFO_ENTITY.HOSPITAL_ID,
                        MEAL_INFO_ENTITY.DEPARTMENT_ID,
                        MEAL_INFO_ENTITY.DOCTOR_ID
                )
        );
    }
} 
