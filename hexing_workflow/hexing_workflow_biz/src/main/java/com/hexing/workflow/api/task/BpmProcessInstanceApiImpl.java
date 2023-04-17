package com.hexing.workflow.api.task;

import com.hexing.workflow.api.task.dto.BpmProcessInstanceCreateReqDTO;
import com.hexing.workflow.service.task.BpmProcessInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Flowable 流程实例 Api 实现类
 *
 * @author firerock_tech
 * @author jason
 */
@Service
@Validated
public class BpmProcessInstanceApiImpl implements BpmProcessInstanceApi {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO reqDTO) {
        return processInstanceService.createProcessInstance(userId, reqDTO);
    }
}
