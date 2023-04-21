package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 订单表
* @TableName fc_order
*/
public class FcOrder implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 合同id
    */
    @NotNull(message="[合同id]不能为空")
    @ApiModelProperty("合同id")
    private Long contarctId;
    /**
    * 订单编号
    */
    @NotBlank(message="[订单编号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("订单编号")
    @Length(max= 255,message="编码长度不能超过255")
    private String orderNumber;
    /**
    * 订单标题
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("订单标题")
    @Length(max= 255,message="编码长度不能超过255")
    private String orderTitle;
    /**
    * 币种
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("币种")
    @Length(max= 255,message="编码长度不能超过255")
    private String currency;
    /**
    * 订单状态（0：启用，1：禁用）
    */
    @ApiModelProperty("订单状态（0：启用，1：禁用）")
    private String status;
    /**
    * SAP创建时间
    */
    @ApiModelProperty("SAP创建时间")
    private Date sapCreateTime;
    /**
    * 售达方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("售达方")
    @Length(max= 255,message="编码长度不能超过255")
    private String soldToParty;
    /**
    * 售达方编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("售达方编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String soldToPartyCd;
    /**
    * 订单金额
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("订单金额")
    @Length(max= 255,message="编码长度不能超过255")
    private String amount;
    /**
    * 税率
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("税率")
    @Length(max= 255,message="编码长度不能超过255")
    private String taxRate;
    /**
    * 付款方编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("付款方编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String payerCd;
    /**
    * 付款方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("付款方")
    @Length(max= 255,message="编码长度不能超过255")
    private String payer;
    /**
    * 是否备表直发
    */
    @ApiModelProperty("是否备表直发")
    private String isBackupTableDirectly;
    /**
    * 送达方/收货方编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("送达方/收货方编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String reciverCd;
    /**
    * 送达方/收货方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("送达方/收货方")
    @Length(max= 255,message="编码长度不能超过255")
    private String reciver;
    /**
    * 要求交货日期
    */
    @ApiModelProperty("要求交货日期")
    private Date requireDeliveryDate;
    /**
    * 客户经理
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("客户经理")
    @Length(max= 255,message="编码长度不能超过255")
    private String customerManager;
    /**
    * 收票方编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收票方编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String bileeCd;
    /**
    * 收票方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收票方")
    @Length(max= 255,message="编码长度不能超过255")
    private String billee;
    /**
    * 事业部
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("事业部")
    @Length(max= 255,message="编码长度不能超过255")
    private String businessUnit;
    /**
    * 销售组织
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("销售组织")
    @Length(max= 255,message="编码长度不能超过255")
    private String saleOrg;
    /**
    * 销售类型
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("销售类型")
    @Length(max= 255,message="编码长度不能超过255")
    private String saleType;
    /**
    * 收款方编码
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收款方编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String payeeCd;
    /**
    * 收款方
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("收款方")
    @Length(max= 255,message="编码长度不能超过255")
    private String payee;
    /**
    * 分销渠道
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("分销渠道")
    @Length(max= 255,message="编码长度不能超过255")
    private String distributionChannel;
    /**
    * 国内/国际营销部
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("国内/国际营销部")
    @Length(max= 255,message="编码长度不能超过255")
    private String marketingDepartment;
    /**
    * 仓储部经理
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("仓储部经理")
    @Length(max= 255,message="编码长度不能超过255")
    private String warehouseManager;
    /**
    * 工厂
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("工厂")
    @Length(max= 255,message="编码长度不能超过255")
    private String factory;
    /**
    * 预计发货日期
    */
    @ApiModelProperty("预计发货日期")
    private Date expectedDeliveryDate;
    /**
    * 预计到货日期
    */
    @ApiModelProperty("预计到货日期")
    private Date expectedArrivalDate;
    /**
    * 预计验收日期
    */
    @ApiModelProperty("预计验收日期")
    private Date expectedCheckDate;
    /**
    * 预计质保日期
    */
    @ApiModelProperty("预计质保日期")
    private Date expectedWarrantyDate;
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
    * 合同id
    */
    private void setContarctId(Long contarctId){
    this.contarctId = contarctId;
    }

    /**
    * 订单编号
    */
    private void setOrderNumber(String orderNumber){
    this.orderNumber = orderNumber;
    }

    /**
    * 订单标题
    */
    private void setOrderTitle(String orderTitle){
    this.orderTitle = orderTitle;
    }

    /**
    * 币种
    */
    private void setCurrency(String currency){
    this.currency = currency;
    }

    /**
    * 订单状态（0：启用，1：禁用）
    */
    private void setStatus(String status){
    this.status = status;
    }

    /**
    * SAP创建时间
    */
    private void setSapCreateTime(Date sapCreateTime){
    this.sapCreateTime = sapCreateTime;
    }

    /**
    * 售达方
    */
    private void setSoldToParty(String soldToParty){
    this.soldToParty = soldToParty;
    }

    /**
    * 售达方编码
    */
    private void setSoldToPartyCd(String soldToPartyCd){
    this.soldToPartyCd = soldToPartyCd;
    }

    /**
    * 订单金额
    */
    private void setAmount(String amount){
    this.amount = amount;
    }

    /**
    * 税率
    */
    private void setTaxRate(String taxRate){
    this.taxRate = taxRate;
    }

    /**
    * 付款方编码
    */
    private void setPayerCd(String payerCd){
    this.payerCd = payerCd;
    }

    /**
    * 付款方
    */
    private void setPayer(String payer){
    this.payer = payer;
    }

    /**
    * 是否备表直发
    */
    private void setIsBackupTableDirectly(String isBackupTableDirectly){
    this.isBackupTableDirectly = isBackupTableDirectly;
    }

    /**
    * 送达方/收货方编码
    */
    private void setReciverCd(String reciverCd){
    this.reciverCd = reciverCd;
    }

    /**
    * 送达方/收货方
    */
    private void setReciver(String reciver){
    this.reciver = reciver;
    }

    /**
    * 要求交货日期
    */
    private void setRequireDeliveryDate(Date requireDeliveryDate){
    this.requireDeliveryDate = requireDeliveryDate;
    }

    /**
    * 客户经理
    */
    private void setCustomerManager(String customerManager){
    this.customerManager = customerManager;
    }

    /**
    * 收票方编码
    */
    private void setBileeCd(String bileeCd){
    this.bileeCd = bileeCd;
    }

    /**
    * 收票方
    */
    private void setBillee(String billee){
    this.billee = billee;
    }

    /**
    * 事业部
    */
    private void setBusinessUnit(String businessUnit){
    this.businessUnit = businessUnit;
    }

    /**
    * 销售组织
    */
    private void setSaleOrg(String saleOrg){
    this.saleOrg = saleOrg;
    }

    /**
    * 销售类型
    */
    private void setSaleType(String saleType){
    this.saleType = saleType;
    }

    /**
    * 收款方编码
    */
    private void setPayeeCd(String payeeCd){
    this.payeeCd = payeeCd;
    }

    /**
    * 收款方
    */
    private void setPayee(String payee){
    this.payee = payee;
    }

    /**
    * 分销渠道
    */
    private void setDistributionChannel(String distributionChannel){
    this.distributionChannel = distributionChannel;
    }

    /**
    * 国内/国际营销部
    */
    private void setMarketingDepartment(String marketingDepartment){
    this.marketingDepartment = marketingDepartment;
    }

    /**
    * 仓储部经理
    */
    private void setWarehouseManager(String warehouseManager){
    this.warehouseManager = warehouseManager;
    }

    /**
    * 工厂
    */
    private void setFactory(String factory){
    this.factory = factory;
    }

    /**
    * 预计发货日期
    */
    private void setExpectedDeliveryDate(Date expectedDeliveryDate){
    this.expectedDeliveryDate = expectedDeliveryDate;
    }

    /**
    * 预计到货日期
    */
    private void setExpectedArrivalDate(Date expectedArrivalDate){
    this.expectedArrivalDate = expectedArrivalDate;
    }

    /**
    * 预计验收日期
    */
    private void setExpectedCheckDate(Date expectedCheckDate){
    this.expectedCheckDate = expectedCheckDate;
    }

    /**
    * 预计质保日期
    */
    private void setExpectedWarrantyDate(Date expectedWarrantyDate){
    this.expectedWarrantyDate = expectedWarrantyDate;
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
    * 合同id
    */
    private Long getContarctId(){
    return this.contarctId;
    }

    /**
    * 订单编号
    */
    private String getOrderNumber(){
    return this.orderNumber;
    }

    /**
    * 订单标题
    */
    private String getOrderTitle(){
    return this.orderTitle;
    }

    /**
    * 币种
    */
    private String getCurrency(){
    return this.currency;
    }

    /**
    * 订单状态（0：启用，1：禁用）
    */
    private String getStatus(){
    return this.status;
    }

    /**
    * SAP创建时间
    */
    private Date getSapCreateTime(){
    return this.sapCreateTime;
    }

    /**
    * 售达方
    */
    private String getSoldToParty(){
    return this.soldToParty;
    }

    /**
    * 售达方编码
    */
    private String getSoldToPartyCd(){
    return this.soldToPartyCd;
    }

    /**
    * 订单金额
    */
    private String getAmount(){
    return this.amount;
    }

    /**
    * 税率
    */
    private String getTaxRate(){
    return this.taxRate;
    }

    /**
    * 付款方编码
    */
    private String getPayerCd(){
    return this.payerCd;
    }

    /**
    * 付款方
    */
    private String getPayer(){
    return this.payer;
    }

    /**
    * 是否备表直发
    */
    private String getIsBackupTableDirectly(){
    return this.isBackupTableDirectly;
    }

    /**
    * 送达方/收货方编码
    */
    private String getReciverCd(){
    return this.reciverCd;
    }

    /**
    * 送达方/收货方
    */
    private String getReciver(){
    return this.reciver;
    }

    /**
    * 要求交货日期
    */
    private Date getRequireDeliveryDate(){
    return this.requireDeliveryDate;
    }

    /**
    * 客户经理
    */
    private String getCustomerManager(){
    return this.customerManager;
    }

    /**
    * 收票方编码
    */
    private String getBileeCd(){
    return this.bileeCd;
    }

    /**
    * 收票方
    */
    private String getBillee(){
    return this.billee;
    }

    /**
    * 事业部
    */
    private String getBusinessUnit(){
    return this.businessUnit;
    }

    /**
    * 销售组织
    */
    private String getSaleOrg(){
    return this.saleOrg;
    }

    /**
    * 销售类型
    */
    private String getSaleType(){
    return this.saleType;
    }

    /**
    * 收款方编码
    */
    private String getPayeeCd(){
    return this.payeeCd;
    }

    /**
    * 收款方
    */
    private String getPayee(){
    return this.payee;
    }

    /**
    * 分销渠道
    */
    private String getDistributionChannel(){
    return this.distributionChannel;
    }

    /**
    * 国内/国际营销部
    */
    private String getMarketingDepartment(){
    return this.marketingDepartment;
    }

    /**
    * 仓储部经理
    */
    private String getWarehouseManager(){
    return this.warehouseManager;
    }

    /**
    * 工厂
    */
    private String getFactory(){
    return this.factory;
    }

    /**
    * 预计发货日期
    */
    private Date getExpectedDeliveryDate(){
    return this.expectedDeliveryDate;
    }

    /**
    * 预计到货日期
    */
    private Date getExpectedArrivalDate(){
    return this.expectedArrivalDate;
    }

    /**
    * 预计验收日期
    */
    private Date getExpectedCheckDate(){
    return this.expectedCheckDate;
    }

    /**
    * 预计质保日期
    */
    private Date getExpectedWarrantyDate(){
    return this.expectedWarrantyDate;
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
