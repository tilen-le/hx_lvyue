package com.hexing.base.convert.db;

import java.util.*;

import com.hexing.base.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import com.hexing.base.controller.admin.db.vo.DataSourceConfigRespVO;
import com.hexing.base.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import com.hexing.base.dal.dataobject.db.DataSourceConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据源配置 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
