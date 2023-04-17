package com.hexing.pay.dal.mysql.notify;

import com.hexing.pay.dal.dataobject.notify.PayNotifyLogDO;
import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayNotifyLogMapper extends BaseMapperX<PayNotifyLogDO> {
}
