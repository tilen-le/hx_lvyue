package com.hexing.workflow.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description: 流程定义查询
 * @author: firerock_tech
 * @created: 2021/10/07 11:15
 */
@Data
public class NextNodeBo implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 任务id
     */
    private String taskId;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables == null ? new HashMap<>(16) : variables;
    }
}
