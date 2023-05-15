package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.common.exception.ServiceException;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.FcCustomerConsignment;
import com.hexing.system.mapper.FcCustomerConsignmentMapper;
import com.hexing.system.mapper.FcCustomerMapper;
import com.hexing.system.service.IFcCustomerConsignmentService;
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
public class FcCustomerConsignmentServiceImpl implements IFcCustomerConsignmentService {

    private final FcCustomerConsignmentMapper baseMapper;
    private final FcCustomerMapper fcCustomerMapper;

    @Override
    public int saveFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment) {
        return baseMapper.insert(fcCustomerConsignment);
    }

    @Override
    public int updateFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getId, fcCustomerConsignment.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该地址不存在或已删除");
        }
        return baseMapper.updateById(fcCustomerConsignment);
    }

    @Override
    public List<FcCustomerConsignment> listFcCustomerConsignment(String cusId) {
        return baseMapper.selectList(new LambdaQueryWrapper<FcCustomerConsignment>().eq(FcCustomerConsignment::getDeleted, "0").eq(FcCustomerConsignment::getCustomerId, cusId));
    }

    @Override
    public List<FcCustomerConsignment> listFcCustomerConsignmentByCode(String code) {
        LambdaQueryWrapper<FcCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomer::getCode, code);
        FcCustomer fcCustomer = fcCustomerMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(fcCustomer)) {
            throw new ServiceException("未找到该收货方信息");
        }
        return baseMapper.selectList(new LambdaQueryWrapper<FcCustomerConsignment>().eq(FcCustomerConsignment::getDeleted, "0").eq(FcCustomerConsignment::getCustomerId, fcCustomer.getId()));
    }

    @Override
    public int removeFcCustomerConsignment(Long id) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getId, id);
        FcCustomerConsignment fcCustomerConsignment = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(fcCustomerConsignment)) {
            throw new ServiceException("该地址不存在或已删除");
        }
        fcCustomerConsignment.setDeleted("1");
        return baseMapper.updateById(fcCustomerConsignment);
    }

    @Override
    public FcCustomerConsignment getById(Long id) {
        LambdaQueryWrapper<FcCustomerConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcCustomerConsignment::getId, id);
        FcCustomerConsignment fcCustomerConsignment = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(fcCustomerConsignment)) {
            throw new ServiceException("该地址不存在或已删除");
        }
        return fcCustomerConsignment;

    }
}
