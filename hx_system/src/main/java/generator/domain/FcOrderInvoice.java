package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 开票单表
* @TableName fc_order_invoice
*/
public class FcOrderInvoice implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 开票单编号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("开票单编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String invoiceNumber;
    /**
    * 订单id
    */
    @NotNull(message="[订单id]不能为空")
    @ApiModelProperty("订单id")
    private Integer orderId;
    /**
    * 发票类型
    */
    @NotNull(message="[发票类型]不能为空")
    @ApiModelProperty("发票类型")
    private Integer invoiceType;
    /**
    * 卖方银行
    */
    @NotNull(message="[卖方银行]不能为空")
    @ApiModelProperty("卖方银行")
    private Integer saleBank;
    /**
    * 收票方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收票方")
    @Length(max= 255,message="编码长度不能超过255")
    private String consigneeId;
    /**
    * 开户行
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("开户行")
    @Length(max= 255,message="编码长度不能超过255")
    private String openingBank;
    /**
    * 送货日期
    */
    @ApiModelProperty("送货日期")
    private Date arrivalDate;
    /**
    * 验收日期
    */
    @ApiModelProperty("验收日期")
    private Date checkDate;
    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿】
    */
    @ApiModelProperty("审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿】")
    private Integer approvalStatus;
    /**
    * 含税金额合计
    */
    @ApiModelProperty("含税金额合计")
    private BigDecimal totalAmountWithTax;
    /**
    * 不含税金额合计
    */
    @ApiModelProperty("不含税金额合计")
    private BigDecimal totalAmountWithoutTax;
    /**
    * 税额合计
    */
    @ApiModelProperty("税额合计")
    private BigDecimal tax;
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
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 开票单编号
    */
    private void setInvoiceNumber(String invoiceNumber){
    this.invoiceNumber = invoiceNumber;
    }

    /**
    * 订单id
    */
    private void setOrderId(Integer orderId){
    this.orderId = orderId;
    }

    /**
    * 发票类型
    */
    private void setInvoiceType(Integer invoiceType){
    this.invoiceType = invoiceType;
    }

    /**
    * 卖方银行
    */
    private void setSaleBank(Integer saleBank){
    this.saleBank = saleBank;
    }

    /**
    * 收票方
    */
    private void setConsigneeId(String consigneeId){
    this.consigneeId = consigneeId;
    }

    /**
    * 开户行
    */
    private void setOpeningBank(String openingBank){
    this.openingBank = openingBank;
    }

    /**
    * 送货日期
    */
    private void setArrivalDate(Date arrivalDate){
    this.arrivalDate = arrivalDate;
    }

    /**
    * 验收日期
    */
    private void setCheckDate(Date checkDate){
    this.checkDate = checkDate;
    }

    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿】
    */
    private void setApprovalStatus(Integer approvalStatus){
    this.approvalStatus = approvalStatus;
    }

    /**
    * 含税金额合计
    */
    private void setTotalAmountWithTax(BigDecimal totalAmountWithTax){
    this.totalAmountWithTax = totalAmountWithTax;
    }

    /**
    * 不含税金额合计
    */
    private void setTotalAmountWithoutTax(BigDecimal totalAmountWithoutTax){
    this.totalAmountWithoutTax = totalAmountWithoutTax;
    }

    /**
    * 税额合计
    */
    private void setTax(BigDecimal tax){
    this.tax = tax;
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
    private Integer getId(){
    return this.id;
    }

    /**
    * 开票单编号
    */
    private String getInvoiceNumber(){
    return this.invoiceNumber;
    }

    /**
    * 订单id
    */
    private Integer getOrderId(){
    return this.orderId;
    }

    /**
    * 发票类型
    */
    private Integer getInvoiceType(){
    return this.invoiceType;
    }

    /**
    * 卖方银行
    */
    private Integer getSaleBank(){
    return this.saleBank;
    }

    /**
    * 收票方
    */
    private String getConsigneeId(){
    return this.consigneeId;
    }

    /**
    * 开户行
    */
    private String getOpeningBank(){
    return this.openingBank;
    }

    /**
    * 送货日期
    */
    private Date getArrivalDate(){
    return this.arrivalDate;
    }

    /**
    * 验收日期
    */
    private Date getCheckDate(){
    return this.checkDate;
    }

    /**
    * 审批状态【0.待审批 1.审批通过 2.审批驳回 3.草稿】
    */
    private Integer getApprovalStatus(){
    return this.approvalStatus;
    }

    /**
    * 含税金额合计
    */
    private BigDecimal getTotalAmountWithTax(){
    return this.totalAmountWithTax;
    }

    /**
    * 不含税金额合计
    */
    private BigDecimal getTotalAmountWithoutTax(){
    return this.totalAmountWithoutTax;
    }

    /**
    * 税额合计
    */
    private BigDecimal getTax(){
    return this.tax;
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
