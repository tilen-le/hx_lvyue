package com.hexing.base.convert.logger;

import com.hexing.base.dal.dataobject.logger.ApiAccessLogDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.hexing.base.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExcelVO;
import com.hexing.base.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(ApiAccessLogDO bean);

    List<ApiAccessLogRespVO> convertList(List<ApiAccessLogDO> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<ApiAccessLogDO> page);

    List<ApiAccessLogExcelVO> convertList02(List<ApiAccessLogDO> list);

    ApiAccessLogDO convert(ApiAccessLogCreateReqDTO bean);

}
