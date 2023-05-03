package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 发货计划关联报关信息表
 *
 * @TableName fc_shipping_plan_report_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_shipping_plan")
public class FcShippingPlanReportInfo extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 序号
     */
    private Integer sequence;
    /**
     * 货物描述（中文)
     */
    private String goodsDescCn;
    /**
     * 货物描述（英文）
     */
    private String goodsDescEn;
    /**
     * 报关数量
     */
    private Integer num;
    /**
     * 报关金额
     */
    private BigDecimal amount;
    /**
     * 品牌型号
     */
    private String brandModel;
    /**
     * 包装件数
     */
    private String packNumber;
    /**
     * 包装种类【：纸箱、木箱、木托、塑料托、混合】
     */
    private String packagingType;
    /**
     * 毛重
     */
    private String grossWeight;
    /**
     * 净重
     */
    private String netWeight;
    /**
     * 体积（CBM)
     */
    private String volume;
    private String deleted;


}