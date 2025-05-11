package com.cool.modules.hospital.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.hospital.entity.DoctorEntity;
import com.cool.modules.hospital.mapper.DoctorMapper;
import com.cool.modules.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl extends BaseServiceImpl<DoctorMapper, DoctorEntity> implements DoctorService {

}