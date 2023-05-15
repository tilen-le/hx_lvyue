package com.hexing.system.domain.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FcShippingPlanReportInfoVo {

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * sap明细号=行项目
     */
    private String sapDetailNumber;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * productId
     */
    private String orderProductId;

    /**
     * 售达方=客户
     */
    private String soldToParty;


    /**
     * 产品编号=SAP物料编码
     */
    private String productNumber;
    /**
     * 产品型号
     */
    private String productModel;

    /**
     * 订单产品数量
     */
    private String num;

    /**
     * 单价
     */
    private String unitPrice;

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
     * SAP物料编码
     */
    private String sapMaterialCode;


}
