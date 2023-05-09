package com.hexing.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * fc_order_pay_milestone
 * @author 
 */
@Data
public class FcOrderPayMilestone implements Serializable {
    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 编码
     */
    private String code;

    /**
     * 里程碑类型
     */
    private String type;

    /**
     * 期待回款日期
     */
    private Date expectPayDate;

    /**
     * 期待回款比例
     */
    private String expectPayScale;

    /**
     * 剩余回款金额
     */
    private String surplusPayAmount;

    /**
     * 计划回款金额
     */
    private String planPayAmount;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private String deleted;

    private static final long serialVersionUID = 1L;
}