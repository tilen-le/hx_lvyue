package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.system.domain.FcOrderInvoice;
import com.hexing.system.domain.FcSaleBank;
import com.hexing.system.mapper.FcOrderInvoiceMapper;
import com.hexing.system.service.IFcOrderInvoiceService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderInvoiceServiceImpl implements IFcOrderInvoiceService {

    private final FcOrderInvoiceMapper baseMappr;
    private final HttpKit httpKit;

    @Override
    public int saveFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        LambdaQueryWrapper<FcOrderInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderInvoice::getOrderId, fcOrderInvoice.getOrderId());
        if (baseMappr.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该订单已存在开票申请");
        }
        int result = baseMappr.insert(fcOrderInvoice);

        return result;
    }

    @Override
    public int updateFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        LambdaQueryWrapper<FcOrderInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderInvoice::getId, fcOrderInvoice.getId());
        if (baseMappr.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该开票申请不存在或已删除");
        }
        return baseMappr.updateById(fcOrderInvoice);
    }

    @Override
    public TableDataInfo<FcOrderInvoice> listFcOrderInvoice(FcOrderInvoice fcOrderInvoice, PageQuery pageQuery) {
        Page<FcOrderInvoice> page = baseMappr.selectPage(pageQuery.build(), buildQueryWrapper(fcOrderInvoice));
        return TableDataInfo.build(page);
    }

    private Wrapper<FcOrderInvoice> buildQueryWrapper(FcOrderInvoice fcOrderInvoice) {
        QueryWrapper<FcOrderInvoice> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .like(StringUtils.isNotEmpty(fcOrderInvoice.getInvoiceNumber()), "invoice_number", fcOrderInvoice.getInvoiceNumber())
                .eq(ObjectUtils.isNotNull(fcOrderInvoice.getInvoiceType()), "invoice_type", fcOrderInvoice.getInvoiceType())
                .eq(ObjectUtils.isNotNull(fcOrderInvoice.getApprovalStatus()), "approval_status", fcOrderInvoice.getApprovalStatus());
        return wrapper;
    }

    @Override
    public FcOrderInvoice getDetailById(Long id) {
        FcOrderInvoice fcOrderInvoice = baseMappr.selectById(id);
        return fcOrderInvoice;
    }


    private void submitInvoice(FcOrderInvoice fcOrderInvoice){
        Map<String, Object> params=new HashMap<>();


    }
}
