package com.hexing.base.convert.config;

import com.hexing.base.dal.dataobject.config.ConfigDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.controller.admin.config.vo.ConfigCreateReqVO;
import com.hexing.base.controller.admin.config.vo.ConfigExcelVO;
import com.hexing.base.controller.admin.config.vo.ConfigRespVO;
import com.hexing.base.controller.admin.config.vo.ConfigUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(ConfigDO bean);

    @Mapping(source = "key", target = "configKey")
    ConfigDO convert(ConfigCreateReqVO bean);

    ConfigDO convert(ConfigUpdateReqVO bean);

    @Mapping(source = "configKey", target = "key")
    List<ConfigExcelVO> convertList(List<ConfigDO> list);

}
