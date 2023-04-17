package com.hexing.system.convert.sms;

import com.hexing.system.controller.admin.sms.vo.log.SmsLogExcelVO;
import com.hexing.system.controller.admin.sms.vo.log.SmsLogRespVO;
import com.hexing.system.entity.sms.SmsLogDO;
import com.hexing.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信日志 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface SmsLogConvert {

    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogRespVO convert(SmsLogDO bean);

    List<SmsLogRespVO> convertList(List<SmsLogDO> list);

    PageResult<SmsLogRespVO> convertPage(PageResult<SmsLogDO> page);

    List<SmsLogExcelVO> convertList02(List<SmsLogDO> list);

}
