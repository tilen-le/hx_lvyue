package com.hexing.workflow.domain.bo;

import com.hexing.workflow.common.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @program: ruoyi-vue-plus
 * @description: 流程定义查询
 * @author: firerock_tech
 * @created: 2021/10/07 11:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DefinitionBo extends PageEntity implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 流程定义id
     */
    private String id;
    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 模型标识key
     */
    private String key;
}
