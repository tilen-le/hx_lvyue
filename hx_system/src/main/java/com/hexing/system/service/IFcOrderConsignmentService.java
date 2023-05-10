package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrderConsignment;

import java.util.Map;

/**
 * @author firerock_tech
 */
public interface IFcOrderConsignmentService {

    /**
     * 保存发货信息
     * @param fcOrderConsignment
     * @return
     */
    int saveFcOrderConsignment(FcOrderConsignment fcOrderConsignment);


    /**
     * 编辑发货信息
     * @param fcOrderConsignment
     * @return
     */
    int  updateFcOrderConsignment(FcOrderConsignment fcOrderConsignment);


    /**
     * 获取发货信息列表
     * @param fcOrderConsignment
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcOrderConsignment> listFcOrderConsignment(FcOrderConsignment fcOrderConsignment, PageQuery pageQuery);


    /**
     * 获取发货详情
     * @param id
     * @return
     */
    Map<String,Object> getDetailById(Long id);




}
