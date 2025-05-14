package com.cool.modules.feedback.service;

import com.cool.core.base.BaseService;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.Map;

/**
 * 投诉信息服务接口
 */
public interface ComplaintService extends BaseService<ComplaintEntity> {

    boolean handleComplaint(Long id, Long handlerId, String handleResult, String remark);

}