package com.hexing.workflow.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @description: 流程节点信息
 * @author: firerock_tech
 * @created: 2021/10/16 19:35
 */
@Data
public class ProcessNodePath implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 节点名称
     */
    private String nodeName;


    /**
     * 网关或者用户任务
     */
    private String nodeType;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 表达式是否成立 true 成立 false 不成立
     */
    private Boolean expression;

    /**
     * 是否存在表达式 true 存在 false 不存在
     */
    private Boolean expressionStr;

    /**
     * 选择方式  role按角色选人  dept按部门选人  person自定义选人
     */
    private String chooseWay;

    /**
     * 审批人员
     */
    private String assignee;

    /**
     * 审批参数ID
     */
    private String assigneeId;

    /**
     * 业务规则id
     */
    private Long businessRuleId;

    /**
     * 是否弹窗选人
     */
    private Boolean isShow;

    /**
     * 是否会签
     */
    private Boolean multiple;

    /**
     * 会签保存人员KEY值
     */
    private String multipleColumn;

    /**
     * 当前节点的上一节点(用户节点或者网关)
     */
    private String sourceFlowElementId;

    /**
     * x坐标
     */
    private Double x;

}
