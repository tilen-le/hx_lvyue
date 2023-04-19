package com.hexing.system.task;

import com.hexing.system.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author firerock_tech
 */

@Component("customerTask")
@Slf4j
public class CustomerTask {
    @Resource
    HttpKit httpKit;

    public void getCustomer(){
        log.error(httpKit.getData("ZLVY_KHCX"));
    }

    public void getContact(){
        log.error(httpKit.getData("ZLVY_HTCX"));
    }
}
