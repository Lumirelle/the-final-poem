package com.cool.modules.order.service.impl;

import static com.cool.modules.order.entity.table.OrderLogEntityTableDef.ORDER_LOG_ENTITY;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.order.entity.OrderLogEntity;
import com.cool.modules.order.mapper.OrderLogMapper;
import com.cool.modules.order.service.OrderLogService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 订单日志ServiceImpl
 */
@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLogMapper, OrderLogEntity> implements
    OrderLogService {
}
