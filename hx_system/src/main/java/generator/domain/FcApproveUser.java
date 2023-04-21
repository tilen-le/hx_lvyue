package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 审批人表
* @TableName fc_approve_user
*/
public class FcApproveUser implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 审批标题
    */
    @NotNull(message="[审批标题]不能为空")
    @ApiModelProperty("审批标题")
    private Integer optUserId;
    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    @ApiModelProperty("类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】")
    private Integer type;
    /**
    * 事业部
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("事业部")
    @Length(max= 255,message="编码长度不能超过255")
    private String businessUnit;
    /**
    * 工厂
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("工厂")
    @Length(max= 255,message="编码长度不能超过255")
    private String factory;
    /**
    * 申请时间
    */
    @ApiModelProperty("申请时间")
    private Date requestTime;
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
    * 审批标题
    */
    private void setOptUserId(Integer optUserId){
    this.optUserId = optUserId;
    }

    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 事业部
    */
    private void setBusinessUnit(String businessUnit){
    this.businessUnit = businessUnit;
    }

    /**
    * 工厂
    */
    private void setFactory(String factory){
    this.factory = factory;
    }

    /**
    * 申请时间
    */
    private void setRequestTime(Date requestTime){
    this.requestTime = requestTime;
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
    * 审批标题
    */
    private Integer getOptUserId(){
    return this.optUserId;
    }

    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 事业部
    */
    private String getBusinessUnit(){
    return this.businessUnit;
    }

    /**
    * 工厂
    */
    private String getFactory(){
    return this.factory;
    }

    /**
    * 申请时间
    */
    private Date getRequestTime(){
    return this.requestTime;
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
