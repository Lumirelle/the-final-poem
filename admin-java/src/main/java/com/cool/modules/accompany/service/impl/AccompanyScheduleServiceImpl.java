package com.cool.modules.accompany.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.accompany.entity.AccompanyScheduleEntity;
import com.cool.modules.accompany.mapper.AccompanyScheduleMapper;
import com.cool.modules.accompany.service.AccompanyScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccompanyScheduleServiceImpl extends BaseServiceImpl<AccompanyScheduleMapper, AccompanyScheduleEntity> implements AccompanyScheduleService {
}