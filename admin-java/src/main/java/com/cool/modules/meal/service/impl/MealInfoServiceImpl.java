package com.cool.modules.meal.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef;
import com.cool.modules.hospital.entity.DepartmentEntity;
import com.cool.modules.hospital.entity.DoctorEntity;
import com.cool.modules.hospital.entity.HospitalInfoEntity;
import com.cool.modules.hospital.entity.table.DepartmentEntityTableDef;
import com.cool.modules.hospital.entity.table.DoctorEntityTableDef;
import com.cool.modules.hospital.entity.table.HospitalInfoEntityTableDef;
import com.cool.modules.meal.entity.MealCategoryEntity;
import com.cool.modules.meal.entity.MealInfoEntity;
import com.cool.modules.meal.entity.table.MealCategoryEntityTableDef;
import com.cool.modules.meal.entity.table.MealInfoEntityTableDef;
import com.cool.modules.meal.mapper.MealInfoMapper;
import com.cool.modules.meal.service.MealInfoService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY;
import static com.cool.modules.hospital.entity.table.DepartmentEntityTableDef.DEPARTMENT_ENTITY;
import static com.cool.modules.hospital.entity.table.DoctorEntityTableDef.DOCTOR_ENTITY;
import static com.cool.modules.hospital.entity.table.HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY;
import static com.cool.modules.meal.entity.table.MealCategoryEntityTableDef.MEAL_CATEGORY_ENTITY;
import static com.cool.modules.meal.entity.table.MealInfoEntityTableDef.MEAL_INFO_ENTITY;

@Service
@RequiredArgsConstructor
public class MealInfoServiceImpl extends BaseServiceImpl<MealInfoMapper, MealInfoEntity> implements MealInfoService {

    @Override
    public Object page(JSONObject requestParams, Page<MealInfoEntity> page, QueryWrapper queryWrapper) {
        queryWrapper
            .select(
                MealInfoEntityTableDef.MEAL_INFO_ENTITY.ALL_COLUMNS,
                MealCategoryEntityTableDef.MEAL_CATEGORY_ENTITY.NAME.as("categoryName"),
                HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY.NAME.as("hospitalName"),
                DepartmentEntityTableDef.DEPARTMENT_ENTITY.NAME.as("departmentName"),
                DoctorEntityTableDef.DOCTOR_ENTITY.NAME.as("doctorName"),
                AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY.NAME.as("staffName")
            )
            .from(MealInfoEntityTableDef.MEAL_INFO_ENTITY)
            .leftJoin(MealCategoryEntityTableDef.MEAL_CATEGORY_ENTITY)
            .on(MealInfoEntity::getCategoryId, MealCategoryEntity::getId)
            .leftJoin(HospitalInfoEntityTableDef.HOSPITAL_INFO_ENTITY)
            .on(MealInfoEntity::getHospitalId, HospitalInfoEntity::getId)
            .leftJoin(DepartmentEntityTableDef.DEPARTMENT_ENTITY)
            .on(MealInfoEntity::getDepartmentId, DepartmentEntity::getId)
            .leftJoin(DoctorEntityTableDef.DOCTOR_ENTITY)
            .on(MealInfoEntity::getDoctorId, DoctorEntity::getId)
            .leftJoin(AccompanyStaffEntityTableDef.ACCOMPANY_STAFF_ENTITY)
            .on(MealInfoEntity::getStaffId, AccompanyStaffEntity::getId);
        return mapper.paginateWithRelations(page, queryWrapper);
    }

    @Override
    public Object info(Long id) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(
                MEAL_INFO_ENTITY.ALL_COLUMNS,
                MEAL_CATEGORY_ENTITY.NAME.as("categoryName"),
                HOSPITAL_INFO_ENTITY.NAME.as("hospitalName"),
                DEPARTMENT_ENTITY.NAME.as("departmentName"),
                DOCTOR_ENTITY.NAME.as("doctorName"),
                ACCOMPANY_STAFF_ENTITY.NAME.as("staffName")
            )
            .from(MEAL_INFO_ENTITY)
            .leftJoin(MEAL_CATEGORY_ENTITY).on(MEAL_INFO_ENTITY.CATEGORY_ID.eq(MEAL_CATEGORY_ENTITY.ID))
            .leftJoin(HOSPITAL_INFO_ENTITY).on(MEAL_INFO_ENTITY.HOSPITAL_ID.eq(HOSPITAL_INFO_ENTITY.ID))
            .leftJoin(DEPARTMENT_ENTITY).on(MEAL_INFO_ENTITY.DEPARTMENT_ID.eq(DEPARTMENT_ENTITY.ID))
            .leftJoin(DOCTOR_ENTITY).on(MEAL_INFO_ENTITY.DOCTOR_ID.eq(DOCTOR_ENTITY.ID))
            .leftJoin(ACCOMPANY_STAFF_ENTITY).on(MEAL_INFO_ENTITY.STAFF_ID.eq(ACCOMPANY_STAFF_ENTITY.ID));
        return mapper.selectOneWithRelationsByQuery(queryWrapper);
    }
}
