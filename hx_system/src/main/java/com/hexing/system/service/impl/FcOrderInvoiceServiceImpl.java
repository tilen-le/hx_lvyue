package com.hexing.system.service.impl;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrderInvoice;
import com.hexing.system.mapper.FcOrderInvoiceMapper;
import com.hexing.system.service.IFcOrderInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderInvoiceServiceImpl implements IFcOrderInvoiceService {

    private final FcOrderInvoiceMapper baseMappr;

    @Override
    public int saveFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        return baseMappr.insert(fcOrderInvoice);
    }

    @Override
    public int updateFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {

        return 0;
    }

    @Override
    public TableDataInfo<FcOrderInvoice> listFcOrderInvoice(FcOrderInvoice fcOrderInvoice, PageQuery pageQuery) {
        return null;
    }

    @Override
    public FcOrderInvoice getDetailById(Long id) {
        return null;
    }
}
