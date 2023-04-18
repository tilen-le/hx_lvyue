package com.hexing.workflow.domain.bo;

import com.hexing.workflow.common.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @program: ruoyi-vue-plus
 * @description: 运行中流程查询
 * @author: firerock_tech
 * @created: 2021/10/16 19:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessInstRunningBo extends PageEntity implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 任务发起人
     */
    private String startUserId;
}
