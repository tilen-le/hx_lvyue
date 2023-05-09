package com.hexing.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentClaimVO {

    private Long claimId;

    /**
     * 认领单号
     */
    private String claimNumber;

    /**
     * 认领币种
     */
    private String claimCurrency;
    /**
     * 同步SAP时间
     */
    private Date syncSapTime;

    /**
     * 认领金额
     */
    private String claimAmount;

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
     * 回款类型
     */
    private String paymentType;
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
}
