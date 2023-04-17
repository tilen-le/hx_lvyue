package com.hexing.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.common.exception.ServerException;
import com.hexing.customer.entity.FcCustomerInvoice;
import com.hexing.customer.mapper.FcCustomerInvoiceMapper;
import com.hexing.customer.service.IFcCustomerInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author firerock_tech
 */
@Slf4j
@Service("invoiceService")
@RequiredArgsConstructor
public class FcCustomerInvoiceImpl implements IFcCustomerInvoice {

    private final FcCustomerInvoiceMapper baseMapper;
    @Override
    public int saveFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice) {
        return baseMapper.insert(fcCustomerInvoice);
    }

    @Override
    public int updateFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getId, fcCustomerInvoice.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServerException(500, "该开票信息不存在或已删除");
        }
        return baseMapper.updateById(fcCustomerInvoice);
    }

    @Override
    public int removeFcCustomerInvoice(String id) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getId, id);
        FcCustomerInvoice fcCustomerInvoice=baseMapper.selectOne(queryWrapper);
        if (fcCustomerInvoice==null) {
            throw new ServerException(500, "该开票信息不存在或已删除");
        }
        fcCustomerInvoice.setDeleted("1");
        return baseMapper.updateById(fcCustomerInvoice);
    }

    @Override
    public List<FcCustomerInvoice> listFcCustomerInvoice(String customerId) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getCustomerId, customerId).eq(FcCustomerInvoice::getDeleted, "0");
        return baseMapper.selectList(queryWrapper);
    }
}
