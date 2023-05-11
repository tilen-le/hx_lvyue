package com.hexing.system.domain.bo;

import com.hexing.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS对象存储分页查询对象 sys_oss
 *
 * @author firerock_tech
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOssCons extends BaseEntity {

    /**
     * ossId
     */
    private String ossId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    /**
     * 服务商
     */
    private String service;

}
