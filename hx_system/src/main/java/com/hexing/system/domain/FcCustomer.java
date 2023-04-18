package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户表
 *
 * @TableName fc_customer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_customer")
public class FcCustomer extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 客户名称
     */
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
}
