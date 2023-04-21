package com.hexing.system.task;

import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.system.domain.form.ContractList;
import com.hexing.system.domain.form.CustomerList;
import com.hexing.system.domain.form.ResultForm;
import com.hexing.system.service.IFcContractService;
import com.hexing.system.service.IFcCustomerService;
import com.hexing.system.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;

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


    public void getCustomer() {
        String result = httpKit.getData("ZLVY_KHCX");
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<CustomerList>>() {
            }.getType();
            ResultForm<CustomerList> customers = new Gson().fromJson(result, type);
            customers.getData().getItData().forEach(item->{
               fcCustomerService.saveCustomer(item);
            });
        }
    }

    public void getContact() {
        String result = httpKit.getData("ZLVY_HTCX");
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<ContractList>>() {
            }.getType();
            ResultForm<ContractList> customers = new Gson().fromJson(result, type);
            customers.getData().getItData().forEach(item->{
                fcContractService.saveContract(item);
            });
        }
    }

    public  void getPayback(){
        String result = httpKit.getData("ZLVY_KUHK");
        log.error(result);
    }

}
