package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 发货计划表
 *
 * @TableName fc_shipping_plan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_shipping_plan")
public class FcShippingPlan extends BaseEntity {

    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 原计划出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date originalPlannedDeliveryDate;
    /**
     * 出库地点
     */
    private String deliveryLocation;
    /**
     * 清关金额
     */
    private BigDecimal currentNode;
    /**
     * INVOICE NO
     */
    private String invoiceNo;
    /**
     * 客户联系人
     */
    private String customerContact;
    /**
     * 币种【无、ABS-阿根廷比索、BDT-孟加拉国塔卡、BRL-巴西市、CNY-中国人民币、COP-哥伦比亚比素、EUR-欧元、GHS-加纳塞地、IDR-印度尼西亚卢比、PEN-秘鲁素尔、USD-美元、ZAR-南非兰特】
     */
    private String currency;
    /**
     * 联系方式
     */
    private String contactInformation;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 收货方
     */
    private String consignee;
    /**
     * 大洲【亚洲、欧洲、非洲、大洋洲、南美洲、北美洲】
     */
    private String continent;
    /**
     * 国家【根据大洲获取对应大洲国家列表】
     */
    private String nation;
    /**
     * SHIPPING MARK
     */
    private String shippingMark;
    /**
     * 是否通知单证专员【0.否 1.是】
     */
    private String isNoticeDocumentSpecialist;
    /**
     * 通知方【客户表id】
     */
    private String notifyId;
    /**
     * 货代
     */
    private String forwarder;
    /**
     * 是否报关完成【0.否 1.是】
     */
    private String reportCustomsComplted;
    /**
     * 运费
     */
    private String freight;
    /**
     * 保费
     */
    private String premium;
    /**
     * 税费
     */
    private String taxation;
    /**
     * 海运费
     */
    private String oceanFreight;
    /**
     * 空运费
     */
    private String airFreight;
    /**
     * SAP开票凭证
     */
    private String sapBillingDocuments;
    /**
     * SAP交货项目
     */
    private String sapDeliveryItems;
    /**
     * 预计到货日
     */
    private Date expectedDeliveryDate;
    /**
     * 预计出运日
     */
    private Date expectedShipmentDate;
    /**
     * 报关单号
     */
    private String reportCustomsNumber;
    /**
     * 最迟装箱日期
     */
    private Date latestPackingDate;
    /**
     * 实际开船日
     */
    private Date actualDepartureDate;
    /**
     * 信用证号码
     */
    private String lcNo;
    /**
     * 信用证交单快递号码
     */
    private String lcDeliveryNo;
    /**
     * 货物完成时间及地点
     */
    private String goodsCompleteDateAndLocate;
    /**
     * 保险条款
     */
    private String insuranceClauses;
    /**
     * 特殊要求
     */
    private String specialRequirements;
    /**
     * 备注
     */
    private String remark;
    /**
     * 报清关差异原因
     */
    private String reportCustomsClearanceReason;
    /**
     * 单证专员
     */
    private String documentSpecialist;
    /**
     * 财务人员
     */
    private String financialStaff;
    /**
     * 报关单位
     */
    private String reportCustomsInstitutions;
    /**
     * 运输方式
     */
    private String transType;
    /**
     * 柜型及数量
     */
    private String cabinetTypeAndQuantity;
    /**
     * 贸易方式
     */
    private String tradeMode;
    /**
     * 出运港
     */
    private String shipmentPort;
    /**
     * 目的港
     */
    private String destinationPort;
    /**
     * 收汇方式
     */
    private String collectionMethod;
    /**
     * 预收汇日期
     */
    private String preCollectDate;
    /**
     * 成交方式
     */
    private String transactionMethod;
    /**
     * 是否同步SAP【0.否 1.是】
     */
    private String isSyncSap;
    private String deleted;

    @TableField(exist = false)
    private List<FcShippingPlanFinancialAccounting> accountIfs;

    @TableField(exist = false)
    private List<FcShippingPlanReportInfo> reportIfs;

}
