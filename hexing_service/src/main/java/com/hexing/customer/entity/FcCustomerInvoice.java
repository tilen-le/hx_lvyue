package com.hexing.customer.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.tenant.core.db.TenantBaseDO;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
* 客户开票信息表
* @TableName fc_customer_invoice
*/
@TableName(value = "fc_customer_invoice", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcCustomerInvoice extends TenantBaseDO {

    /**
    * 主键
    */
    @TableId(value = "id")
    private Integer id;
    /**
    * 客户id
    */
    @NotNull(message="[客户id]不能为空")
    private Integer customerId;
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
    private String adress;
    private String deleted;

}
