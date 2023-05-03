package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcApproveConfig;

/**
 * @author firerock_tech
 */
public interface IFcApproveConfigService {

    /**
     * 新增审批配置
     *
     * @param fcApproveConfig
     * @return
     */
    int saveFcApproveConfig(FcApproveConfig fcApproveConfig);

    /**
     * 修改审批配置
     *
     * @param fcApproveConfig
     * @return
     */
    int updateFcApproveConfig(FcApproveConfig fcApproveConfig);


    /**
     * 删除审批配置
     *
     * @param id
     * @return
     */
    int removeFcApproveConfig(Long id);


    /**
     * 获取审批配置列表
     *
     * @param fcApproveConfig
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcApproveConfig> listApproveConfig(FcApproveConfig fcApproveConfig, PageQuery pageQuery);
}
