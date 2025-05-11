package com.cool.modules.hospital.service;

import com.cool.core.base.BaseService;
import com.cool.modules.hospital.entity.ScheduleEntity;
import com.mybatisflex.core.paginate.Page;
import cn.hutool.json.JSONObject;

public interface ScheduleService extends BaseService<ScheduleEntity> {
    Page<ScheduleEntity> page(JSONObject requestParams);
}