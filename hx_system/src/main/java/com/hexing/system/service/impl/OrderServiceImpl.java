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
import com.hexing.system.domain.FcOrderProduct;
import com.hexing.system.domain.form.*;
import com.hexing.system.mapper.FcContractMapper;
import com.hexing.system.mapper.FcOrderMapper;
import com.hexing.system.mapper.FcOrderProductMapper;
import com.hexing.system.service.IOrderService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author firerock_tech
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final FcOrderMapper baseMapper;

    private final FcContractMapper fcContractMapper;
    private final FcOrderProductMapper fcOrderProductMapper;
    private final HttpKit httpKit;

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
            existOrder.setContractId(fcContract == null ? null : fcContract.getId());
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
            existOrder.setRequireDeliveryDate(orderForm.getVdatu());
            existOrder.setBileeCd(orderForm.getKunnrBpT());
            existOrder.setBillee(orderForm.getKunnrBp());
            existOrder.setSaleOrg(orderForm.getVkorg());
            existOrder.setFactory(orderForm.getWerks());
            existOrder.setDistributionChannel(orderForm.getVtweg());
            baseMapper.updateById(existOrder);
        } else {
            existOrder = new FcOrder();
            existOrder.setContractId(fcContract == null ? null : fcContract.getId());
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWerks());
            existOrder.setSapCreateTime(orderForm.getErdat());
            existOrder.setSoldToParty(orderForm.getKunnrSp());
            existOrder.setSoldToPartyCd(orderForm.getKunnrSpT());
            existOrder.setRequireDeliveryDate(orderForm.getVdatu());
            existOrder.setAmount(orderForm.getAmount());
            existOrder.setTaxRate(orderForm.getZsj());
            existOrder.setReciverCd(orderForm.getKunnrBpT());
            existOrder.setReciver(orderForm.getKunnrBp());
            existOrder.setFactory(orderForm.getWerks());
            existOrder.setCustomerManager(orderForm.getKunnrEr());
            existOrder.setBileeCd(orderForm.getKunnrBpT());
            existOrder.setBillee(orderForm.getKunnrBp());
            existOrder.setSaleOrg(orderForm.getVkorg());
            existOrder.setDistributionChannel(orderForm.getVtweg());
            baseMapper.insert(existOrder);
        }
        StockForm stockForm = getStore(orderForm);
        FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, existOrder.getId()));
        if (ObjectUtil.isNull(fcOrderProduct)) {
            fcOrderProduct = new FcOrderProduct();
            fcOrderProduct.setOrderId(existOrder.getId());
            fcOrderProduct.setNum(orderForm.getZmeng());
            fcOrderProduct.setProductNumber(orderForm.getMatnr());
            fcOrderProduct.setProductName(orderForm.getMaktx());
            fcOrderProduct.setUnitPrice(orderForm.getNetprZpr0());
            fcOrderProduct.setInStorageNum(stockForm == null ? "0" : stockForm.getKzsl().toString());
            fcOrderProduct.setInTransitNum(stockForm == null ? "0" : stockForm.getZtsl().toString());
            fcOrderProduct.setNotSentNum(stockForm == null ? orderForm.getZmeng() : (Double.parseDouble(orderForm.getZmeng()) - stockForm.getFhsl()) + "");
            fcOrderProduct.setSapDetailNumber(orderForm.getPosnr());
            fcOrderProductMapper.insert(fcOrderProduct);
        } else {
            fcOrderProduct.setOrderId(existOrder.getId());
            fcOrderProduct.setNum(orderForm.getZmeng());
            fcOrderProduct.setProductNumber(orderForm.getMatnr());
            fcOrderProduct.setSapDetailNumber(orderForm.getPosnr());
            fcOrderProduct.setProductName(orderForm.getMaktx());
            fcOrderProduct.setInStorageNum(stockForm == null ? "0" : stockForm.getKzsl().toString());
            fcOrderProduct.setInTransitNum(stockForm == null ? "0" : stockForm.getZtsl().toString());
            fcOrderProduct.setNotSentNum(stockForm == null ? orderForm.getZmeng() : (Double.parseDouble(orderForm.getZmeng()) - stockForm.getFhsl()) + "");
            fcOrderProduct.setUnitPrice(orderForm.getNetprZpr0());
            fcOrderProductMapper.updateById(fcOrderProduct);
        }
    }

    public StockForm getStore(OrderForm orderForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KCGX");
        Map<String, String> data = new HashMap<>();
        data.put("IV_MATNR", orderForm.getMatnr());
        data.put("IV_WERKS", orderForm.getWerks());
        data.put("IV_VBELN", orderForm.getVbeln());
        data.put("IV_POSNR", orderForm.getPosnr());
        params.put("data", data);
        String result = httpKit.getData(params);
        log.error(result);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<StockForm>>() {
            }.getType();
            ResultForm<StockForm> customers = new Gson().fromJson(result, type);
            return customers.getData();
        }
        return null;
    }

    @Override
    public TableDataInfo<FcOrder> listOrders(FcOrder fcOrder, PageQuery pageQuery) {
        Page<FcOrder> page = baseMapper.selectPageOrderList(pageQuery.build(), fcOrder);
        page.getRecords().forEach(item -> {
            LambdaQueryWrapper<FcOrderProduct> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FcOrderProduct::getOrderId, item.getId());
            List<FcOrderProduct> products = fcOrderProductMapper.selectList(queryWrapper);
            if (products.size() > 0) {
                item.setHasChildren(true);
                item.setProducts(products);
            }
        });
        return TableDataInfo.build(page);
    }

    @Override
    public FcOrder getOrderDetailById(Long id) {
        return baseMapper.selectById(id);
    }
}
