package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 接口日志表
* @TableName fc_sap_push_log
*/
public class FcSapPushLog implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 源系统
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("源系统")
    @Length(max= 255,message="编码长度不能超过255")
    private String source;
    /**
    * 目标系统
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("目标系统")
    @Length(max= 255,message="编码长度不能超过255")
    private String target;
    /**
    * 执行时间
    */
    @ApiModelProperty("执行时间")
    private Date excuteTime;
    /**
    * 所属模块
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("所属模块")
    @Length(max= 255,message="编码长度不能超过255")
    private String belongModule;
    /**
    * 接口ID
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("接口ID")
    @Length(max= 255,message="编码长度不能超过255")
    private String apiId;
    /**
    * 接口名
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("接口名")
    @Length(max= 255,message="编码长度不能超过255")
    private String apiName;
    /**
    * 状态码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("状态码")
    @Length(max= 255,message="编码长度不能超过255")
    private String statusCode;
    /**
    * 日志信息
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("日志信息")
    @Length(max= 255,message="编码长度不能超过255")
    private String logInfo;
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
    * 是否已重新推送【0.未重推 1.已重推】
    */
    @ApiModelProperty("是否已重新推送【0.未重推 1.已重推】")
    private String isAgain;

    /**
    * 主键
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 源系统
    */
    private void setSource(String source){
    this.source = source;
    }

    /**
    * 目标系统
    */
    private void setTarget(String target){
    this.target = target;
    }

    /**
    * 执行时间
    */
    private void setExcuteTime(Date excuteTime){
    this.excuteTime = excuteTime;
    }

    /**
    * 所属模块
    */
    private void setBelongModule(String belongModule){
    this.belongModule = belongModule;
    }

    /**
    * 接口ID
    */
    private void setApiId(String apiId){
    this.apiId = apiId;
    }

    /**
    * 接口名
    */
    private void setApiName(String apiName){
    this.apiName = apiName;
    }

    /**
    * 状态码
    */
    private void setStatusCode(String statusCode){
    this.statusCode = statusCode;
    }

    /**
    * 日志信息
    */
    private void setLogInfo(String logInfo){
    this.logInfo = logInfo;
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
    * 是否已重新推送【0.未重推 1.已重推】
    */
    private void setIsAgain(String isAgain){
    this.isAgain = isAgain;
    }


    /**
    * 主键
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 源系统
    */
    private String getSource(){
    return this.source;
    }

    /**
    * 目标系统
    */
    private String getTarget(){
    return this.target;
    }

    /**
    * 执行时间
    */
    private Date getExcuteTime(){
    return this.excuteTime;
    }

    /**
    * 所属模块
    */
    private String getBelongModule(){
    return this.belongModule;
    }

    /**
    * 接口ID
    */
    private String getApiId(){
    return this.apiId;
    }

    /**
    * 接口名
    */
    private String getApiName(){
    return this.apiName;
    }

    /**
    * 状态码
    */
    private String getStatusCode(){
    return this.statusCode;
    }

    /**
    * 日志信息
    */
    private String getLogInfo(){
    return this.logInfo;
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

    /**
    * 是否已重新推送【0.未重推 1.已重推】
    */
    private String getIsAgain(){
    return this.isAgain;
    }

}
