package com.hexing.pay.convert.merchant;

import java.util.*;

import com.hexing.common.pojo.PageResult;

import com.hexing.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import com.hexing.pay.controller.admin.merchant.vo.merchant.PayMerchantExcelVO;
import com.hexing.pay.controller.admin.merchant.vo.merchant.PayMerchantRespVO;
import com.hexing.pay.controller.admin.merchant.vo.merchant.PayMerchantUpdateReqVO;
import com.hexing.pay.dal.dataobject.merchant.PayMerchantDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayMerchantConvert {

    PayMerchantConvert INSTANCE = Mappers.getMapper(PayMerchantConvert.class);

    PayMerchantDO convert(PayMerchantCreateReqVO bean);

    PayMerchantDO convert(PayMerchantUpdateReqVO bean);

    PayMerchantRespVO convert(PayMerchantDO bean);

    List<PayMerchantRespVO> convertList(List<PayMerchantDO> list);

    PageResult<PayMerchantRespVO> convertPage(PageResult<PayMerchantDO> page);

    List<PayMerchantExcelVO> convertList02(List<PayMerchantDO> list);

}
