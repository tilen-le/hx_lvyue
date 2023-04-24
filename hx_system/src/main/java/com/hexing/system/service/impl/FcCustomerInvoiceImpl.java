package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.common.exception.ServiceException;
import com.hexing.system.domain.FcCustomerInvoice;
import com.hexing.system.mapper.FcCustomerInvoiceMapper;
import com.hexing.system.service.IFcCustomerInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcCustomerInvoiceImpl implements IFcCustomerInvoiceService {

    private final FcCustomerInvoiceMapper baseMapper;

    @Override
    public int saveFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getCustomerId, fcCustomerInvoice.getCustomerId())
                .eq(FcCustomerInvoice::getOpeningBank, fcCustomerInvoice.getOpeningBank())
                .eq(FcCustomerInvoice::getAccount, fcCustomerInvoice.getAccount());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该开票信息已存在");
        }
        return baseMapper.insert(fcCustomerInvoice);
    }

    @Override
    public int updateFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getId, fcCustomerInvoice.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该开票信息不存在或已删除");
        }
        return baseMapper.updateById(fcCustomerInvoice);
    }

    @Override
    public List<FcCustomerInvoice> listFcCustomerInvoice(Long userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<FcCustomerInvoice>().eq(FcCustomerInvoice::getDeleted,"0").eq(FcCustomerInvoice::getCustomerId, userId));
    }

    @Override
    public int removeFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice) {
        LambdaQueryWrapper<FcCustomerInvoice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerInvoice::getId, fcCustomerInvoice.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该开票信息不存在或已删除");
        }
        fcCustomerInvoice.setDeleted("1");
        return baseMapper.updateById(fcCustomerInvoice);
    }
}
