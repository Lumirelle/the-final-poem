package com.cool.modules.accompany.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.accompany.entity.AccompanyStaffEntity;
import com.cool.modules.accompany.mapper.AccompanyStaffMapper;
import com.cool.modules.accompany.service.AccompanyStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccompanyStaffServiceImpl extends BaseServiceImpl<AccompanyStaffMapper, AccompanyStaffEntity> implements AccompanyStaffService {
}