package com.cool.modules.order.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.mapper.OrderInfoMapper;
import com.cool.modules.order.service.OrderInfoService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import static com.cool.modules.meal.entity.table.MealInfoEntityTableDef.MEAL_INFO_ENTITY;
import static com.cool.modules.order.entity.table.OrderInfoEntityTableDef.ORDER_INFO_ENTITY;
import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;

/**
 * 订单信息ServiceImpl
 */
@Service
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfoMapper, OrderInfoEntity> implements
    OrderInfoService {

    @Override
    public Object page(JSONObject requestParams, Page<OrderInfoEntity> page, QueryWrapper queryWrapper) {
        queryWrapper.select(
                ORDER_INFO_ENTITY.ALL_COLUMNS,
                MEAL_INFO_ENTITY.NAME.as("mealName"),
                MEAL_INFO_ENTITY.COVER.as("mealCover"),
                USER_INFO_ENTITY.NICK_NAME.as("userName")
            )
            .from(ORDER_INFO_ENTITY)
            .leftJoin(MEAL_INFO_ENTITY).on(ORDER_INFO_ENTITY.MEAL_ID.eq(MEAL_INFO_ENTITY.ID))
            .leftJoin(USER_INFO_ENTITY).on(ORDER_INFO_ENTITY.PAY_USER_ID.eq(USER_INFO_ENTITY.ID));
        return mapper.paginateWithRelations(page, queryWrapper);
    }

    @Override
    public Object info(Long id) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(
                ORDER_INFO_ENTITY.ALL_COLUMNS,
                MEAL_INFO_ENTITY.NAME.as("mealName"),
                MEAL_INFO_ENTITY.COVER.as("mealCover"),
                USER_INFO_ENTITY.NICK_NAME.as("userName")
            )
            .from(ORDER_INFO_ENTITY)
            .leftJoin(MEAL_INFO_ENTITY).on(ORDER_INFO_ENTITY.MEAL_ID.eq(MEAL_INFO_ENTITY.ID))
            .leftJoin(USER_INFO_ENTITY).on(ORDER_INFO_ENTITY.PAY_USER_ID.eq(USER_INFO_ENTITY.ID))
            .where(ORDER_INFO_ENTITY.ID.eq(id));
        return mapper.selectOneWithRelationsByQuery(queryWrapper);
    }

    @Override
    public Long countPayOrder() {
        return count(QueryWrapper.create()
            .from(ORDER_INFO_ENTITY)
            .where(ORDER_INFO_ENTITY.STATUS.ge(1).and(ORDER_INFO_ENTITY.STATUS.le(3)))
        );
    }

    @Override
    public Long add(OrderInfoEntity entity) {
        // NO + 6位随机数 + 当前时间戳
        entity.setOrderNumber("NO" + RandomUtil.randomString("0123456789", 6) + System.currentTimeMillis());
        mapper.insert(entity);
        return entity.getId();
    }
}
