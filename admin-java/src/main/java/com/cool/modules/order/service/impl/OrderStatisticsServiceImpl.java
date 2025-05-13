package com.cool.modules.order.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.order.entity.OrderStatisticsEntity;
import com.cool.modules.order.mapper.OrderStatisticsMapper;
import com.cool.modules.order.service.OrderStatisticsService;
import org.springframework.stereotype.Service;

/**
 * 订单统计ServiceImpl
 */
@Service
public class OrderStatisticsServiceImpl extends
    BaseServiceImpl<OrderStatisticsMapper, OrderStatisticsEntity> implements OrderStatisticsService {
}