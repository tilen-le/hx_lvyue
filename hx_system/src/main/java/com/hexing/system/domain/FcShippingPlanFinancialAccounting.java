package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 发货计划id
     */
    private Long shippingPlanId;
    /**
     * 订单产品id
     */
    private String orderProductId;
    /**
     * 报关数量
     */
    private String reportCustomsNum;
    /**
     * 报关剩余数量
     */
    private String reportCustomsResidueNum;
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
     * 实际报关金额
     */
    private BigDecimal realityReportCustomsAmount;

    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * SAP物料编码
     */
    private String sapMaterialCode;

    /**
     * 是否需要同步sap标识，0 否 1 是
     */
    private String sapSyncFlag;

    private String deleted;

    /**
     * sap明细号=行项目
     */
    @TableField(exist = false)
    private String sapDetailNumber;
    /**
     * 订单编号
     */
    @TableField(exist = false)
    private String orderNumber;

    /**
     * 订单标题
     */
    @TableField(exist = false)
    private String orderTitle;


    /**
     * 产品名称
     */
    @TableField(exist = false)
    private String productName;


    /**
     * 产品编号
     */
    @TableField(exist = false)
    private String productNumber;
    /**
     * 产品型号
     */
    @TableField(exist = false)
    private String productModel;


    /**
     * 订单数量
     */
    @TableField(exist = false)
    private String num;


    @TableField(exist = false)
    private String productId;


}
