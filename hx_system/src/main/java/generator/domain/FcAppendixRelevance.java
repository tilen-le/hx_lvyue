package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 附件关联表
* @TableName fc_appendix_relevance
*/
public class FcAppendixRelevance implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 归属类型【1.发货单 2.开票单】
    */
    @ApiModelProperty("归属类型【1.发货单 2.开票单】")
    private Integer type;
    /**
    * 归属单据id
    */
    @NotBlank(message="[归属单据id]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("归属单据id")
    @Length(max= 255,message="编码长度不能超过255")
    private String mainId;
    /**
    * 附件id
    */
    @NotNull(message="[附件id]不能为空")
    @ApiModelProperty("附件id")
    private Integer appendixId;
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
    * 归属类型【1.发货单 2.开票单】
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 归属单据id
    */
    private void setMainId(String mainId){
    this.mainId = mainId;
    }

    /**
    * 附件id
    */
    private void setAppendixId(Integer appendixId){
    this.appendixId = appendixId;
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
    * 归属类型【1.发货单 2.开票单】
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 归属单据id
    */
    private String getMainId(){
    return this.mainId;
    }

    /**
    * 附件id
    */
    private Integer getAppendixId(){
    return this.appendixId;
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
