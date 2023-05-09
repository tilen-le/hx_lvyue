package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 回款认领单表
 *
 * @TableName fc_payment_claim
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_payment_claim")
public class FcPaymentClaim extends BaseEntity {

    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 回款单id
     */
    private Long paymentId;
    /**
     * 认领单号
     */
    private String claimNumber;
    /**
     * 同步SAP信息时间
     */
    private Date syncSapInfoTime;
    /**
     * 同步SAP信息
     */
    private String syncSapInfo;
    /**
     * 同步SAP状态
     */
    private String syncSapStatus;
    /**
     * 同步sap时间
     */
    private Date syncSapTime;
    /**
     * 撤销同步SAP时间
     */
    private Date cancelSapTime;
    /**
     * 撤销同步sap信息
     */
    private String cancelSapInfo;
    /**
     * 撤销同步sap信息时间
     */
    private Date cancelSapInfoTime;
    /**
     * 认领单状态【1.生效 0.失效】
     */
    private String status;
    /**
     * 认领人
     */
    private String claimCurrency;
    /**
     * 认领金额
     */
    private String claimAmount;
    private String deleted;
    @TableField(exist = false)
    private String paymentNumber;
    @TableField(exist = false)
    private String receivedAmount;

    @TableField(exist = false)
    private String customerName;

    @TableField(exist = false)
    private Date postingDate;
    @TableField(exist = false)
    private String paymentCurrency;
    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private List<FcPaymentClaimDetail> details;


}
