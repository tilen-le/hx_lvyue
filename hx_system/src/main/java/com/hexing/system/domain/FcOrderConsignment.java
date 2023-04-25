package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 发货单表
 *
 * @TableName fc_order_consignment
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_order_consignment")
public class FcOrderConsignment extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 发货单编号
     */
    private String consigmentNumber;
    /**
     * 订单主键
     */
    private Integer orderId;
    /**
     * 发货原因
     */
    private String reason;
    /**
     * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】
     */
    private Integer approvalStatus;
    /**
     * 是否逾期【0：未逾期 1.已逾期】
     */
    private String isLatePayment;
    /**
     * 运输方式【0。无、1.快递、2.物流、3.其他】
     */
    private Integer transType;
    /**
     * 是否备表直发【0.否 1.是】
     */
    private String isReserveSend;
    /**
     * 配件是否单独包装【0.无、1.是、2.否】
     */
    private Integer isSeparatePackaging;
    /**
     * 预计验收日期
     */
    private Date expectedCheckDate;
    /**
     * 预计质保日期
     */
    private Date expectedWarrantyDate;
    /**
     * 预计到货日期
     */
    private Date expectedArrivalDate;
    /**
     * 出厂日期
     */
    private Date productDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 收货人id
     */
    private String consigneeId;
    /**
     * 发货人code
     */
    private String consignorCode;
    private String deleted;

    @TableField(exist = false)
    private List<FcOrderConsignmentDetail> products;


}
