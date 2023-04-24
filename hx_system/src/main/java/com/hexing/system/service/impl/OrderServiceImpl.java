package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.FcContract;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.ResultForm;
import com.hexing.system.mapper.FcContractMapper;
import com.hexing.system.mapper.FcOrderMapper;
import com.hexing.system.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author firerock_tech
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final FcOrderMapper baseMapper;

    private final FcContractMapper fcContractMapper;

    @Override
    public int saveOrder(Object orders) {
        if (ObjectUtil.isNotNull(orders)) {
            Type type = new TypeToken<ResultForm<List<OrderForm>>>() {
            }.getType();
            ResultForm<List<OrderForm>> orderForm = new Gson().fromJson(JsonUtils.toJsonString(orders), type);
            if (ObjectUtil.isNotNull(orderForm)) {
                orderForm.getData().forEach(this::handleOrderStruct);
            }
        }
        return 0;
    }

    private FcContract getContact(String cid) {
        LambdaQueryWrapper<FcContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcContract::getContractNumber, cid);
        return fcContractMapper.selectOne(queryWrapper);
    }

    @Override
    public void handleOrderStruct(OrderForm orderForm) {
        log.error(JsonUtils.toJsonString(orderForm));
        FcContract fcContract = getContact(orderForm.getVbelnRe());
        LambdaQueryWrapper<FcOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrder::getOrderNumber, orderForm.getVbeln());
        FcOrder existOrder = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(existOrder)) {
            existOrder.setContarctId(fcContract == null ? null : fcContract.getId());
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWerks());
            existOrder.setSapCreateTime(orderForm.getErdat());
            existOrder.setSoldToParty(orderForm.getKunnrSp());
            existOrder.setSoldToPartyCd(orderForm.getKunnrSpT());
            existOrder.setAmount(orderForm.getAmount());
            existOrder.setTaxRate(orderForm.getZsj());
            existOrder.setReciverCd(orderForm.getKunnrBpT());
            existOrder.setReciver(orderForm.getKunnrBp());
            existOrder.setCustomerManager(orderForm.getKunnrEr());
            existOrder.setBileeCd(orderForm.getKunnrBpT());
            existOrder.setBillee(orderForm.getKunnrBp());
            existOrder.setSaleOrg(orderForm.getVkorg());
            existOrder.setDistributionChannel(orderForm.getVtweg());
            baseMapper.updateById(existOrder);
        } else {
            FcOrder newOrder = new FcOrder();
            newOrder.setContarctId(fcContract == null ? null : fcContract.getId());
            newOrder.setOrderNumber(orderForm.getVbeln());
            newOrder.setOrderTitle(orderForm.getVbelnT());
            newOrder.setCurrency(orderForm.getWerks());
            newOrder.setSapCreateTime(orderForm.getErdat());
            newOrder.setSoldToParty(orderForm.getKunnrSp());
            newOrder.setSoldToPartyCd(orderForm.getKunnrSpT());
            newOrder.setAmount(orderForm.getAmount());
            newOrder.setTaxRate(orderForm.getZsj());
            newOrder.setReciverCd(orderForm.getKunnrBpT());
            newOrder.setReciver(orderForm.getKunnrBp());
            newOrder.setCustomerManager(orderForm.getKunnrEr());
            newOrder.setBileeCd(orderForm.getKunnrBpT());
            newOrder.setBillee(orderForm.getKunnrBp());
            newOrder.setSaleOrg(orderForm.getVkorg());
            newOrder.setDistributionChannel(orderForm.getVtweg());
            baseMapper.insert(newOrder);
        }
    }

    @Override
    public TableDataInfo<FcOrder> listOrders(FcOrder fcOrder, PageQuery pageQuery) {
        Page<FcOrder> page = baseMapper.selectPage(pageQuery.build(), new LambdaQueryWrapper<>());
        return TableDataInfo.build(page);
    }

    @Override
    public FcOrder getOrderDetailById(Long id) {
        return baseMapper.selectById(id);
    }
}
