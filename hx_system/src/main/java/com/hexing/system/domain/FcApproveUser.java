package com.hexing.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * fc_approve_user
 * @author 
 */
@Data
public class FcApproveUser implements Serializable {
    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 审批标题
     */
    private Integer optUserId;

    /**
     * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
     */
    private Short type;

    /**
     * 事业部
     */
    private String businessUnit;

    /**
     * 工厂
     */
    private String factory;

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