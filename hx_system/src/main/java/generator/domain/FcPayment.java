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
* 回款单表
* @TableName fc_payment
*/
public class FcPayment implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 客户回款编号
    */
    @NotBlank(message="[客户回款编号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("客户回款编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String paymentNumber;
    /**
    * 凭证编号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("凭证编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String documentNumber;
    /**
    * 公司名称
    */
    @NotBlank(message="[公司名称]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("公司名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String corporateName;
    /**
    * 客户名称
    */
    @NotBlank(message="[客户名称]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("客户名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String customerName;
    /**
    * 回款币种
    */
    @NotBlank(message="[回款币种]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("回款币种")
    @Length(max= 255,message="编码长度不能超过255")
    private String paymentCurrency;
    /**
    * 到账金额
    */
    @ApiModelProperty("到账金额")
    private BigDecimal receivedAmount;
    /**
    * 负责人
    */
    @ApiModelProperty("负责人")
    private Date diretor;
    /**
    * 过账日期
    */
    @ApiModelProperty("过账日期")
    private Date postingDate;
    /**
    * 回款已分配金额
    */
    @ApiModelProperty("回款已分配金额")
    private BigDecimal allocatedAmount;
    /**
    * 回款未分配金额
    */
    @ApiModelProperty("回款未分配金额")
    private BigDecimal undistributedAmount;
    /**
    * sap创建日期
    */
    @ApiModelProperty("sap创建日期")
    private Date sapCreateDate;
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
    * 客户回款编号
    */
    private void setPaymentNumber(String paymentNumber){
    this.paymentNumber = paymentNumber;
    }

    /**
    * 凭证编号
    */
    private void setDocumentNumber(String documentNumber){
    this.documentNumber = documentNumber;
    }

    /**
    * 公司名称
    */
    private void setCorporateName(String corporateName){
    this.corporateName = corporateName;
    }

    /**
    * 客户名称
    */
    private void setCustomerName(String customerName){
    this.customerName = customerName;
    }

    /**
    * 回款币种
    */
    private void setPaymentCurrency(String paymentCurrency){
    this.paymentCurrency = paymentCurrency;
    }

    /**
    * 到账金额
    */
    private void setReceivedAmount(BigDecimal receivedAmount){
    this.receivedAmount = receivedAmount;
    }

    /**
    * 负责人
    */
    private void setDiretor(Date diretor){
    this.diretor = diretor;
    }

    /**
    * 过账日期
    */
    private void setPostingDate(Date postingDate){
    this.postingDate = postingDate;
    }

    /**
    * 回款已分配金额
    */
    private void setAllocatedAmount(BigDecimal allocatedAmount){
    this.allocatedAmount = allocatedAmount;
    }

    /**
    * 回款未分配金额
    */
    private void setUndistributedAmount(BigDecimal undistributedAmount){
    this.undistributedAmount = undistributedAmount;
    }

    /**
    * sap创建日期
    */
    private void setSapCreateDate(Date sapCreateDate){
    this.sapCreateDate = sapCreateDate;
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
    * 客户回款编号
    */
    private String getPaymentNumber(){
    return this.paymentNumber;
    }

    /**
    * 凭证编号
    */
    private String getDocumentNumber(){
    return this.documentNumber;
    }

    /**
    * 公司名称
    */
    private String getCorporateName(){
    return this.corporateName;
    }

    /**
    * 客户名称
    */
    private String getCustomerName(){
    return this.customerName;
    }

    /**
    * 回款币种
    */
    private String getPaymentCurrency(){
    return this.paymentCurrency;
    }

    /**
    * 到账金额
    */
    private BigDecimal getReceivedAmount(){
    return this.receivedAmount;
    }

    /**
    * 负责人
    */
    private Date getDiretor(){
    return this.diretor;
    }

    /**
    * 过账日期
    */
    private Date getPostingDate(){
    return this.postingDate;
    }

    /**
    * 回款已分配金额
    */
    private BigDecimal getAllocatedAmount(){
    return this.allocatedAmount;
    }

    /**
    * 回款未分配金额
    */
    private BigDecimal getUndistributedAmount(){
    return this.undistributedAmount;
    }

    /**
    * sap创建日期
    */
    private Date getSapCreateDate(){
    return this.sapCreateDate;
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
