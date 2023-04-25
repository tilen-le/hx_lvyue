package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 订单表
 *
 * @TableName fc_order
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order")
public class FcOrder extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 订单标题
     */
    private String orderTitle;
    /**
     * 币种
     */
    private String currency;
    /**
     * 订单状态（0：启用，1：禁用）
     */
    private String status;
    /**
     * SAP创建时间
     */
    private Date sapCreateTime;
    /**
     * 售达方
     */
    private String soldToParty;
    /**
     * 售达方编码
     */
    private String soldToPartyCd;
    /**
     * 订单金额
     */
    private String amount;
    /**
     * 税率
     */
    private String taxRate;
    /**
     * 付款方编码
     */
    private String payerCd;
    /**
     * 付款方
     */
    private String payer;
    /**
     * 是否备表直发
     */
    private String isBackupTableDirectly;
    /**
     * 送达方/收货方编码
     */
    private String reciverCd;
    /**
     * 送达方/收货方
     */
    private String reciver;
    /**
     * 要求交货日期
     */
    private Date requireDeliveryDate;
    /**
     * 客户经理
     */
    private String customerManager;
    /**
     * 收票方编码
     */
    private String bileeCd;
    /**
     * 收票方
     */
    private String billee;
    /**
     * 事业部
     */
    private String businessUnit;
    /**
     * 销售组织
     */
    private String saleOrg;
    /**
     * 销售类型
     */
    private String saleType;
    /**
     * 收款方编码
     */
    private String payeeCd;
    /**
     * 收款方
     */
    private String payee;
    /**
     * 分销渠道
     */
    private String distributionChannel;
    /**
     * 国内/国际营销部
     */
    private String marketingDepartment;
    /**
     * 仓储部经理
     */
    private String warehouseManager;
    /**
     * 工厂
     */
    private String factory;
    /**
     * 预计发货日期
     */
    private Date expectedDeliveryDate;
    /**
     * 预计到货日期
     */
    private Date expectedArrivalDate;
    /**
     * 预计验收日期
     */
    private Date expectedCheckDate;
    /**
     * 预计质保日期
     */
    private Date expectedWarrantyDate;
    private String deleted;

    @TableField(exist = false)
    private String contractNumber;

    @TableField(exist = false)
    private String contractName;

    @TableField(exist = false)
    private List<FcOrderProduct> products;

    @TableField(exist = false)
    private Boolean hasChildren;


}
