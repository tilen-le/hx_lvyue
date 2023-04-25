package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcSaleBank;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcSaleBankService {

    /**
     * 新增卖方银行
     * @param fcSaleBank
     * @return
     */
    int saveFcSaleBank(FcSaleBank fcSaleBank);


    /**
     * 修改卖方银行
     * @param fcSaleBank
     * @return
     */
    int updateFcSaleBank(FcSaleBank fcSaleBank);

    /**
     * 删除卖方银行
     * @param fcSaleBank
     * @return
     */
    int removeFcSaleBank(FcSaleBank fcSaleBank);

    /**
     * 分页获取卖方银行
     * @param fcSaleBank
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcSaleBank> listFcSaleBank(FcSaleBank fcSaleBank, PageQuery pageQuery);


    /**
     * 获取可用卖方银行
     * @return
     */
    List<FcSaleBank> availableBank();
}
