package com.cool.modules.hospital.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.hospital.entity.ScheduleEntity;
import com.cool.modules.hospital.mapper.ScheduleMapper;
import com.cool.modules.hospital.service.ScheduleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import static com.cool.modules.hospital.entity.table.ScheduleEntityTableDef.SCHEDULE_ENTITY;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl extends BaseServiceImpl<ScheduleMapper, ScheduleEntity> implements ScheduleService {

    @Override
    public Page<ScheduleEntity> page(JSONObject requestParams) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 名称模糊搜索（假设没有名称字段，这里使用时段模糊搜索代替）
        if (requestParams.containsKey("keyWord") && requestParams.getStr("keyWord") != null) {
            queryWrapper.and(SCHEDULE_ENTITY.TIME_SLOT.like("%" + requestParams.getStr("keyWord") + "%"));
        }

        // 状态精确筛选
        if (requestParams.containsKey("status") && requestParams.getInt("status") != null) {
            queryWrapper.and(SCHEDULE_ENTITY.STATUS.eq(requestParams.getInt("status")));
        }

        // 日期范围筛选（假设没有日期范围，这里使用日期精确匹配代替）
        if (requestParams.containsKey("scheduleDate") && requestParams.getStr("scheduleDate") != null) {
            queryWrapper.and(SCHEDULE_ENTITY.SCHEDULE_DATE.eq(requestParams.getStr("scheduleDate")));
        }

        // 科室ID精确匹配
        if (requestParams.containsKey("departmentId") && requestParams.getLong("departmentId") != null) {
            queryWrapper.and(SCHEDULE_ENTITY.DEPARTMENT_ID.eq(requestParams.getLong("departmentId")));
        }

        // 医生ID精确匹配
        if (requestParams.containsKey("doctorId") && requestParams.getLong("doctorId") != null) {
            queryWrapper.and(SCHEDULE_ENTITY.DOCTOR_ID.eq(requestParams.getLong("doctorId")));
        }

        Page<ScheduleEntity> page = new Page<>(requestParams.getInt("page", 1), requestParams.getInt("size", 20));
        return mapper.paginate(page, queryWrapper);
    }
}