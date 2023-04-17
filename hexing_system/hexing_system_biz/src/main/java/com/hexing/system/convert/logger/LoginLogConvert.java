package com.hexing.system.convert.logger;

import com.hexing.common.pojo.PageResult;
import com.hexing.system.api.logger.dto.LoginLogCreateReqDTO;
import com.hexing.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import com.hexing.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import com.hexing.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
