package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcOrderProduct;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.StockForm;
import com.hexing.system.mapper.FcOrderProductMapper;
import com.hexing.system.service.IOrderService;
import com.hexing.system.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SyncService {

    @Resource
    HttpKit httpKit;

    @Resource
    private IOrderService orderService;

    @Resource
    private FcOrderProductMapper fcOrderProductMapper;

    @Async
    public void syncStore(List<FcOrderProduct> productList) {
        productList.forEach(item->{
            FcOrder order = orderService.getById(item.getOrderId());
            OrderForm orderForm = new OrderForm();
            orderForm.setMatnr(item.getProductNumber());
            orderForm.setPosnr(item.getSapDetailNumber());
            orderForm.setVbeln(order.getOrderNumber());
            orderForm.setWerks(order.getFactory());
            getStore(orderForm);
        });
    }

    /**
     * 获取库存信息
     *
     * @param orderForm
     * @return
     */
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
            JSONObject obj = JSONObject.parseObject(result);
            StockForm stockForm = obj.getObject("data", StockForm.class);
            if ("S".equals(stockForm.getEvType())) {
                String kzsl = stockForm.getKzsl().toString();
                String ztsl = stockForm.getZtsl().toString();
                String fhsl = stockForm.getFhsl().toString();
                LambdaUpdateWrapper<FcOrderProduct> updateWrapper = new LambdaUpdateWrapper<FcOrderProduct>()
                        .eq(FcOrderProduct::getOrderNumber, orderForm.getVbeln())
                        .eq(FcOrderProduct::getSapDetailNumber, orderForm.getPosnr())
                        .set(FcOrderProduct::getInStorageNum, kzsl)
                        .set(FcOrderProduct::getInTransitNum, ztsl)
                        .set(FcOrderProduct::getSendNum, fhsl);
                fcOrderProductMapper.update(null, updateWrapper);
                Integer storeStatus = getStoreStatus(orderForm.getVbeln());
                orderService.update(null,new LambdaUpdateWrapper<FcOrder>()
                        .set(FcOrder::getStoreStatus,storeStatus)
                        .eq(FcOrder::getOrderNumber,orderForm.getVbeln()));
            }
            return stockForm;
        }
        return null;
    }

    /**
     * 库存状态，部分备货，备货完成，未备货
     * 库存状态：全部产品行未发货=库存，备货状态为备货完成，
     * 部分备货是有产品行不等于库存，量，
     * 未备货是所有库存都为0且剩余发货不为0
     *
     * @param orderNumber
     * @return
     */
    private Integer getStoreStatus(String orderNumber) {
        FcOrder one = orderService.lambdaQuery().eq(FcOrder::getOrderNumber, orderNumber).one();
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, one.getId()));
        double sum = products.stream().mapToDouble(s -> Double.parseDouble(s.getInStorageNum())).sum();
        double nSentSum = products.stream().mapToDouble(s -> Double.parseDouble(s.getNotSentNum())).sum();
        if (sum==0 && nSentSum >0){
            return 3;
        }else {
            for (int i = 0; i < products.size(); i++) {
                FcOrderProduct product = products.get(i);
                int compare = Double.compare(Double.parseDouble(product.getInStorageNum()), Double.parseDouble(product.getNotSentNum()));
                if (compare < 0) {
                    return 2;
                }

            }
            return 1;
        }
    }

    public static void main(String[] args) {

        System.out.println();
    }

}
