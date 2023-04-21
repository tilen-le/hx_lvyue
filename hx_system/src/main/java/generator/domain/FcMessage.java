package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 消息通知表
* @TableName fc_message
*/
public class FcMessage implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 消息内容
    */
    @NotBlank(message="[消息内容]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("消息内容")
    @Length(max= 255,message="编码长度不能超过255")
    private String message;
    /**
    * 是否已读【0：未读，1已读】
    */
    @ApiModelProperty("是否已读【0：未读，1已读】")
    private String isRead;
    /**
    * 是否撤销【0：未撤销，1已撤销】
    */
    @ApiModelProperty("是否撤销【0：未撤销，1已撤销】")
    private String isCancel;
    /**
    * 消息处理人
    */
    @NotNull(message="[消息处理人]不能为空")
    @ApiModelProperty("消息处理人")
    private Integer handleUserId;
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
    * 消息内容
    */
    private void setMessage(String message){
    this.message = message;
    }

    /**
    * 是否已读【0：未读，1已读】
    */
    private void setIsRead(String isRead){
    this.isRead = isRead;
    }

    /**
    * 是否撤销【0：未撤销，1已撤销】
    */
    private void setIsCancel(String isCancel){
    this.isCancel = isCancel;
    }

    /**
    * 消息处理人
    */
    private void setHandleUserId(Integer handleUserId){
    this.handleUserId = handleUserId;
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
    * 消息内容
    */
    private String getMessage(){
    return this.message;
    }

    /**
    * 是否已读【0：未读，1已读】
    */
    private String getIsRead(){
    return this.isRead;
    }

    /**
    * 是否撤销【0：未撤销，1已撤销】
    */
    private String getIsCancel(){
    return this.isCancel;
    }

    /**
    * 消息处理人
    */
    private Integer getHandleUserId(){
    return this.handleUserId;
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
