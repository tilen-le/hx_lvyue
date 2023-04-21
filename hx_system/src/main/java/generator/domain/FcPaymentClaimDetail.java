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
* 回款认领明细表
* @TableName fc_payment_claim_detail
*/
public class FcPaymentClaimDetail implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 认领单id
    */
    @NotNull(message="[认领单id]不能为空")
    @ApiModelProperty("认领单id")
    private Integer claimId;
    /**
    * 认领订单id
    */
    @ApiModelProperty("认领订单id")
    private Integer orderId;
    /**
    * 认领里程碑id
    */
    @NotNull(message="[认领里程碑id]不能为空")
    @ApiModelProperty("认领里程碑id")
    private Integer milestonesId;
    /**
    * 认领币种
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("认领币种")
    @Length(max= 255,message="编码长度不能超过255")
    private String currency;
    /**
    * 认领金额
    */
    @ApiModelProperty("认领金额")
    private BigDecimal amount;
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
    * 认领单id
    */
    private void setClaimId(Integer claimId){
    this.claimId = claimId;
    }

    /**
    * 认领订单id
    */
    private void setOrderId(Integer orderId){
    this.orderId = orderId;
    }

    /**
    * 认领里程碑id
    */
    private void setMilestonesId(Integer milestonesId){
    this.milestonesId = milestonesId;
    }

    /**
    * 认领币种
    */
    private void setCurrency(String currency){
    this.currency = currency;
    }

    /**
    * 认领金额
    */
    private void setAmount(BigDecimal amount){
    this.amount = amount;
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
    * 认领单id
    */
    private Integer getClaimId(){
    return this.claimId;
    }

    /**
    * 认领订单id
    */
    private Integer getOrderId(){
    return this.orderId;
    }

    /**
    * 认领里程碑id
    */
    private Integer getMilestonesId(){
    return this.milestonesId;
    }

    /**
    * 认领币种
    */
    private String getCurrency(){
    return this.currency;
    }

    /**
    * 认领金额
    */
    private BigDecimal getAmount(){
    return this.amount;
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
