package com.hexing.pay.convert.demo;

import com.hexing.common.pojo.PageResult;
import com.hexing.pay.controller.admin.demo.vo.PayDemoOrderCreateReqVO;
import com.hexing.pay.controller.admin.demo.vo.PayDemoOrderRespVO;
import com.hexing.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
