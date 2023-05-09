package com.hexing.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * fc_approve
 * @author 
 */
@Data
public class FcApprove implements Serializable {
    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 审批标题
     */
    private String title;

    /**
     * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
     */
    private Integer type;

    private Long mainId;

    /**
     * 当前节点
     */
    private String currentNode;

    /**
     * 发起人
     */
    private String originator;

    /**
     * 状态
     */
    private String status;

    /**
     * 申请时间
     */
    private Date requestTime;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private String deleted;

    private static final long serialVersionUID = 1L;
}