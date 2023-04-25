package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.system.domain.FcSaleBank;
import com.hexing.system.mapper.FcSaleBankMapper;
import com.hexing.system.service.IFcSaleBankService;
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
public class FcSaleBankServiceImpl implements IFcSaleBankService {

    private final FcSaleBankMapper baseMapper;

    @Override
    public int saveFcSaleBank(FcSaleBank fcSaleBank) {
        LambdaQueryWrapper<FcSaleBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcSaleBank::getBankName, fcSaleBank.getBankName())
                .eq(FcSaleBank::getBeneficiary, fcSaleBank.getBeneficiary())
                .eq(FcSaleBank::getOutBankCode, fcSaleBank.getOutBankCode())
                .eq(FcSaleBank::getInBankCode, fcSaleBank.getInBankCode());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该卖方银行已存在");
        }
        return baseMapper.insert(fcSaleBank);
    }

    @Override
    public int updateFcSaleBank(FcSaleBank fcSaleBank) {
        LambdaQueryWrapper<FcSaleBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcSaleBank::getId, fcSaleBank.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该卖方银行不存在或已删除");
        }
        return baseMapper.updateById(fcSaleBank);
    }

    @Override
    public int removeFcSaleBank(FcSaleBank fcSaleBank) {
        LambdaQueryWrapper<FcSaleBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcSaleBank::getId, fcSaleBank.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该卖方银行不存在或已删除");
        }
        fcSaleBank.setDeleted("1");
        return baseMapper.updateById(fcSaleBank);
    }

    @Override
    public TableDataInfo<FcSaleBank> listFcSaleBank(FcSaleBank fcSaleBank, PageQuery pageQuery) {
        Page<FcSaleBank> page = baseMapper.selectPage(pageQuery.build(), buildQueryWrapper(fcSaleBank));
        return TableDataInfo.build(page);
    }

    private Wrapper<FcSaleBank> buildQueryWrapper(FcSaleBank fcSaleBank) {
        QueryWrapper<FcSaleBank> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .like(StringUtils.isNotEmpty(fcSaleBank.getBankName()), "bank_name", fcSaleBank.getBankName());
        return wrapper;
    }

    @Override
    public List<FcSaleBank> availableBank() {
        LambdaQueryWrapper<FcSaleBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcSaleBank::getStatus, "0");
        return baseMapper.selectList(queryWrapper);
    }
}
