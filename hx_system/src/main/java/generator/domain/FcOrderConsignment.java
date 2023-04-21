package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 发货单表
* @TableName fc_order_consignment
*/
public class FcOrderConsignment implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Object id;
    /**
    * 发货单编号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("发货单编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String consigmentNumber;
    /**
    * 订单主键
    */
    @NotNull(message="[订单主键]不能为空")
    @ApiModelProperty("订单主键")
    private Integer orderId;
    /**
    * 发货原因
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("发货原因")
    @Length(max= 255,message="编码长度不能超过255")
    private String reason;
    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】
    */
    @NotNull(message="[审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】]不能为空")
    @ApiModelProperty("审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】")
    private Integer approvalStatus;
    /**
    * 是否逾期【0：未逾期 1.已逾期】
    */
    @ApiModelProperty("是否逾期【0：未逾期 1.已逾期】")
    private String isLatePayment;
    /**
    * 运输方式【0。无、1.快递、2.物流、3.其他】
    */
    @ApiModelProperty("运输方式【0。无、1.快递、2.物流、3.其他】")
    private Integer transType;
    /**
    * 是否备表直发【0.否 1.是】
    */
    @ApiModelProperty("是否备表直发【0.否 1.是】")
    private String isReserveSend;
    /**
    * 配件是否单独包装【0.无、1.是、2.否】
    */
    @ApiModelProperty("配件是否单独包装【0.无、1.是、2.否】")
    private Integer isSeparatePackaging;
    /**
    * 预计验收日期
    */
    @ApiModelProperty("预计验收日期")
    private Date expectedCheckDate;
    /**
    * 预计质保日期
    */
    @ApiModelProperty("预计质保日期")
    private Date expectedWarrantyDate;
    /**
    * 预计到货日期
    */
    @ApiModelProperty("预计到货日期")
    private Date expectedArrivalDate;
    /**
    * 出厂日期
    */
    @ApiModelProperty("出厂日期")
    private Date productDate;
    /**
    * 备注
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("备注")
    @Length(max= 255,message="编码长度不能超过255")
    private String remark;
    /**
    * 收货人id
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收货人id")
    @Length(max= 255,message="编码长度不能超过255")
    private String consigneeId;
    /**
    * 发货人code
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("发货人code")
    @Length(max= 255,message="编码长度不能超过255")
    private String consignorCode;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String createBy;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String updateBy;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date createTime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date updateTime;
    /**
    * 
    */
    @ApiModelProperty("")
    private String deleted;

    /**
    * 主键
    */
    private void setId(Object id){
    this.id = id;
    }

    /**
    * 发货单编号
    */
    private void setConsigmentNumber(String consigmentNumber){
    this.consigmentNumber = consigmentNumber;
    }

    /**
    * 订单主键
    */
    private void setOrderId(Integer orderId){
    this.orderId = orderId;
    }

    /**
    * 发货原因
    */
    private void setReason(String reason){
    this.reason = reason;
    }

    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】
    */
    private void setApprovalStatus(Integer approvalStatus){
    this.approvalStatus = approvalStatus;
    }

    /**
    * 是否逾期【0：未逾期 1.已逾期】
    */
    private void setIsLatePayment(String isLatePayment){
    this.isLatePayment = isLatePayment;
    }

    /**
    * 运输方式【0。无、1.快递、2.物流、3.其他】
    */
    private void setTransType(Integer transType){
    this.transType = transType;
    }

    /**
    * 是否备表直发【0.否 1.是】
    */
    private void setIsReserveSend(String isReserveSend){
    this.isReserveSend = isReserveSend;
    }

    /**
    * 配件是否单独包装【0.无、1.是、2.否】
    */
    private void setIsSeparatePackaging(Integer isSeparatePackaging){
    this.isSeparatePackaging = isSeparatePackaging;
    }

    /**
    * 预计验收日期
    */
    private void setExpectedCheckDate(Date expectedCheckDate){
    this.expectedCheckDate = expectedCheckDate;
    }

    /**
    * 预计质保日期
    */
    private void setExpectedWarrantyDate(Date expectedWarrantyDate){
    this.expectedWarrantyDate = expectedWarrantyDate;
    }

    /**
    * 预计到货日期
    */
    private void setExpectedArrivalDate(Date expectedArrivalDate){
    this.expectedArrivalDate = expectedArrivalDate;
    }

    /**
    * 出厂日期
    */
    private void setProductDate(Date productDate){
    this.productDate = productDate;
    }

    /**
    * 备注
    */
    private void setRemark(String remark){
    this.remark = remark;
    }

    /**
    * 收货人id
    */
    private void setConsigneeId(String consigneeId){
    this.consigneeId = consigneeId;
    }

    /**
    * 发货人code
    */
    private void setConsignorCode(String consignorCode){
    this.consignorCode = consignorCode;
    }

    /**
    * 
    */
    private void setCreateBy(String createBy){
    this.createBy = createBy;
    }

    /**
    * 
    */
    private void setUpdateBy(String updateBy){
    this.updateBy = updateBy;
    }

    /**
    * 
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }

    /**
    * 
    */
    private void setUpdateTime(Date updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 
    */
    private void setDeleted(String deleted){
    this.deleted = deleted;
    }


    /**
    * 主键
    */
    private Object getId(){
    return this.id;
    }

    /**
    * 发货单编号
    */
    private String getConsigmentNumber(){
    return this.consigmentNumber;
    }

    /**
    * 订单主键
    */
    private Integer getOrderId(){
    return this.orderId;
    }

    /**
    * 发货原因
    */
    private String getReason(){
    return this.reason;
    }

    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿,4.撤销审批】
    */
    private Integer getApprovalStatus(){
    return this.approvalStatus;
    }

    /**
    * 是否逾期【0：未逾期 1.已逾期】
    */
    private String getIsLatePayment(){
    return this.isLatePayment;
    }

    /**
    * 运输方式【0。无、1.快递、2.物流、3.其他】
    */
    private Integer getTransType(){
    return this.transType;
    }

    /**
    * 是否备表直发【0.否 1.是】
    */
    private String getIsReserveSend(){
    return this.isReserveSend;
    }

    /**
    * 配件是否单独包装【0.无、1.是、2.否】
    */
    private Integer getIsSeparatePackaging(){
    return this.isSeparatePackaging;
    }

    /**
    * 预计验收日期
    */
    private Date getExpectedCheckDate(){
    return this.expectedCheckDate;
    }

    /**
    * 预计质保日期
    */
    private Date getExpectedWarrantyDate(){
    return this.expectedWarrantyDate;
    }

    /**
    * 预计到货日期
    */
    private Date getExpectedArrivalDate(){
    return this.expectedArrivalDate;
    }

    /**
    * 出厂日期
    */
    private Date getProductDate(){
    return this.productDate;
    }

    /**
    * 备注
    */
    private String getRemark(){
    return this.remark;
    }

    /**
    * 收货人id
    */
    private String getConsigneeId(){
    return this.consigneeId;
    }

    /**
    * 发货人code
    */
    private String getConsignorCode(){
    return this.consignorCode;
    }

    /**
    * 
    */
    private String getCreateBy(){
    return this.createBy;
    }

    /**
    * 
    */
    private String getUpdateBy(){
    return this.updateBy;
    }

    /**
    * 
    */
    private Date getCreateTime(){
    return this.createTime;
    }

    /**
    * 
    */
    private Date getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 
    */
    private String getDeleted(){
    return this.deleted;
    }

}
