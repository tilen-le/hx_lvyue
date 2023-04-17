package com.hexing.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.common.exception.ServerException;
import com.hexing.customer.entity.FcCustomerConsignment;
import com.hexing.customer.mapper.FcCustomerConsignmentMapper;
import com.hexing.customer.service.IFcCustomerConsignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author firerock_tech
 */
@Slf4j
@Service("consignmentService")
@RequiredArgsConstructor
public class FcCustomerConsignmentServiceImpl implements IFcCustomerConsignmentService {

    private final FcCustomerConsignmentMapper baseMapper;

    @Override
    public int saveFcCustomerConsignment(@Validated FcCustomerConsignment fcCustomerConsignment) {
        return baseMapper.insert(fcCustomerConsignment);
    }

    @Override
    public int updateFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getId, fcCustomerConsignment.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServerException(500, "该收货地址不存在或已删除");
        }
        return baseMapper.updateById(fcCustomerConsignment);
    }

    @Override
    public int removeFcCustomerConsignment(String id) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getId, id);
        FcCustomerConsignment existConsignment = baseMapper.selectOne(queryWrapper);
        if (existConsignment == null) {
            throw new ServerException(500, "该收货地址不存在或已删除");
        }
        existConsignment.setDeleted("1");
        return baseMapper.updateById(existConsignment);
    }

    @Override
    public List<FcCustomerConsignment> listFcCustomerConsignment(String customerId) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getCustomerId, customerId).eq(FcCustomerConsignment::getDeleted, "0");
        return baseMapper.selectList(queryWrapper);
    }
}
