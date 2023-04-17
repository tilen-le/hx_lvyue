package com.hexing.pay.convert.app;

import java.util.*;

import com.hexing.common.pojo.PageResult;

import com.hexing.pay.dal.dataobject.merchant.PayAppDO;
import com.hexing.pay.dal.dataobject.merchant.PayMerchantDO;
import com.hexing.pay.controller.admin.merchant.vo.app.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 支付应用信息 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface PayAppConvert {

    PayAppConvert INSTANCE = Mappers.getMapper(PayAppConvert.class);

    PayAppPageItemRespVO pageConvert (PayAppDO bean);

    PayAppPageItemRespVO.PayMerchant convert(PayMerchantDO bean);

    PayAppDO convert(PayAppCreateReqVO bean);

    PayAppDO convert(PayAppUpdateReqVO bean);

    PayAppRespVO convert(PayAppDO bean);

    List<PayAppRespVO> convertList(List<PayAppDO> list);

    PageResult<PayAppRespVO> convertPage(PageResult<PayAppDO> page);

    List<PayAppExcelVO> convertList02(List<PayAppDO> list);

}
