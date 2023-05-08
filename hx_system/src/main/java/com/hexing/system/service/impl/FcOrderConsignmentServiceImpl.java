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
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.FcOrderConsignment;
import com.hexing.system.domain.FcOrderInvoice;
import com.hexing.system.mapper.FcOrderConsignmentDetailMapper;
import com.hexing.system.mapper.FcOrderConsignmentMapper;
import com.hexing.system.service.IFcOrderConsignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderConsignmentServiceImpl implements IFcOrderConsignmentService {

    private final FcOrderConsignmentMapper baseMapper;

    private final FcOrderConsignmentDetailMapper fcOrderConsignmentDetailMapper;


    @Override
    public int saveFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        log.error(JsonUtils.toJsonString(fcOrderConsignment));
        LambdaQueryWrapper<FcOrderConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderConsignment::getOrderId, fcOrderConsignment.getOrderId());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException("该发货到单已存在");
        }
        int result = baseMapper.insert(fcOrderConsignment);
        if (result > 0) {
            fcOrderConsignment.getProducts().forEach(item -> {
                item.setConsignmentId(fcOrderConsignment.getId().toString());
                item.setOrderProductId(fcOrderConsignment.getOrderId());
                fcOrderConsignmentDetailMapper.insert(item);
            });
        }
        return result;
    }

    @Override
    public int updateFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        LambdaQueryWrapper<FcOrderConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderConsignment::getId, fcOrderConsignment.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该发货到单不存在");
        }
        return baseMapper.updateById(fcOrderConsignment);
    }

    @Override
    public TableDataInfo<FcOrderConsignment> listFcOrderConsignment(FcOrderConsignment fcOrderConsignment, PageQuery pageQuery) {
        Page<FcOrderConsignment> page = baseMapper.listFcOrder(pageQuery.build(), fcOrderConsignment);
        return TableDataInfo.build(page);
    }

    @Override
    public FcOrderConsignment getDetailById(Long id) {
        return baseMapper.selectById(id);
    }

    private Wrapper<FcOrderConsignment> buildQueryWrapper(FcOrderConsignment fcOrderConsignment) {
        QueryWrapper<FcOrderConsignment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .eq(ObjectUtils.isNotNull(fcOrderConsignment.getOrderId()), "order_id", fcOrderConsignment.getOrderId());
        return wrapper;
    }

}
