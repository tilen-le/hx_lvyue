package com.hexing.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.common.pojo.PageResult;
import com.hexing.customer.entity.FcCustomer;
import com.hexing.customer.entity.vo.FcCustomerVo;
import com.hexing.customer.mapper.CustomerMapper;
import com.hexing.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * @author firerock_tech
 */
@Slf4j
@Service("customerService")
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerMapper baseMapper;


    @Override
    public int saveCustomer(FcCustomer fcCustomer) {
        LambdaQueryWrapper<FcCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomer::getCode, fcCustomer.getCode());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            updateCustomer(fcCustomer);
        }
        return baseMapper.insert(fcCustomer);
    }

    @Override
    public int updateCustomer(FcCustomer fcCustomer) {
        LambdaQueryWrapper<FcCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomer::getCode, fcCustomer.getCode());
        return baseMapper.update(fcCustomer, queryWrapper);
    }

    @Override
    public PageResult<FcCustomer> listUserPage(FcCustomerVo fcCustomer) {
        return baseMapper.selectPage(fcCustomer);
    }

}
