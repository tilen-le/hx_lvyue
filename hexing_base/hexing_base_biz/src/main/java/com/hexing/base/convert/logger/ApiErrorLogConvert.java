package com.hexing.base.convert.logger;

import com.hexing.base.dal.dataobject.logger.ApiErrorLogDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.hexing.base.controller.admin.logger.vo.apierrorlog.ApiErrorLogExcelVO;
import com.hexing.base.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    List<ApiErrorLogExcelVO> convertList02(List<ApiErrorLogDO> list);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
