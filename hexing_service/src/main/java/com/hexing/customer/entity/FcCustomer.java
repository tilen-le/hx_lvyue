package com.hexing.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.tenant.core.db.TenantBaseDO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 客户表
 *
 * @TableName fc_customer
 */
@TableName(value = "fc_customer", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcCustomer extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;
    /**
     * 编码
     */
    @NotBlank(message = "客户编码不能为空")
    private String code;
    /**
     * 客户名称
     */
    @NotBlank(message = "客户昵称不能为空")
    private String name;
    /**
     * 信用额度
     */
    private String creditLimit;
    /**
     * 国籍
     */
    private String nation;
    /**
     * 状态【0.禁用 1.启用】
     */
    private String status;
    /**
     *
     */
    private String deleted;

    @TableField(exist = false)
    private String receiver;

    @TableField(exist = false)
    private String consPhone;

    @TableField(exist = false)
    private String consAddress;

    @TableField(exist = false)
    private List<FcCustomerConsignment> receiverIfs;



}
