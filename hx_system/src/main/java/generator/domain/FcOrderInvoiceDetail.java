package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 开票单明细表
* @TableName fc_order_invoice_detail
*/
public class FcOrderInvoiceDetail implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 开票单id
    */
    @NotNull(message="[开票单id]不能为空")
    @ApiModelProperty("开票单id")
    private Integer invoiceId;
    /**
    * 产品id
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("产品id")
    @Length(max= 255,message="编码长度不能超过255")
    private String orderProductId;
    /**
    * 单位
    */
    @NotNull(message="[单位]不能为空")
    @ApiModelProperty("单位")
    private Integer unit;
    /**
    * 财务软件编码
    */
    @NotNull(message="[财务软件编码]不能为空")
    @ApiModelProperty("财务软件编码")
    private Integer sapFinancialCode;
    /**
    * 申请开票数量
    */
    @NotNull(message="[申请开票数量]不能为空")
    @ApiModelProperty("申请开票数量")
    private Integer appliedQuantity;
    /**
    * 开票含税单价
    */
    @ApiModelProperty("开票含税单价")
    private BigDecimal invoicingUnitPriceWithTax;
    /**
    * 客户物料名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("客户物料名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String customerMaterialName;
    /**
    * 客户规格名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("客户规格名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String customerSpecName;
    /**
    * 开票含税金额合计
    */
    @ApiModelProperty("开票含税金额合计")
    private BigDecimal invoicingAmountWithTax;
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
    * 开票单id
    */
    private void setInvoiceId(Integer invoiceId){
    this.invoiceId = invoiceId;
    }

    /**
    * 产品id
    */
    private void setOrderProductId(String orderProductId){
    this.orderProductId = orderProductId;
    }

    /**
    * 单位
    */
    private void setUnit(Integer unit){
    this.unit = unit;
    }

    /**
    * 财务软件编码
    */
    private void setSapFinancialCode(Integer sapFinancialCode){
    this.sapFinancialCode = sapFinancialCode;
    }

    /**
    * 申请开票数量
    */
    private void setAppliedQuantity(Integer appliedQuantity){
    this.appliedQuantity = appliedQuantity;
    }

    /**
    * 开票含税单价
    */
    private void setInvoicingUnitPriceWithTax(BigDecimal invoicingUnitPriceWithTax){
    this.invoicingUnitPriceWithTax = invoicingUnitPriceWithTax;
    }

    /**
    * 客户物料名称
    */
    private void setCustomerMaterialName(String customerMaterialName){
    this.customerMaterialName = customerMaterialName;
    }

    /**
    * 客户规格名称
    */
    private void setCustomerSpecName(String customerSpecName){
    this.customerSpecName = customerSpecName;
    }

    /**
    * 开票含税金额合计
    */
    private void setInvoicingAmountWithTax(BigDecimal invoicingAmountWithTax){
    this.invoicingAmountWithTax = invoicingAmountWithTax;
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
    * 开票单id
    */
    private Integer getInvoiceId(){
    return this.invoiceId;
    }

    /**
    * 产品id
    */
    private String getOrderProductId(){
    return this.orderProductId;
    }

    /**
    * 单位
    */
    private Integer getUnit(){
    return this.unit;
    }

    /**
    * 财务软件编码
    */
    private Integer getSapFinancialCode(){
    return this.sapFinancialCode;
    }

    /**
    * 申请开票数量
    */
    private Integer getAppliedQuantity(){
    return this.appliedQuantity;
    }

    /**
    * 开票含税单价
    */
    private BigDecimal getInvoicingUnitPriceWithTax(){
    return this.invoicingUnitPriceWithTax;
    }

    /**
    * 客户物料名称
    */
    private String getCustomerMaterialName(){
    return this.customerMaterialName;
    }

    /**
    * 客户规格名称
    */
    private String getCustomerSpecName(){
    return this.customerSpecName;
    }

    /**
    * 开票含税金额合计
    */
    private BigDecimal getInvoicingAmountWithTax(){
    return this.invoicingAmountWithTax;
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