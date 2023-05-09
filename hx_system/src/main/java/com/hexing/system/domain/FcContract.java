package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合同表
 *
 * @TableName fc_contract
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_contract")
public class FcContract extends BaseEntity {

    /**
     * 主键
     */
   @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 合同名称
     */
    private String contractName;

    private String deleted;


}
