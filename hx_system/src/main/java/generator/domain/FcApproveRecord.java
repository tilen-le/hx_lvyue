package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 审批记录表
* @TableName fc_approve_record
*/
public class FcApproveRecord implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 审批id
    */
    @NotNull(message="[审批id]不能为空")
    @ApiModelProperty("审批id")
    private Integer approveId;
    /**
    * 审批结果【1.通过，2驳回】
    */
    @ApiModelProperty("审批结果【1.通过，2驳回】")
    private String result;
    /**
    * 审批操作人
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("审批操作人")
    @Length(max= 255,message="编码长度不能超过255")
    private String optUser;
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
    * 审批id
    */
    private void setApproveId(Integer approveId){
    this.approveId = approveId;
    }

    /**
    * 审批结果【1.通过，2驳回】
    */
    private void setResult(String result){
    this.result = result;
    }

    /**
    * 审批操作人
    */
    private void setOptUser(String optUser){
    this.optUser = optUser;
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
    * 审批id
    */
    private Integer getApproveId(){
    return this.approveId;
    }

    /**
    * 审批结果【1.通过，2驳回】
    */
    private String getResult(){
    return this.result;
    }

    /**
    * 审批操作人
    */
    private String getOptUser(){
    return this.optUser;
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
