package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.redis.RedisUtils;
import com.hexing.system.domain.*;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IFcOrderConsignmentService;
import com.hexing.system.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderConsignmentServiceImpl implements IFcOrderConsignmentService {

    private final FcOrderConsignmentMapper baseMapper;

    private final FcOrderMapper fcOrderMapper;

    private final FcOssRelevanceMapper fcOssRelevanceMapper;

    private final FcOrderProductMapper fcOrderProductMapper;

    private final IOrderService orderService;

    private final FcCustomerConsignmentMapper fcCustomerConsignmentMapper;

    private final FcOrderConsignmentDetailMapper fcOrderConsignmentDetailMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        log.error(JsonUtils.toJsonString(fcOrderConsignment));
        LambdaQueryWrapper<FcOrderConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderConsignment::getOrderId, fcOrderConsignment.getOrderId());
        int result = baseMapper.insert(fcOrderConsignment);
        if (result > 0) {
            fcOrderConsignment.getProducts().forEach(item -> {
                Long productId = item.getId();
                item.setConsignmentId(fcOrderConsignment.getId().toString());
                item.setOrderProductId(productId);
                item.setId(null);
                fcOrderConsignmentDetailMapper.insert(item);
                //库存锁定
                FcOrderProduct product = fcOrderProductMapper.selectById(productId);
                double v = Double.parseDouble(product.getNotSentNum()) - (double) item.getProductNum();
                product.setNotSentNum(String.valueOf(v));
                fcOrderProductMapper.updateById(product);
            });
        }
        //附件处理
        List<String> fileIds = fcOrderConsignment.getFileIds();
        if (CollectionUtils.isNotEmpty(fileIds)) {
            FcOssRelevance relevance = fcOssRelevanceMapper.selectOne(new LambdaQueryWrapper<FcOssRelevance>().eq(FcOssRelevance::getMainId, fcOrderConsignment.getId()).eq(FcOssRelevance::getType, 1).orderByDesc(FcOssRelevance::getVersion).last("limit 1"));
            fileIds.forEach(temp -> {
                FcOssRelevance fcOssRelevance = new FcOssRelevance();
                fcOssRelevance.setOssId(Long.parseLong(temp));
                fcOssRelevance.setMainId(fcOrderConsignment.getId());
                fcOssRelevance.setType(1);
                fcOssRelevance.setVersion(Objects.isNull(relevance) ? 1 : relevance.getVersion() + 1);
                fcOssRelevanceMapper.insert(fcOssRelevance);
            });
        }
        return result;
    }

    @Override
    public int updateFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        FcOrderConsignment consignment = baseMapper.selectById(fcOrderConsignment.getId());
        if (Objects.isNull(consignment)) {
            throw new ServiceException("该发货到单不存在");
        }
        if (consignment.getApprovalStatus() != 3) {
            throw new ServiceException("只允许修改草稿状态的发货单");
        }
        int i = baseMapper.updateById(fcOrderConsignment);

        return 0;
    }

    @Override
    public TableDataInfo<FcOrderConsignment> listFcOrderConsignment(FcOrderConsignment fcOrderConsignment, PageQuery pageQuery) {
        Page<FcOrderConsignment> page = baseMapper.listFcOrder(pageQuery.build(), fcOrderConsignment);
        return TableDataInfo.build(page);
    }

    @Override
    public Map<String, Object> getDetailById(Long id) {
        Map<String, Object> result = new HashMap<>();
        FcOrderConsignment consignment = baseMapper.selectById(id);
        List<FcOrderConsignmentDetail> details = fcOrderConsignmentDetailMapper.selectList(new LambdaQueryWrapper<FcOrderConsignmentDetail>().eq(FcOrderConsignmentDetail::getConsignmentId, consignment.getId()));
        details.forEach(item -> {
            FcOrderProduct orderProduct = fcOrderProductMapper.selectById(item.getOrderProductId());
            item.setOrderProduct(orderProduct);
        });
        FcOrder fcOrder = fcOrderMapper.selectById(consignment.getOrderId());
        FcContract fcContract = orderService.getContact(fcOrder.getContractNumber());
        FcCustomerConsignment customerConsignment = fcCustomerConsignmentMapper.selectById(consignment.getConsigneeId());

        //附件


        result.put("consignment", consignment);
        result.put("fcOrder", fcOrder);
        result.put("fcContract", fcContract);
        result.put("customerConsignment", customerConsignment);
        result.put("details", details);

        return result;
    }

    private Wrapper<FcOrderConsignment> buildQueryWrapper(FcOrderConsignment fcOrderConsignment) {
        QueryWrapper<FcOrderConsignment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0").eq(ObjectUtils.isNotNull(fcOrderConsignment.getOrderId()), "order_id", fcOrderConsignment.getOrderId());
        return wrapper;
    }

}
