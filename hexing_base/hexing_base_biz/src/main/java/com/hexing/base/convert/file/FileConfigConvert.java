package com.hexing.base.convert.file;

import com.hexing.base.dal.dataobject.file.FileConfigDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.controller.admin.file.vo.config.FileConfigCreateReqVO;
import com.hexing.base.controller.admin.file.vo.config.FileConfigRespVO;
import com.hexing.base.controller.admin.file.vo.config.FileConfigUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(FileConfigDO bean);

    List<FileConfigRespVO> convertList(List<FileConfigDO> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<FileConfigDO> page);

}
