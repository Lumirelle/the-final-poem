package com.cool.modules.order.service;

import com.cool.core.base.BaseService;
import com.cool.modules.order.entity.OrderInfoEntity;

/**
 * 订单信息Service
 */
public interface OrderInfoService extends BaseService<OrderInfoEntity> {

  /**
   * 统计付款订单总数
   */
  Long countPayOrder();
  
}
