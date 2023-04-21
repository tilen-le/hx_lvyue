package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 订单产品表
* @TableName fc_order_product
*/
public class FcOrderProduct implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Long id;
    /**
    * 订单主键
    */
    @NotNull(message="[订单主键]不能为空")
    @ApiModelProperty("订单主键")
    private Long orderId;
    /**
    * sap明细号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("sap明细号")
    @Length(max= 255,message="编码长度不能超过255")
    private String sapDetailNumber;
    /**
    * 产品编号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("产品编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String productNumber;
    /**
    * 产品型号
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("产品型号")
    @Length(max= 255,message="编码长度不能超过255")
    private String productModel;
    /**
    * 产品名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("产品名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String productName;
    /**
    * 数量
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("数量")
    @Length(max= 255,message="编码长度不能超过255")
    private String num;
    /**
    * 单价
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("单价")
    @Length(max= 255,message="编码长度不能超过255")
    private String unitPrice;
    /**
    * 入库数
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("入库数")
    @Length(max= 255,message="编码长度不能超过255")
    private String inStorageNum;
    /**
    * 在途
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("在途")
    @Length(max= 255,message="编码长度不能超过255")
    private String inTransitNum;
    /**
    * 未发
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("未发")
    @Length(max= 255,message="编码长度不能超过255")
    private String notSentNum;
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
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 订单主键
    */
    private void setOrderId(Long orderId){
    this.orderId = orderId;
    }

    /**
    * sap明细号
    */
    private void setSapDetailNumber(String sapDetailNumber){
    this.sapDetailNumber = sapDetailNumber;
    }

    /**
    * 产品编号
    */
    private void setProductNumber(String productNumber){
    this.productNumber = productNumber;
    }

    /**
    * 产品型号
    */
    private void setProductModel(String productModel){
    this.productModel = productModel;
    }

    /**
    * 产品名称
    */
    private void setProductName(String productName){
    this.productName = productName;
    }

    /**
    * 数量
    */
    private void setNum(String num){
    this.num = num;
    }

    /**
    * 单价
    */
    private void setUnitPrice(String unitPrice){
    this.unitPrice = unitPrice;
    }

    /**
    * 入库数
    */
    private void setInStorageNum(String inStorageNum){
    this.inStorageNum = inStorageNum;
    }

    /**
    * 在途
    */
    private void setInTransitNum(String inTransitNum){
    this.inTransitNum = inTransitNum;
    }

    /**
    * 未发
    */
    private void setNotSentNum(String notSentNum){
    this.notSentNum = notSentNum;
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
    private Long getId(){
    return this.id;
    }

    /**
    * 订单主键
    */
    private Long getOrderId(){
    return this.orderId;
    }

    /**
    * sap明细号
    */
    private String getSapDetailNumber(){
    return this.sapDetailNumber;
    }

    /**
    * 产品编号
    */
    private String getProductNumber(){
    return this.productNumber;
    }

    /**
    * 产品型号
    */
    private String getProductModel(){
    return this.productModel;
    }

    /**
    * 产品名称
    */
    private String getProductName(){
    return this.productName;
    }

    /**
    * 数量
    */
    private String getNum(){
    return this.num;
    }

    /**
    * 单价
    */
    private String getUnitPrice(){
    return this.unitPrice;
    }

    /**
    * 入库数
    */
    private String getInStorageNum(){
    return this.inStorageNum;
    }

    /**
    * 在途
    */
    private String getInTransitNum(){
    return this.inTransitNum;
    }

    /**
    * 未发
    */
    private String getNotSentNum(){
    return this.notSentNum;
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
