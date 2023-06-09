package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hexing.common.core.domain.BaseEntity;
import com.hexing.system.domain.bo.SysOssCons;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 开票单表
 *
 * @TableName fc_order_invoice
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_invoice")
public class FcOrderInvoice extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 开票单编号
     */
    private String invoiceNumber;
    /**
     * 订单id
     */
    @NotNull(message = "[订单id]不能为空")
    private Long orderId;
    /**
     * 发票类型
     */
    @NotNull(message = "[发票类型]不能为空")
    private Integer invoiceType;
    /**
     * 卖方银行
     */

    /**
     * 当前附件版本
     */
    private Integer currentVersion;


    @NotNull(message = "[卖方银行]不能为空")
    private Long saleBank;

    @TableField(exist = false)
    private String saleBankName;
    /**
     * 收票方
     */
    private String consigneeId;

    /**
     * 收票方名称
     */
    @TableField(exist = false)
    private String consigneeName;

    private Integer syncSapStatus;

    private String syncSapVoucher;

    private Date syncSapTime;

    /**
     * 开户行
     */
    private String openingBank;
    /**
     * 送货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date arrivalDate;
    /**
     * 验收日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;
    /**
     * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿】
     */
    private Integer approvalStatus;
    /**
     * 含税金额合计
     */
    private BigDecimal totalAmountWithTax;
    /**
     * 不含税金额合计
     */
    private BigDecimal totalAmountWithoutTax;
    /**
     * 税额合计
     */
    private BigDecimal tax;

    /**
     * 客户收货地址id
     */
    private Long consignmentId;

    private String deleted;

    @TableField(exist = false)
    private String orderNumber;

    @TableField(exist = false)
    private List<FcOrderInvoiceDetail> productList;

    @TableField(exist = false)
    private List<SysOssCons> files;

    @TableField(exist = false)
    private String orderTitle;
    @TableField(exist = false)
    private String customer;
    @TableField(exist = false)
    private String contractName;
    @TableField(exist = false)
    private String businessUnit;
    @TableField(exist = false)
    private String factory;
}
