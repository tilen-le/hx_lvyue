package com.hexing.system.service.impl;

import cn.hutool.json.JSONUtil;
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
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.*;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IFcApproveService;
import com.hexing.system.service.IFcCustomerConsignmentService;
import com.hexing.system.service.IFcOrderInvoiceService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderInvoiceServiceImpl implements IFcOrderInvoiceService {

    @Resource
    private FcOrderInvoiceMapper baseMappr;
    @Resource
    private FcOrderMapper fcOrderMapper;

    @Resource
    private FcOrderProductMapper productMapper;

    @Resource
    private IFcApproveService iFcApproveService;

    @Resource
    private FcCustomerConsignmentMapper fcCustomerConsignmentMapper;

    @Resource
    private FcOrderInvoiceDetailMapper fcOrderInvoiceDetailMapper;
    private final HttpKit httpKit;

    @Override
    public int saveFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        LambdaQueryWrapper<FcOrderInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderInvoice::getOrderId, fcOrderInvoice.getOrderId());
        if (baseMappr.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该订单已存在开票申请");
        }
        if (fcOrderInvoice.getProductList() == null) {
            throw new ServiceException("开票明细不能为空");
        }
        int result = baseMappr.insert(fcOrderInvoice);
        if (result > 0) {
            for (FcOrderInvoiceDetail detail : fcOrderInvoice.getProductList()) {
                detail.setInvoiceId(fcOrderInvoice.getId());
                fcOrderInvoiceDetailMapper.insert(detail);
            }
        }
        handleApprove(fcOrderInvoice);
        return result;
    }

    private void handleApprove(FcOrderInvoice fcOrderInvoice) {
        FcApprove fcApprove = new FcApprove();
        fcApprove.setTitle("开票审批");
        fcApprove.setType(2);
        fcApprove.setOriginator(LoginHelper.getUserId().toString());
        fcApprove.setStatus(0);
        fcApprove.setRequestTime(new Date());
        fcApprove.setMainId(fcOrderInvoice.getId());
        iFcApproveService.saveFcApprove(fcApprove);
    }

    private void submitSapInvoice(FcOrderInvoice fcOrderInvoice) {
        if (fcOrderInvoice.getProductList() == null) {
            throw new ServiceException("开票明细不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_JHKP");
        List<FcOrderInvoiceDetail> details = fcOrderInvoice.getProductList();
        List<Object> data = new ArrayList<>(details.size());
        LambdaQueryWrapper<FcOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(FcOrder::getId, fcOrderInvoice.getOrderId());
        FcOrder order = fcOrderMapper.selectOne(orderWrapper);
        for (FcOrderInvoiceDetail info : details) {
            FcOrderProduct fcOrderProduct = productMapper.selectById(info.getOrderProductId());
            FcCustomerConsignment fcCustomerConsignment = fcCustomerConsignmentMapper.selectById(fcOrderInvoice.getConsignmentId());
            Map<String, Object> item = new HashMap<>();
            item.put("VBELN_VA", order.getOrderNumber());
            item.put("POSNR_VA", fcOrderProduct.getSapDetailNumber());
            item.put("CREATE_TYPE", "C");
            item.put("ZMENG", info.getAppliedQuantity());
            item.put("KUNNR_BP", fcOrderInvoice.getConsigneeId());
            item.put("NETPR_ZFOB", info.getInvoicingUnitPriceWithTax());
            item.put("ITEXT1", fcOrderInvoice.getInvoiceType());
            item.put("ITEXT3", "");
            item.put("ITEXT4", info.getUnit());
            item.put("ITEXT5", "");
            item.put("ITEXT6", fcCustomerConsignment.getAddress() + "(" + fcCustomerConsignment.getPhone() + ")");
            item.put("ITEXT7", fcOrderInvoice.getOpeningBank());
            item.put("LGORT", "3007");
            item.put("VSTEL", order.getFactory());
            data.add(item);
        }
        params.put("data", data);
        httpKit.postData(params);
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
        Page<FcOrderInvoice> page = baseMappr.listPageInvoice(pageQuery.build(), fcOrderInvoice);
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
    public Map<String,Object> getDetailById(Long id) {
        Map<String,Object> result = new HashMap<>();
        FcOrderInvoice fcOrderInvoice = baseMappr.selectById(id);
        result.put("fcOrderInvoice",fcOrderInvoice);
        return result;
    }


    private void submitInvoice(FcOrderInvoice fcOrderInvoice) {
        Map<String, Object> params = new HashMap<>();


    }
}
