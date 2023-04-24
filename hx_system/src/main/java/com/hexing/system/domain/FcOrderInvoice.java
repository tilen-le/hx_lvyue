package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* 开票单表
* @TableName fc_order_invoice
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_invoice")
public class FcOrderInvoice extends BaseEntity {

    /**
    * 主键
    */
    @TableId(value = "id")
    private Integer id;
    /**
    * 开票单编号
    */
    private String invoiceNumber;
    /**
    * 订单id
    */
    @NotNull(message="[订单id]不能为空")
    private Integer orderId;
    /**
    * 发票类型
    */
    @NotNull(message="[发票类型]不能为空")
    private Integer invoiceType;
    /**
    * 卖方银行
    */
    @NotNull(message="[卖方银行]不能为空")
    private Integer saleBank;
    /**
    * 收票方
    */
    private String consigneeId;
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
    private String deleted;


}
