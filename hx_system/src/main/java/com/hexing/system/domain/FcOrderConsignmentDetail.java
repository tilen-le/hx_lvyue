package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 发货明细表
* @TableName fc_order_consignment_detail
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_consignment_detail")
public class FcOrderConsignmentDetail extends BaseEntity {

    /**
    * 主键
    */
    @TableId(value = "id")
    private Long id;
    /**
    * 发货单主键
    */
    private String consignmentId;
    /**
    * 产品id
    */
    private String orderProductId;
    /**
    * 产品数量
    */
    private Integer productNum;
    /**
    * 技术要求
    */
    private String technicalRequirement;
    private String deleted;


}
