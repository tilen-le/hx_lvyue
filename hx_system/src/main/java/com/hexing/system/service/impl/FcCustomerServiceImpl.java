package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.constant.UserConstants;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.entity.SysRole;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.form.CustomerForm;
import com.hexing.system.mapper.FcCustomerMapper;
import com.hexing.system.service.IFcCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcCustomerServiceImpl implements IFcCustomerService {

    private final FcCustomerMapper baseMapper;

    /**
     * 同步客户列表，存在更新，不存在写入
     *
     * @param fcCustomer
     * @return
     */
    @Override
    public int saveCustomer(CustomerForm fcCustomer) {
        LambdaQueryWrapper<FcCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomer::getCode, fcCustomer.getKunnr());
        FcCustomer existCustomer = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(existCustomer)) {
            existCustomer = new FcCustomer();
            existCustomer.setName(fcCustomer.getName1());
            existCustomer.setCreditLimit(fcCustomer.getCredit());
            existCustomer.setNation(fcCustomer.getLand1());
            existCustomer.setCode(fcCustomer.getKunnr());
            return baseMapper.insert(existCustomer);
        } else {
            existCustomer.setName(fcCustomer.getName1());
            existCustomer.setCreditLimit(fcCustomer.getCredit());
            existCustomer.setNation(fcCustomer.getLand1());
            return baseMapper.updateById(existCustomer);
        }
    }

    @Override
    public FcCustomer getCustomerById(String id) {
        FcCustomer fcCustomer = baseMapper.selectById(id);
        return fcCustomer;
    }




    @Override
    public TableDataInfo<FcCustomer> listCustomer(FcCustomer fcCustomer, PageQuery pageQuery) {
        Page<FcCustomer> page = baseMapper.selectPage(pageQuery.build(), buildQueryWrapper(fcCustomer));
        return TableDataInfo.build(page);
    }

    @Override
    public List<FcCustomer> allCustomer(FcCustomer fcCustomer) {
        // baseMapper.lis
        return null;
    }

    @Override
    public List<FcCustomer> getCustomerlist(FcCustomer fcCustomer) {
        return baseMapper.getCustomerlist(fcCustomer);
    }

    private Wrapper<FcCustomer> buildQueryWrapper(FcCustomer fcCustomer) {
        QueryWrapper<FcCustomer> wrapper = Wrappers.query();
        wrapper.like(StringUtils.isNotEmpty(fcCustomer.getName()), "name", fcCustomer.getName())
                .or()
                .like(StringUtils.isNotEmpty(fcCustomer.getCode()), "code", fcCustomer.getCode());
        return wrapper;
    }
}
