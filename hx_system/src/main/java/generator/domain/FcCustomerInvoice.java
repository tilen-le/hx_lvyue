package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 客户开票信息表
* @TableName fc_customer_invoice
*/
public class FcCustomerInvoice implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 客户id
    */
    @NotNull(message="[客户id]不能为空")
    @ApiModelProperty("客户id")
    private Long customerId;
    /**
    * 开户行
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("开户行")
    @Length(max= 255,message="编码长度不能超过255")
    private String openingBank;
    /**
    * 账号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("账号")
    @Length(max= 255,message="编码长度不能超过255")
    private String account;
    /**
    * 地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String adress;
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
    * 客户id
    */
    private void setCustomerId(Long customerId){
    this.customerId = customerId;
    }

    /**
    * 开户行
    */
    private void setOpeningBank(String openingBank){
    this.openingBank = openingBank;
    }

    /**
    * 账号
    */
    private void setAccount(String account){
    this.account = account;
    }

    /**
    * 地址
    */
    private void setAdress(String adress){
    this.adress = adress;
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
    * 客户id
    */
    private Long getCustomerId(){
    return this.customerId;
    }

    /**
    * 开户行
    */
    private String getOpeningBank(){
    return this.openingBank;
    }

    /**
    * 账号
    */
    private String getAccount(){
    return this.account;
    }

    /**
    * 地址
    */
    private String getAdress(){
    return this.adress;
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
