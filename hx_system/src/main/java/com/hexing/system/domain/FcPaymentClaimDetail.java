package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 回款认领明细表
 *
 * @TableName fc_payment_claim_detail
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_payment_claim_detail")
public class FcPaymentClaimDetail extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 认领单id
     */
    private Long claimId;
    /**
     * 认领订单id
     */
    private Long orderId;
    /**
     * 认领里程碑id
     */
    private String milestonesId;
    /**
     * 认领币种
     */
    private String currency;
    /**
     * 认领金额
     */
    private BigDecimal amount;

    private String deleted;


    @TableField(exist = false)
    private String orderNumber;

    @TableField(exist = false)
    private String orderTitle;
    @TableField(exist = false)
    private String claimNumber;
    @TableField(exist = false)
    private String receivedAmount;
    @TableField(exist = false)
    private String undistributedAmount;
    @TableField(exist = false)
    private String allocatedAmount;


}
