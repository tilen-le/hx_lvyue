package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户开票信息表
 *
 * @TableName fc_customer_invoice
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_customer_invoice")
public class FcCustomerInvoice extends BaseEntity {

    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 开户行
     */
    private String openingBank;
    /**
     * 账号
     */
    private String account;
    /**
     * 地址
     */
    private String address;
    private String deleted;

}
