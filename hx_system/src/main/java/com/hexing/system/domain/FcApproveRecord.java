package com.hexing.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * fc_approve_record
 * @author 
 */
@Data
public class FcApproveRecord implements Serializable {
    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 审批id
     */
    private Integer approveId;

    /**
     * 审批结果【1.通过，2驳回】
     */
    private String result;

    /**
     * 审批操作人
     */
    private String optUser;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private String deleted;

    private static final long serialVersionUID = 1L;
}