package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 收款里程碑
* @TableName fc_order_pay_milestone
*/
public class FcOrderPayMilestone implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 订单id
    */
    @NotNull(message="[订单id]不能为空")
    @ApiModelProperty("订单id")
    private Integer orderId;
    /**
    * 编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String code;
    /**
    * 里程碑类型
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("里程碑类型")
    @Length(max= 255,message="编码长度不能超过255")
    private String type;
    /**
    * 期待回款日期
    */
    @ApiModelProperty("期待回款日期")
    private Integer expectPayDate;
    /**
    * 期待回款比例
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("期待回款比例")
    @Length(max= 255,message="编码长度不能超过255")
    private String expectPayScale;
    /**
    * 剩余回款金额
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("剩余回款金额")
    @Length(max= 255,message="编码长度不能超过255")
    private String surplusPayAmount;
    /**
    * 计划回款金额
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("计划回款金额")
    @Length(max= 255,message="编码长度不能超过255")
    private String planPayAmount;
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
    * 订单id
    */
    private void setOrderId(Integer orderId){
    this.orderId = orderId;
    }

    /**
    * 编码
    */
    private void setCode(String code){
    this.code = code;
    }

    /**
    * 里程碑类型
    */
    private void setType(String type){
    this.type = type;
    }

    /**
    * 期待回款日期
    */
    private void setExpectPayDate(Integer expectPayDate){
    this.expectPayDate = expectPayDate;
    }

    /**
    * 期待回款比例
    */
    private void setExpectPayScale(String expectPayScale){
    this.expectPayScale = expectPayScale;
    }

    /**
    * 剩余回款金额
    */
    private void setSurplusPayAmount(String surplusPayAmount){
    this.surplusPayAmount = surplusPayAmount;
    }

    /**
    * 计划回款金额
    */
    private void setPlanPayAmount(String planPayAmount){
    this.planPayAmount = planPayAmount;
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
    * 订单id
    */
    private Integer getOrderId(){
    return this.orderId;
    }

    /**
    * 编码
    */
    private String getCode(){
    return this.code;
    }

    /**
    * 里程碑类型
    */
    private String getType(){
    return this.type;
    }

    /**
    * 期待回款日期
    */
    private Integer getExpectPayDate(){
    return this.expectPayDate;
    }

    /**
    * 期待回款比例
    */
    private String getExpectPayScale(){
    return this.expectPayScale;
    }

    /**
    * 剩余回款金额
    */
    private String getSurplusPayAmount(){
    return this.surplusPayAmount;
    }

    /**
    * 计划回款金额
    */
    private String getPlanPayAmount(){
    return this.planPayAmount;
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
