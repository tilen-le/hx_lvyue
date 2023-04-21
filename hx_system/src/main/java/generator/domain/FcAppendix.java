package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 附件表
* @TableName fc_appendix
*/
public class FcAppendix implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 文件id(在文件服务器的id)
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("文件id(在文件服务器的id)")
    @Length(max= 255,message="编码长度不能超过255")
    private String fileId;
    /**
    * 源文件名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("源文件名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String filename;
    /**
    * 文件版本号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("文件版本号")
    @Length(max= 255,message="编码长度不能超过255")
    private String version;
    /**
    * 拼接文件名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("拼接文件名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String fixfilename;
    /**
    * url
    */
    @ApiModelProperty("url")
    private Integer url;
    /**
    * 大小
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("大小")
    @Length(max= 255,message="编码长度不能超过255")
    private String size;
    /**
    * 后缀
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("后缀")
    @Length(max= 255,message="编码长度不能超过255")
    private String suffix;
    /**
    * 计次
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("计次")
    @Length(max= 255,message="编码长度不能超过255")
    private String count;
    /**
    * 组
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("组")
    @Length(max= 255,message="编码长度不能超过255")
    private String group;
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
    * 文件id(在文件服务器的id)
    */
    private void setFileId(String fileId){
    this.fileId = fileId;
    }

    /**
    * 源文件名称
    */
    private void setFilename(String filename){
    this.filename = filename;
    }

    /**
    * 文件版本号
    */
    private void setVersion(String version){
    this.version = version;
    }

    /**
    * 拼接文件名称
    */
    private void setFixfilename(String fixfilename){
    this.fixfilename = fixfilename;
    }

    /**
    * url
    */
    private void setUrl(Integer url){
    this.url = url;
    }

    /**
    * 大小
    */
    private void setSize(String size){
    this.size = size;
    }

    /**
    * 后缀
    */
    private void setSuffix(String suffix){
    this.suffix = suffix;
    }

    /**
    * 计次
    */
    private void setCount(String count){
    this.count = count;
    }

    /**
    * 组
    */
    private void setGroup(String group){
    this.group = group;
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
    * 文件id(在文件服务器的id)
    */
    private String getFileId(){
    return this.fileId;
    }

    /**
    * 源文件名称
    */
    private String getFilename(){
    return this.filename;
    }

    /**
    * 文件版本号
    */
    private String getVersion(){
    return this.version;
    }

    /**
    * 拼接文件名称
    */
    private String getFixfilename(){
    return this.fixfilename;
    }

    /**
    * url
    */
    private Integer getUrl(){
    return this.url;
    }

    /**
    * 大小
    */
    private String getSize(){
    return this.size;
    }

    /**
    * 后缀
    */
    private String getSuffix(){
    return this.suffix;
    }

    /**
    * 计次
    */
    private String getCount(){
    return this.count;
    }

    /**
    * 组
    */
    private String getGroup(){
    return this.group;
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
