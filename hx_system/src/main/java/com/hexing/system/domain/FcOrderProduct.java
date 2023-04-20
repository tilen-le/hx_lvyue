package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 订单产品表
* @TableName fc_order_product
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_product")
public class FcOrderProduct extends BaseEntity {

    /**
    * 主键
    */
    @TableId(value = "id")
    private Long id;
    /**
    * 订单主键
    */
    private Long orderId;
    /**
    * sap明细号
    */
    private String sapDetailNumber;
    /**
    * 产品编号
    */
    private String productNumber;
    /**
    * 产品型号
    */
    private String productModel;
    /**
    * 产品名称
    */
    private String productName;
    /**
    * 数量
    */
    private String num;
    /**
    * 单价
    */
    private String unitPrice;
    /**
    * 入库数
    */
    private String inStorageNum;
    /**
    * 在途
    */
    private String inTransitNum;
    /**
    * 未发
    */
    private String notSentNum;
    private String deleted;

}
