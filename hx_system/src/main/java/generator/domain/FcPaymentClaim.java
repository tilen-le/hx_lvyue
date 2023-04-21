package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 回款认领单表
* @TableName fc_payment_claim
*/
public class FcPaymentClaim implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 回款单id
    */
    @NotNull(message="[回款单id]不能为空")
    @ApiModelProperty("回款单id")
    private Integer paymentId;
    /**
    * 认领单号
    */
    @NotBlank(message="[认领单号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("认领单号")
    @Length(max= 255,message="编码长度不能超过255")
    private String claimNumber;
    /**
    * 同步SAP信息时间
    */
    @ApiModelProperty("同步SAP信息时间")
    private Date syncSapInfoTime;
    /**
    * 同步SAP信息
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("同步SAP信息")
    @Length(max= 255,message="编码长度不能超过255")
    private String syncSapInfo;
    /**
    * 同步SAP状态
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("同步SAP状态")
    @Length(max= 255,message="编码长度不能超过255")
    private String syncSapStatus;
    /**
    * 同步sap时间
    */
    @ApiModelProperty("同步sap时间")
    private Date syncSapTime;
    /**
    * 撤销同步SAP时间
    */
    @ApiModelProperty("撤销同步SAP时间")
    private Date cancelSapTime;
    /**
    * 撤销同步sap信息
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("撤销同步sap信息")
    @Length(max= 255,message="编码长度不能超过255")
    private String cancelSapInfo;
    /**
    * 撤销同步sap信息时间
    */
    @ApiModelProperty("撤销同步sap信息时间")
    private Date cancelSapInfoTime;
    /**
    * 认领单状态【1.生效 0.失效】
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("认领单状态【1.生效 0.失效】")
    @Length(max= 255,message="编码长度不能超过255")
    private String status;
    /**
    * 认领人
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("认领人")
    @Length(max= 255,message="编码长度不能超过255")
    private String claimCurrency;
    /**
    * 认领金额
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("认领金额")
    @Length(max= 255,message="编码长度不能超过255")
    private String claimAmount;
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
    * 回款单id
    */
    private void setPaymentId(Integer paymentId){
    this.paymentId = paymentId;
    }

    /**
    * 认领单号
    */
    private void setClaimNumber(String claimNumber){
    this.claimNumber = claimNumber;
    }

    /**
    * 同步SAP信息时间
    */
    private void setSyncSapInfoTime(Date syncSapInfoTime){
    this.syncSapInfoTime = syncSapInfoTime;
    }

    /**
    * 同步SAP信息
    */
    private void setSyncSapInfo(String syncSapInfo){
    this.syncSapInfo = syncSapInfo;
    }

    /**
    * 同步SAP状态
    */
    private void setSyncSapStatus(String syncSapStatus){
    this.syncSapStatus = syncSapStatus;
    }

    /**
    * 同步sap时间
    */
    private void setSyncSapTime(Date syncSapTime){
    this.syncSapTime = syncSapTime;
    }

    /**
    * 撤销同步SAP时间
    */
    private void setCancelSapTime(Date cancelSapTime){
    this.cancelSapTime = cancelSapTime;
    }

    /**
    * 撤销同步sap信息
    */
    private void setCancelSapInfo(String cancelSapInfo){
    this.cancelSapInfo = cancelSapInfo;
    }

    /**
    * 撤销同步sap信息时间
    */
    private void setCancelSapInfoTime(Date cancelSapInfoTime){
    this.cancelSapInfoTime = cancelSapInfoTime;
    }

    /**
    * 认领单状态【1.生效 0.失效】
    */
    private void setStatus(String status){
    this.status = status;
    }

    /**
    * 认领人
    */
    private void setClaimCurrency(String claimCurrency){
    this.claimCurrency = claimCurrency;
    }

    /**
    * 认领金额
    */
    private void setClaimAmount(String claimAmount){
    this.claimAmount = claimAmount;
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
    * 回款单id
    */
    private Integer getPaymentId(){
    return this.paymentId;
    }

    /**
    * 认领单号
    */
    private String getClaimNumber(){
    return this.claimNumber;
    }

    /**
    * 同步SAP信息时间
    */
    private Date getSyncSapInfoTime(){
    return this.syncSapInfoTime;
    }

    /**
    * 同步SAP信息
    */
    private String getSyncSapInfo(){
    return this.syncSapInfo;
    }

    /**
    * 同步SAP状态
    */
    private String getSyncSapStatus(){
    return this.syncSapStatus;
    }

    /**
    * 同步sap时间
    */
    private Date getSyncSapTime(){
    return this.syncSapTime;
    }

    /**
    * 撤销同步SAP时间
    */
    private Date getCancelSapTime(){
    return this.cancelSapTime;
    }

    /**
    * 撤销同步sap信息
    */
    private String getCancelSapInfo(){
    return this.cancelSapInfo;
    }

    /**
    * 撤销同步sap信息时间
    */
    private Date getCancelSapInfoTime(){
    return this.cancelSapInfoTime;
    }

    /**
    * 认领单状态【1.生效 0.失效】
    */
    private String getStatus(){
    return this.status;
    }

    /**
    * 认领人
    */
    private String getClaimCurrency(){
    return this.claimCurrency;
    }

    /**
    * 认领金额
    */
    private String getClaimAmount(){
    return this.claimAmount;
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
