package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 卖方银行
 *
 * @TableName fc_sale_bank
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_sale_bank")
public class FcSaleBank extends BaseEntity {

    /**
     *
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 受益人银行
     */
    private String bankName;
    /**
     * 1-国内，2-国外
     */
    private String isInternational;
    /**
     * 银行国外卡号
     */
    private String inBankCode;
    /**
     * 银行国内行号
     */
    private String outBankCode;
    /**
     * 受益人
     */
    private String beneficiary;
    /**
     *
     */
    private String status;
    private String deleted;


}
