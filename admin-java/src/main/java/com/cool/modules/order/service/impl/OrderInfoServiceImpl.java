package com.cool.modules.order.service.impl;

import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.mapper.OrderInfoMapper;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * 订单信息ServiceImpl
 */
@Service
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfoMapper, OrderInfoEntity> implements
    OrderInfoService {
}
