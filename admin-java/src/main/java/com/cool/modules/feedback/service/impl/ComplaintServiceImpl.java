package com.cool.modules.feedback.service.impl;

import static com.cool.modules.feedback.entity.table.ComplaintEntityTableDef.COMPLAINT_ENTITY;

import cn.hutool.core.util.StrUtil;
import com.cool.core.base.BaseServiceImpl;
import com.cool.core.exception.CoolPreconditions;
import com.cool.modules.feedback.entity.ComplaintEntity;
import com.cool.modules.feedback.mapper.ComplaintMapper;
import com.cool.modules.feedback.service.ComplaintService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投诉信息服务实现类
 */
@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl extends BaseServiceImpl<ComplaintMapper, ComplaintEntity> implements
    ComplaintService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleComplaint(Long id, Long handlerId, String handleResult, String remark) {
        ComplaintEntity complaint = this.mapper.selectOneById(id);
        CoolPreconditions.checkEmpty(complaint, "投诉信息不存在");

        complaint.setHandlerId(handlerId);
        complaint.setHandleResult(handleResult);
        complaint.setRemark(remark);
        complaint.setHandleTime(new Date());
        complaint.setStatus(2); // 已解决
        return this.mapper.update(complaint) > 0;
    }

}