package com.hexing.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * fc_oss_relevance
 *
 * @author
 */
@Data
public class FcOssRelevance implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 归属类型【1.发货单 2.开票单 3.发货计划】
     */
    private Integer type;

    /**
     * 归属单据id
     */
    private Long mainId;

    /**
     * oss_id
     */
    private Long ossId;

    /**
     * 版本
     */
    private Integer version;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private String deleted;

    private static final long serialVersionUID = 1L;
}
