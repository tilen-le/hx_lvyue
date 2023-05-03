package com.hexing.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批配置
 *
 * @TableName fc_approve_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fc_approve_config")
public class FcApproveConfig extends BaseEntity {

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 工厂
     */
    private String factory;
    /**
     * 国内/国际营销部
     */
    private String saleDept;
    /**
     * 库管id
     */
    private String storeKeeper;
    /**
     * 仓储部经理id
     */
    private String wareManager;
    /**
     * 事业部
     */
    private String businessUnit;
    /**
     * 会计id
     */
    private String bookKeeper;
    private Integer delFlag;
    private Integer category;

    @TableField(exist = false)
    private String storeKpName;
    @TableField(exist = false)
    private String wareName;
    @TableField(exist = false)
    private String bookName;



}
