package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 发货明细表
* @TableName fc_order_consignment_detail
*/
public class FcOrderConsignmentDetail implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 发货单主键
    */
    @NotNull(message="[发货单主键]不能为空")
    @ApiModelProperty("发货单主键")
    private Integer consignmentId;
    /**
    * 产品id
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("产品id")
    @Length(max= 255,message="编码长度不能超过255")
    private String orderProductId;
    /**
    * 产品数量
    */
    @ApiModelProperty("产品数量")
    private Integer productNum;
    /**
    * 技术要求
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("技术要求")
    @Length(max= 255,message="编码长度不能超过255")
    private String technicalRequirement;
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
    * 发货单主键
    */
    private void setConsignmentId(Integer consignmentId){
    this.consignmentId = consignmentId;
    }

    /**
    * 产品id
    */
    private void setOrderProductId(String orderProductId){
    this.orderProductId = orderProductId;
    }

    /**
    * 产品数量
    */
    private void setProductNum(Integer productNum){
    this.productNum = productNum;
    }

    /**
    * 技术要求
    */
    private void setTechnicalRequirement(String technicalRequirement){
    this.technicalRequirement = technicalRequirement;
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
    * 发货单主键
    */
    private Integer getConsignmentId(){
    return this.consignmentId;
    }

    /**
    * 产品id
    */
    private String getOrderProductId(){
    return this.orderProductId;
    }

    /**
    * 产品数量
    */
    private Integer getProductNum(){
    return this.productNum;
    }

    /**
    * 技术要求
    */
    private String getTechnicalRequirement(){
    return this.technicalRequirement;
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
