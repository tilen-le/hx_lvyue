package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
* 开票单明细表
* @TableName fc_order_invoice_detail
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_invoice_detail")
public class FcOrderInvoiceDetail extends BaseEntity
{

    /**
    * 主键
    */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
    * 开票单id
    */
    private Long invoiceId;
    /**
    * 产品id
    */
    private String orderProductId;
    /**
    * 单位
    */
    private String unit;
    /**
    * 财务软件编码
    */
    private Integer sapFinancialCode;
    /**
    * 申请开票数量
    */
    private Integer appliedQuantity;
    /**
    * 开票含税单价
    */
    private BigDecimal invoicingUnitPriceWithTax;
    /**
    * 客户物料名称
    */
    private String customerMaterialName;
    /**
    * 客户规格名称
    */
    private String customerSpecName;
    /**
    * 开票含税金额合计
    */
    private BigDecimal invoicingAmountWithTax;
    private String deleted;

}
