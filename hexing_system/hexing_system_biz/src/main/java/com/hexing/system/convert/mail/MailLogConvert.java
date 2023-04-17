package com.hexing.system.convert.mail;

import com.hexing.common.pojo.PageResult;
import com.hexing.system.controller.admin.mail.vo.log.MailLogRespVO;
import com.hexing.system.entity.mail.MailLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    PageResult<MailLogRespVO> convertPage(PageResult<MailLogDO> pageResult);

    MailLogRespVO convert(MailLogDO bean);

}
