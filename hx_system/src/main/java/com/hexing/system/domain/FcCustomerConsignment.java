package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户收货信息表
 *
 * @TableName fc_customer_consignment
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_customer_consignment")
public class FcCustomerConsignment extends BaseEntity {

    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 收货人名称
     */
    private String name;
    /**
     * 收货人电话
     */
    private String phone;
    /**
     * 收货人地址
     */
    private String address;

    private String location;
    private String deleted;


}
