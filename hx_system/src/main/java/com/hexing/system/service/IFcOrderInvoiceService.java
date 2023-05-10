package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrderInvoice;

import java.util.Map;

/**
 * @author firerock_tech
 */
public interface IFcOrderInvoiceService {


    /**
     * 创建开票
     *
     * @param fcOrderInvoice
     * @return
     */
    int saveFcOrderInvoice(FcOrderInvoice fcOrderInvoice);

    /**
     * 修改开票信息
     *
     * @param fcOrderInvoice
     * @return
     */
    int updateFcOrderInvoice(FcOrderInvoice fcOrderInvoice);


    /**
     * 获取开票列表
     *
     * @param fcOrderInvoice
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcOrderInvoice> listFcOrderInvoice(FcOrderInvoice fcOrderInvoice, PageQuery pageQuery);


    /**
     * 获取开票详情
     *
     * @param id
     * @return
     */
    Map<String,Object> getDetailById(Long id);

}
