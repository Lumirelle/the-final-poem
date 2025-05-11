package com.cool.modules.meal.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.meal.entity.MealCategoryEntity;
import com.cool.modules.meal.entity.MealInfoEntity;
import com.cool.modules.meal.entity.table.MealCategoryEntityTableDef;
import com.cool.modules.meal.entity.table.MealInfoEntityTableDef;
import com.cool.modules.meal.mapper.MealInfoMapper;
import com.cool.modules.meal.service.MealInfoService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

@Service
public class MealInfoServiceImpl extends BaseServiceImpl<MealInfoMapper, MealInfoEntity> implements MealInfoService {

    @Override
    public Object page(JSONObject requestParams, Page<MealInfoEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                        MealInfoEntityTableDef.MEAL_INFO_ENTITY.ALL_COLUMNS,
                        MealCategoryEntityTableDef.MEAL_CATEGORY_ENTITY.NAME.as("categoryName")
                )
                .from(MealInfoEntityTableDef.MEAL_INFO_ENTITY)
                .leftJoin(MealCategoryEntityTableDef.MEAL_CATEGORY_ENTITY)
                .on(MealInfoEntity::getCategoryId, MealCategoryEntity::getId);
        return mapper.paginate(page, queryWrapper);
    }
}
