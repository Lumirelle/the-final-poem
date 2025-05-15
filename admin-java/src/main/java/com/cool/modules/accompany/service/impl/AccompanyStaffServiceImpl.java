package com.cool.modules.accompany.service.impl;

import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.entity.table.AccompanyStaffEntityTableDef;
import com.cool.modules.accompany.mapper.AccompanyStaffMapper;
import com.cool.modules.accompany.service.AccompanyStaffService;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.entity.table.UserInfoEntityTableDef;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccompanyStaffServiceImpl extends BaseServiceImpl<AccompanyStaffMapper, AccompanyStaffEntity> implements AccompanyStaffService {
}
