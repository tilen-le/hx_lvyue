package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 回款单表
 *
 * @TableName fc_payment
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_payment")
public class FcPayment extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 客户回款编号
     */
    private String paymentNumber;
    /**
     * 凭证编号
     */
    private String documentNumber;
    /**
     * 公司名称
     */
    private String corporateName;
    /**
     * 客户名称
     */
    private String customerName;

    private String cusCode;
    /**
     * 回款币种
     */
    private String paymentCurrency;
    /**
     * 到账金额
     */
    private BigDecimal receivedAmount;
    /**
     * 负责人
     */
    private Date diretor;
    /**
     * 过账日期
     */
    private Date postingDate;
    /**
     * 回款已分配金额
     */
    private BigDecimal allocatedAmount;
    /**
     * 回款未分配金额
     */
    private BigDecimal undistributedAmount;
    /**
     * sap创建日期
     */
    private Date sapCreateDate;
    private String deleted;

}
