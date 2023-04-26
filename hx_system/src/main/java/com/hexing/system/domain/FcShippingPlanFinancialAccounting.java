package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
* 发货计划关联核算收入表
* @TableName fc_shipping_plan_financial_accounting
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_shipping_plan_financial_accounting")
public class FcShippingPlanFinancialAccounting extends BaseEntity {

    /**
    * 主键
    */
    @TableId(value = "id")
    private Long id;
    /**
    * 序号
    */
    private Integer sequence;
    /**
    * 订单产品id
    */
    private String orderProductId;
    /**
    * 报关数量
    */
    private Integer reportCustomsNum;
    /**
    * 报关剩余数量
    */
    private Integer reportCustomsResidueNum;
    /**
    * 产品总金额
    */
    private BigDecimal totalProductAmount;
    /**
    * 剩余报关金额
    */
    private BigDecimal remainingReportCustomsAmount;
    /**
    * 本次报关金额
    */
    private BigDecimal currentReportCustomsAmount;
    /**
    * 单价
    */
    private BigDecimal unitPrice;
    /**
    * SAP物料编码
    */
    private String sapMaterialCode;
    private String deleted;


}
