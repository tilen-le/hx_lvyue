package com.hexing.system.task;

import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.*;
import com.hexing.system.service.IFcContractService;
import com.hexing.system.service.IFcCustomerService;
import com.hexing.system.service.IFcPaymentService;
import com.hexing.system.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author firerock_tech
 */

@Component("customerTask")
@Slf4j
public class CustomerTask {
    @Resource
    HttpKit httpKit;

    @Resource
    private IFcCustomerService fcCustomerService;

    @Resource
    private IFcContractService fcContractService;

    @Resource
    private IFcPaymentService iFcPaymentService;


    public void getCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KHCX");
        String result = httpKit.getData(params);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<CustomerList>>() {
            }.getType();
            ResultForm<CustomerList> customers = new Gson().fromJson(result, type);
            customers.getData().getItData().forEach(item -> {
                fcCustomerService.saveCustomer(item);
            });
        }
    }

    public void getContact() {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_HTCX");
        String result = httpKit.getData(params);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<ContractList>>() {
            }.getType();
            ResultForm<ContractList> customers = new Gson().fromJson(result, type);
            customers.getData().getItData().forEach(item -> {
                fcContractService.saveContract(item);
            });
        }
    }

    public void getStore(OrderForm orderForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KCGX");
        Map<String, String> data = new HashMap<>();
        data.put("IV_MATNR", orderForm.getMatnr());
        data.put("IV_WERKS", orderForm.getWerks());
        data.put("IV_VBELN", orderForm.getVbeln());
        data.put("IV_POSNR", orderForm.getPosnr());
        params.put("data", data);
        String result = httpKit.getData(params);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<ContractList>>() {
            }.getType();
            ResultForm<ContractList> customers = new Gson().fromJson(result, type);
            customers.getData().getItData().forEach(item -> {
                fcContractService.saveContract(item);
            });
        }
    }


    public void getPayment() {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KUHK");
        String result = httpKit.getData(params);
        log.error(result);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<List<PaymentForm>>>() {
            }.getType();
            ResultForm<List<PaymentForm>> customers = new Gson().fromJson(result, type);
            customers.getData().forEach(item -> {
                iFcPaymentService.saveFcPayment(item);
            });
        }
    }

}
