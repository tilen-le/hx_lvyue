package com.hexing.workflow.controller;

import com.hexing.common.core.domain.R;
import com.hexing.workflow.domain.ActBusinessStatus;
import com.hexing.workflow.service.IActBusinessStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;

/**
 * @program: ruoyi-vue-plus
 * @description: 流程与业务关联控制器
 * @author: firerock_tech
 * @created: 2021/10/16 16:18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/actBusiness")
public class ActBusinessStatusController {

    private final IActBusinessStatusService iActBusinessStatusService;

    /**
     * @Description: 根据业务id查询流程实例
     * @param: businessKey 业务id
     * @return: com.ruoyi.common.core.domain.R<com.ruoyi.workflow.domain.ActBusinessStatus>
     * @Author: firerock_tech
     * @Date: 2021/10/16
     */
    @GetMapping("/getInfoByBusinessKey/{businessKey}")
    public R<ActBusinessStatus> getInfoByBusinessId(@NotBlank(message = "业务id不能为空") @PathVariable String  businessKey){
        ActBusinessStatus actBusinessStatus = iActBusinessStatusService.getInfoByBusinessKey(businessKey);
        return R.ok(actBusinessStatus);
    }

}
