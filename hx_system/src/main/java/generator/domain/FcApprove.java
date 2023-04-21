package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 审批表
* @TableName fc_approve
*/
public class FcApprove implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 审批标题
    */
    @NotBlank(message="[审批标题]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("审批标题")
    @Length(max= 255,message="编码长度不能超过255")
    private String title;
    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    @ApiModelProperty("类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】")
    private Integer type;
    /**
    * 当前节点
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("当前节点")
    @Length(max= 255,message="编码长度不能超过255")
    private String currentNode;
    /**
    * 发起人
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("发起人")
    @Length(max= 255,message="编码长度不能超过255")
    private String originator;
    /**
    * 状态
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("状态")
    @Length(max= 255,message="编码长度不能超过255")
    private String status;
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
    private void setTitle(String title){
    this.title = title;
    }

    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 当前节点
    */
    private void setCurrentNode(String currentNode){
    this.currentNode = currentNode;
    }

    /**
    * 发起人
    */
    private void setOriginator(String originator){
    this.originator = originator;
    }

    /**
    * 状态
    */
    private void setStatus(String status){
    this.status = status;
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
    private String getTitle(){
    return this.title;
    }

    /**
    * 类型【1.发货单审批 2.开票审批 3.开票撤销审批 4.】
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 当前节点
    */
    private String getCurrentNode(){
    return this.currentNode;
    }

    /**
    * 发起人
    */
    private String getOriginator(){
    return this.originator;
    }

    /**
    * 状态
    */
    private String getStatus(){
    return this.status;
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
