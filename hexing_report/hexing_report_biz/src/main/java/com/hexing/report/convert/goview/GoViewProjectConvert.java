package com.hexing.report.convert.goview;

import com.hexing.common.pojo.PageResult;
import com.hexing.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import com.hexing.report.controller.admin.goview.vo.project.GoViewProjectRespVO;
import com.hexing.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import com.hexing.report.dal.dataobject.goview.GoViewProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoViewProjectConvert {

    GoViewProjectConvert INSTANCE = Mappers.getMapper(GoViewProjectConvert.class);

    GoViewProjectDO convert(GoViewProjectCreateReqVO bean);

    GoViewProjectDO convert(GoViewProjectUpdateReqVO bean);

    GoViewProjectRespVO convert(GoViewProjectDO bean);

    PageResult<GoViewProjectRespVO> convertPage(PageResult<GoViewProjectDO> page);

}
