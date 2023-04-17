package com.hexing.customer.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.tenant.core.db.TenantBaseDO;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 客户收货信息表
 *
 * @TableName fc_customer_consignment
 */
@TableName(value = "fc_customer_consignment", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcCustomerConsignment extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;
    /**
     * 客户id
     */
    @NotBlank(message = "客户id不能为空")
    private Integer customerId;
    /**
     * 收货人名称
     */
    @NotBlank(message = "客收货人名称不能为空")
    private String name;
    /**
     * 收货人电话
     */
    @NotBlank(message = "客收货人电话不能为空")
    private String phone;
    /**
     * 收货人地址
     */
    @NotBlank(message = "客收货人地址不能为空")
    private String adress;

    private String deleted;

}
